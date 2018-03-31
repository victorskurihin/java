package ru.otus.l161;

import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;
import ru.otus.l161.app.Msg;
import ru.otus.l161.app.MsgWorker;
import ru.otus.l161.channel.SocketMsgWorker;
import ru.otus.l161.messages.Address;
import ru.otus.l161.messages.PingMsg;
import ru.otus.l161.messages.RegisterDBServerMsg;
import ru.otus.l161.messages.RequestDBServerMsg;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by tully.
 */
public class FrontendMain {
    private static final Logger LOG = Log.getLogger(FrontendMain.class);

    private static final String HOST = "localhost";
    private static final int PORT = 5050;
    private static final int PAUSE_MS = 5000;
    private static final int MAX_MESSAGES_COUNT = 10;

    private Address dbServerAddress;


    public static void main(String[] args) throws Exception {
        new FrontendMain().start();
    }

    @SuppressWarnings("InfiniteLoopStatement")
    private void start() throws Exception {
        SocketMsgWorker client = new FrontendWorker(HOST, PORT);
        client.init();

        LOG.info("Frontend Address: " + client.getAddress());

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(() -> {
            try {
                while (true) {
                    Msg msg = client.take();
                    System.out.println("Message received: " + msg.toString());

                    if (RequestDBServerMsg.REQUEST_DB_SERVER.equals(msg.getTo().getId())) {
                        dbServerAddress = msg.getFrom();
                        LOG.warn("Get DB Server Address: {}", dbServerAddress);
                    }
                }
            } catch (InterruptedException e) {
                LOG.info(e.getMessage());
            }
        });

        Address myAddress = client.getAddress();
        Msg registerMsg = new RequestDBServerMsg(myAddress);
        client.send(registerMsg);

        int count = 0;
        while (count < MAX_MESSAGES_COUNT) {
            Msg msg = new PingMsg(myAddress, myAddress);
            client.send(msg);
            System.out.println("Message sent: " + msg.toString());
            if (dbServerAddress != null) {
                Msg msgToDbServer = new PingMsg(myAddress, dbServerAddress);
                client.send(msgToDbServer);
                System.out.println("Message sent: " + msgToDbServer.toString());
            }
            Thread.sleep(PAUSE_MS);
            count++;
        }
        client.close();
        executorService.shutdown();
    }
}
