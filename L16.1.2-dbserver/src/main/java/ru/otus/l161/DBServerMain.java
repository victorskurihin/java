package ru.otus.l161;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.otus.l161.messages.Msg;
import ru.otus.l161.channel.SocketMsgWorker;
import ru.otus.l161.messages.Address;
import ru.otus.l161.messages.PingMsg;
import ru.otus.l161.messages.RegisterDBServerMsg;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by tully.
 */
public class DBServerMain {
    private static final Logger LOG = LogManager.getLogger(DBServerMain.class);

    private static final String HOST = "localhost";
    private static final int PORT = 5050;
    private static final int PAUSE_MS = 10000;

    public static void main(String[] args) throws Exception {
        new DBServerMain().start();
    }

    @SuppressWarnings("InfiniteLoopStatement")
    private void start() throws Exception {
        SocketMsgWorker client = new DBServerWorker(HOST, PORT);
        client.init();

        LOG.info("DB Server Address: " + client.getAddress());

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(() -> {
            try {
                while (true) {
                    Object msg = client.take();
                    System.out.println("Message received: " + msg.toString());
                }
            } catch (InterruptedException e) {
                LOG.warn(e);
            }
        });

        Address myAddress = client.getAddress();
        Msg registerMsg = new RegisterDBServerMsg(myAddress);
        client.send(registerMsg);

        try {
            while (true) {
                Msg msg = new PingMsg(myAddress, myAddress);
                client.send(msg);
                System.out.println("Message sent: " + msg.toString());
                Thread.sleep(PAUSE_MS);
            }
        } catch (Exception e) {
            LOG.error(e);
        } finally {
            client.close();
            executorService.shutdown();
        }
    }
}
