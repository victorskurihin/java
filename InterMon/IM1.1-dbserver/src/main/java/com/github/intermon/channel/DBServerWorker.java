package com.github.intermon.channel;

/*
 * Created by VSkurikhin at Sun Apr 25 13:21:50 MSK 2018.
 */

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.github.intermon.DBServerMain;
import com.github.intermon.dataset.UserDataSet;
import com.github.intermon.db.DBService;
import com.github.intermon.db.DBServiceImpl;
import com.github.intermon.messages.*;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DBServerWorker extends SocketMsgWorker implements Addressee, AutoCloseable {

    private static final int PAUSE_MS = 10000;
    private static final Logger LOG = LogManager.getLogger(DBServerMain.class);

    private final Address address = new Address();
    private final Socket socket;
    private DBService dbService;

    public DBServerWorker(String host, int port) throws IOException {
        this(new Socket(host, port));
    }

    private DBServerWorker(Socket socket) {
        super(socket);
        this.socket = socket;
        dbService = new DBServiceImpl(address);
    }

    private boolean userExists(String name) {
        UserDataSet user = dbService.loadByName(name);
        return null != user;
    }

    @SuppressWarnings("InfiniteLoopStatement")
    public void loop() throws Exception {

        LOG.info("DB Server Address:{}", getAddress());

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        /* executorService.submit(() -> {
            try {
                while (true) {
                    Msg msg = take();
                    LOG.debug("Take the message :{}", msg);
                    if (AuthenticateMsg.ID.equals(msg.getId())) {
                        authenticate((AuthenticateMsg) msg);
                    } else if (SingupMsg.ID.equals(msg.getId())) {
                        singUp((SingupMsg) msg);
                    }
                }
            } catch (InterruptedException e) {
                LOG.error(e);
            }
        });*/

        Msg registerMsg = new RegisterDBServerMsg(address);
        send(registerMsg);

        try {
            while (true) {
                Msg msg = new PingMsg(address, address);
                send(msg);
                LOG.debug("ping:{}", msg.getTo());
                Thread.sleep(PAUSE_MS);
            }
        } catch (Exception e) {
            LOG.error(e);
        } finally {
            close();
            executorService.shutdown();
        }
    }

    @Override
    public Address getAddress() {
        return address;
    }

    @Override
    public void close() {
        super.close();
        try {
            socket.close();
        } catch (IOException e) {
            LOG.error(e);
        }
    }

    @Override
    public void deliver(Msg msg) {
        LOG.warn("Message is delivered: {}", msg.toString());
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
