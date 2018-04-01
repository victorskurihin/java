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

/**
 * Created by tully.
 */
public class MsgServer implements MsgServerMBean {
    private static final Logger LOG = LogManager.getLogger(MsgServerMBean.class);

    private static final int PORT = 5050;
    private static final int THREADS_NUMBER = 1;
    private static final int MIRROR_DELAY_MS = 100;

    private final ExecutorService executor;
    private final Map<Address, MsgWorker> clients;
    private final Map<Address, Integer> dbServers;

    public MsgServer() {
        executor = Executors.newFixedThreadPool(THREADS_NUMBER);
        clients = new ConcurrentHashMap<>();
        dbServers = new ConcurrentHashMap<>();
    }

    @Blocks
    public void start() throws Exception {
        executor.submit(this::mirror);

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            LOG.info("Server started on port: " + serverSocket.getLocalPort());
            while (!executor.isShutdown()) {
                Socket socket = serverSocket.accept(); //blocks
                SocketMsgWorker client = new SocketMsgWorker(socket);
                client.init();
                // clients.put(client, client.getAddress());
                clients.put(client.getAddress(), client);
                LOG.info("New socket for client: " + client);
            }
        }
    }

    private Address getMinimumLoadingDBServer() {
        return dbServers.entrySet()
                .stream()
                .sorted(Comparator.comparingDouble(Map.Entry::getValue))
                .findFirst()
                .map(Map.Entry::getKey).get();
    }

    @SuppressWarnings("InfiniteLoopStatement")
    private void mirror() {
        while (true) {
            long startNs = System.nanoTime();
            for (Map.Entry<Address, MsgWorker> entry : clients.entrySet()) {
                MsgWorker client = entry.getValue();
                Msg msg = client.pool();
                if (msg == null) {
                    LOG.debug("msg is null from client: {}", client.toString());
                }
                while (msg != null) {
                    if (CloseSocketMsg.CLOSE_SOCKET.equals(msg.getTo().getId())) {
                        LOG.info("Close socket from client: {}", client.toString());
                        dbServers.remove(msg.getFrom());
                        clients.remove(client);
                    } else if (RequestDBServerMsg.REQUEST_DB_SERVER.equals(msg.getTo().getId())) {
                        LOG.info("Register the client: {}", msg.getFrom());
                        clients.put(msg.getFrom(), client);
                        Address dbServerAddress = getMinimumLoadingDBServer();
                        dbServers.compute(dbServerAddress, (address, count) -> count++);
                        LOG.info("Fount DB Server: {}", dbServerAddress);
                        RequestDBServerMsg answer = new RequestDBServerMsg(dbServerAddress);
                        LOG.info("Answer: {}", answer);
                        client.send(answer);
                    } else if (RegisterDBServerMsg.DB_REGISTRATOR.equals(msg.getTo().getId())) {
                        LOG.info("Register the DBService: {}", msg.getFrom());
                        clients.put(msg.getFrom(), client);
                        dbServers.put(msg.getFrom(), 0);
                    } else {
                        MsgWorker recipient = clients.getOrDefault(msg.getTo(), null);
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
            try {
                long delta = (System.nanoTime() - startNs)/1_000_000;
                Thread.sleep(MIRROR_DELAY_MS - (delta < MIRROR_DELAY_MS ? delta : 0));
            } catch (InterruptedException e) {
                LOG.error(e);
            }
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
