package ru.otus.l161.channel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ru.otus.l161.DBServerMain;
import ru.otus.l161.dataset.UserDataSet;
import ru.otus.l161.db.DBService;
import ru.otus.l161.db.DBServiceImpl;
import ru.otus.l161.messages.*;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DBServerWorker extends SocketMsgWorker implements Addressee, AutoCloseable {

    private static final int PAUSE_MS = 10000;
    private static final Logger LOG = LogManager.getLogger(DBServerMain.class);

    private final Address address = new Address();
    private final Socket socket;
    private SocketMsgWorker client;
    private DBService dbService;

    public DBServerWorker(String host, int port) throws IOException {
        this(new Socket(host, port));
    }

    private static void onClose(Socket socket) {
        LOG.info("tut5 {}", socket);
        // TODO
    }

    private DBServerWorker(Socket socket) throws IOException {
        super(socket);
        this.socket = socket;
        this.client = this;
        dbService = new DBServiceImpl(address);
    }

    @SuppressWarnings("InfiniteLoopStatement")
    public void loop() throws Exception {
        client.init();

        LOG.info("DB Server Address: " + client.getAddress());

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(() -> {
            try {
                while (true) {
                    Msg msg = client.take();
                    if (SingupMsg.ID.equals(msg.getId())) {
                        SingupMsg singup = (SingupMsg) msg;
                        System.out.println("Message received: " + msg.toString());
                        UserDataSet usr = singup.getUser();
                        dbService.save(usr);
                        UserDataSet user = dbService.loadByName(usr.getName());
                        if (null != user) {
                            SingedMsg singed = singup.createAnswer(true, "ok");
                            System.out.println("singed = " + singed);
                            client.send(singed);
                        } else {
                            // TODO
                        }
                    }
                }
            } catch (InterruptedException e) {
                LOG.warn(e);
            }
        });

        Msg registerMsg = new RegisterDBServerMsg(address);
        client.send(registerMsg);

        try {
            while (true) {
                Msg msg = new PingMsg(address, address);
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

    @Override
    public void close() throws IOException, InterruptedException {
        super.close();
        socket.close();
    }

    @Override
    public void deliver(Msg msg) {
        // TODO
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
