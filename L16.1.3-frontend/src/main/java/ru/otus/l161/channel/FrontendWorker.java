package ru.otus.l161.channel;

import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;

import ru.otus.l161.front.ChatEndpoint;
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
    private static final int MESSAGE_DELAY_MS = 100;
    private static final Logger LOG = Log.getLogger(FrontendWorker.class);

    private final Address address = new Address();
    private final Socket socket;
    private SocketMsgWorker client;
    private List<FrontendService> services = new CopyOnWriteArrayList<>();

    public FrontendWorker(String host, int port) throws IOException {
        this(new Socket(host, port));
    }

    private static void onClose(Socket socket) {
        LOG.info("Close socket {}", socket);
        // TODO
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

                for (FrontendService service : services) {
                    if (service.knowsHisAddress(msg.getTo())) {
                        service.deliver(msg);
                        delivered = true;
                    }
                }
                if (address.equals(msg.getTo())) {
                    //noinspection UnusedAssignment
                    delivered = true;
                    LOG.info("Message is delivered: {}", msg.toString());
                } else if ( ! delivered) {
                    LOG.info("Message is not delivered: {}", msg.toString());
                }
            }
        } catch (InterruptedException e) {
            LOG.info(e.getMessage());
        }
    }

    private void registeringServices() throws InterruptedException {
        Msg registerMeMsg = new RequestDBServerMsg(address);
        send(registerMeMsg);
        Thread.sleep(MESSAGE_DELAY_MS);

        for (FrontendService service : services) {
            Address serviceAddress = service.getAddress();
            LOG.info("Registering: {}", serviceAddress);

            Msg pingMsg = new PingMsg(serviceAddress, serviceAddress);
            send(pingMsg);
            Thread.sleep(2*MESSAGE_DELAY_MS);

            Msg requestMsg = new RequestDBServerMsg(serviceAddress);
            send(requestMsg);

            if (service instanceof ChatEndpoint) {
                Msg registerMsg = new RegisterChatFrontendMsg(serviceAddress);
                send(registerMsg);
            }
        }
    }

    private ExecutorService executorService;

    @SuppressWarnings("InfiniteLoopStatement")
    public void loop() throws Exception {

        executorService = Executors.newSingleThreadExecutor();
        executorService.submit(this::call);

        registeringServices();

        while (true) {
            Msg msg = new PingMsg(address, address);
            client.send(msg);
            LOG.debug("Message sent: {}", msg.toString());
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
