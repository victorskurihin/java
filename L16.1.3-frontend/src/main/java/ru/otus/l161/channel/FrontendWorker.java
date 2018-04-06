package ru.otus.l161.channel;

import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;

import ru.otus.l161.front.AuthEndpoint;
import ru.otus.l161.front.ChatEndpoint;
import ru.otus.l161.front.FrontEndpoint;
import ru.otus.l161.front.FrontendService;
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
    private static final int MESSAGE_DELAY_MS = 1;
    private static final Logger LOG = Log.getLogger(FrontendWorker.class);

    private final Address address = new Address();
    private final Socket socket;
    private SocketMsgWorker client;
    private List<FrontEndpoint> services = new CopyOnWriteArrayList<>();

    public FrontendWorker(String host, int port) throws IOException {
        this(new Socket(host, port));
    }

    private FrontendWorker(Socket socket) {
        super(socket);
        this.socket = socket;
        this.client = this;
        LOG.info("Frontend Address: " + getAddress());
    }

    public void init(FrontEndpoint ... frontendServices) {
        for (FrontEndpoint service : frontendServices) {
            System.out.println("service.getClass().getName() = " + service.getClass().getName());
            if ((service instanceof AuthEndpoint) || (service instanceof ChatEndpoint)) {
                services.add(service);
            }
        }
        // services.addAll(Arrays.asList(frontendServices));
        super.init();
    }

    @SuppressWarnings("InfiniteLoopStatement")
    private void call() {
        try {
            while (true) {
                LOG.info("Call loop.");
                Msg msg = client.take();
                LOG.info("Call loop take:{}", msg);
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
                    // LOG.debug("Message is delivered: {}", msg.toString());
                } else if ( ! delivered) {
                    LOG.warn("Message is not delivered: {}", msg.toString());
                }
            }
        } catch (InterruptedException e) {
            LOG.info(e.getMessage());
        }
    }

    private void registeringServices() throws InterruptedException {
//        Msg registerMeMsg = new RequestDBServerMsg(address);
//        send(registerMeMsg);
//        Thread.sleep(MESSAGE_DELAY_MS);

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
            LOG.info("Loop loop.");
            Msg msg = new PingMsg(address, address);
            client.send(msg);
            LOG.info("Loop loop send:{}", msg);
            // LOG.debug("In loop Message sent: {}", msg.toString());
            Thread.sleep(PAUSE_MS);
        }
    }

    @Override
    public void close() throws IOException, InterruptedException {
        executorService.shutdown();
        super.close();
        socket.close();
        LOG.warn("close!");
    }

    @Override
    public void deliver(Msg msg) {
        LOG.debug("Message is delivered: {}", msg.toString());
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
