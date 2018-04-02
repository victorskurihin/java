package ru.otus.l161.server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ru.otus.l161.messages.Msg;
import ru.otus.l161.app.MsgWorker;
import ru.otus.l161.channel.Blocks;
import ru.otus.l161.channel.SocketMsgWorker;
import ru.otus.l161.messages.*;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.Comparator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MsgServer implements MsgServerMBean {

    private static final Logger LOG = LogManager.getLogger(MsgServerMBean.class);
    private static final int PORT = 5050;
    private static final int THREADS_NUMBER = 1;
    private static final int MIRROR_DELAY_MS = 100;

    private final ExecutorService executor;
    private final Map<Socket, MsgWorker> sockets;
    private final Map<MsgWorker, Address> clients;
    private final Map<Address, MsgWorker> recipients;
    private final Map<Address, Integer> dbServers;

    public MsgServer() {
        executor = Executors.newFixedThreadPool(THREADS_NUMBER);
        sockets = new ConcurrentHashMap<>();
        clients = new ConcurrentHashMap<>();
        recipients = new ConcurrentHashMap<>();
        dbServers = new ConcurrentHashMap<>();
    }

    private void onClose(Socket socket) {
        MsgWorker client = sockets.getOrDefault(socket, null);
        if (null != client) {
            Address address = clients.getOrDefault(client, null);
            LOG.info("address {}", address);
            if (null != dbServers.remove(address)) {
                LOG.info("Remove DB Server {}", client);
            }
            if (null != recipients.remove(address)) {
                LOG.info("Remove client {}", client);
            }
            clients.remove(client);
            sockets.remove(socket);
        }
    }

    @Blocks
    public void start() throws Exception {
        executor.submit(this::iterateByClients);

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            LOG.info("Server started on port: " + serverSocket.getLocalPort());

            while (!executor.isShutdown()) {
                Socket socket = serverSocket.accept(); //blocks
                SocketMsgWorker client = new SocketMsgWorker(socket, this::onClose);

                client.init();
                sockets.put(socket, client);

                LOG.info("New socket for client: " + client);
            }
        }
    }

    private Address getMinimumLoadingDBServer() {
        //noinspection ConstantConditions
        Address address = dbServers
                .entrySet()
                .stream()
                .min(Comparator.comparingDouble(Map.Entry::getValue))
                .map(Map.Entry::getKey).get();
        LOG.info("Minimum Loading {}", address);

        return address;
    }

    private void loop(MsgWorker client, Msg msg) {
        while (msg != null) {
            if (CloseSocketMsg.ID.equals(msg.getTo().getId())) {
                LOG.info("Close socket from client: {}", client.toString());

                dbServers.remove(msg.getFrom());
                recipients.remove(client.getAddress());
            } else if (RequestDBServerMsg.ID.equals(msg.getTo().getId())) {
                LOG.info("Register the client: {}", msg.getFrom());
                recipients.put(msg.getFrom(), client);
                clients.put(client, msg.getFrom());

                Address dbServerAddress = getMinimumLoadingDBServer();
                dbServers.compute(dbServerAddress, (address, count) -> count++);

                LOG.info("Fount DB Server: {}", dbServerAddress);
                RequestDBServerMsg answer = new RequestDBServerMsg(dbServerAddress);

                LOG.info("Answer: {}", answer);
                client.send(answer);
            } else if (RegisterDBServerMsg.ID.equals(msg.getTo().getId())) {
                LOG.info("Register the DBService: {}", msg.getFrom());

                recipients.put(msg.getFrom(), client);
                dbServers.put(msg.getFrom(), 0);
                clients.put(client, msg.getFrom());
            } else {
                MsgWorker recipient = recipients.getOrDefault(msg.getTo(), null);

                if (null != recipient) {
                    LOG.warn("Delivering the message: {}", msg.toString());
                    recipient.send(msg);
                } else {
                    LOG.error(
                        "Recipient {} can't find and the message: {} is droped.",
                        msg.getTo(), msg.toString()
                    );
                }
            }
            msg = client.pool();
        }
    }

    private void checkAliveClient(MsgWorker client) {
        // Address address = client.getAddress();
        LOG.info("check client {}", client);
        Address address = clients.getOrDefault(client, null);
        if (null != address) {
            Msg ping = new PingMsg(address, address);
            client.send(ping);
        }
    }

    @SuppressWarnings("InfiniteLoopStatement")
    private void iterateByClients() {
        long count = 0;
        while (true) {
            long startNs = System.nanoTime();

            for (Map.Entry<Socket, MsgWorker> entry : sockets.entrySet()) {

                MsgWorker client = entry.getValue();
                Msg msg = client.pool();

                if (msg == null && count % MIRROR_DELAY_MS == 0) {
                    checkAliveClient(client);
                } else {
                    loop(client, msg);
                }
            }
            try {
                long delta = (System.nanoTime() - startNs)/1_000_000;
                Thread.sleep(MIRROR_DELAY_MS - (delta < MIRROR_DELAY_MS ? delta : 0));
            } catch (InterruptedException e) {
                LOG.error(e);
            }
            count++;
        }
    }

    @Override
    public boolean getRunning() {
        return true;
    }

    @Override
    public void setRunning(boolean running) {
        if (!running) {
            executor.shutdown();
            LOG.info("Bye.");
        }
    }
}
