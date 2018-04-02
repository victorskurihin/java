package ru.otus.l161.channel;

import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;

import ru.otus.l161.front.FrontendService;
import ru.otus.l161.messages.Msg;
import ru.otus.l161.messages.*;

import java.io.IOException;
import java.net.Socket;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FrontendWorker extends SocketMsgWorker implements Addressee, AutoCloseable {

    private static final int PAUSE_MS = 10000;
    private static final Logger LOG = Log.getLogger(FrontendWorker.class);

    private final Address address = new Address();
    private final Socket socket;
    private SocketMsgWorker client;
    private Address dbServerAddress;
    private List<FrontendService> services = new CopyOnWriteArrayList<>();

    public FrontendWorker(String host, int port) throws IOException {
        this(new Socket(host, port));
    }

    private FrontendWorker(Socket socket) throws IOException {
        super(socket);
        this.socket = socket;
        this.client = this;
        LOG.info("Frontend Address: " + getAddress());
    }

    public void init(FrontendService ... frontendServices) {
        services.addAll(Arrays.asList(frontendServices));
        super.init();
    }

    @SuppressWarnings("InfiniteLoopStatement")
    private void call() {
        try {
            while (true) {
                Msg msg = client.take();
                boolean delivered = false;

                if (RequestDBServerMsg.REQUEST_DB_SERVER.equals(msg.getTo().getId())) {
                    dbServerAddress = msg.getFrom();
                    for (FrontendService service : services) {
                        service.setDbServerAddress(dbServerAddress);
                    }
                    LOG.warn("Get DB Server Address: {}", dbServerAddress);
                } else {
                    for (FrontendService service : services) {
                        if (service.knowsHisAddress(msg.getTo())) {
                            service.deliver(msg);
                            delivered = true;
                        }
                    }
                    if ( ! delivered) {
                        LOG.info("Message is not delivered: {}", msg.toString());
                    }
                }
            }
        } catch (InterruptedException e) {
            LOG.info(e.getMessage());
        }
    }

    private ExecutorService executorService;

    @SuppressWarnings("InfiniteLoopStatement")
    public void loop() throws Exception {

        executorService = Executors.newSingleThreadExecutor();
        executorService.submit(this::call);

        Msg registerMsg = new RequestDBServerMsg(address);
        send(registerMsg);

        while (true) {
            Msg msg = new PingMsg(address, address);
            client.send(msg);

            LOG.debug("Message sent: {}", msg.toString());

            if (dbServerAddress != null) {
                Msg msgToDbServer = new PingMsg(address, dbServerAddress);
                client.send(msgToDbServer);
                LOG.debug("Message sent: {}", msgToDbServer.toString());
            }
            Thread.sleep(PAUSE_MS);
        }
    }

    @Override
    public void close() throws IOException, InterruptedException {
        executorService.shutdown();
        super.close();
        socket.close();
        LOG.warn("close!!!");
    }

    @Override
    public void deliver(Msg msg) {
        LOG.debug("Message is delivered: {}", msg.toString());
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
