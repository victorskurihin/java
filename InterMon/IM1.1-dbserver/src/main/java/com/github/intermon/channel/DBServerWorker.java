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

    private static final int WORKERS_COUNT = 2;
    private static final int PAUSE_MS = 10000;
    private static final Logger LOG = LogManager.getLogger(DBServerMain.class);

    private final Address address = new Address();
    private final Socket socket;
    private DBService dbService;
    private ExecutorService executor;

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


    private boolean loginAnswer(LoginMsg msg) {
        send(msg.createAnswer(getAddress()));
        return true;
    }

    private boolean handleMsg(Msg msg) {
        if (null == msg) {
            LOG.error("Can't handle null!");
            return false;
        }
        switch (msg.getId()) {
            case RegisterOfMsg.LOGIN_MSG:
                return loginAnswer((LoginMsg) msg);
        }
        return false;
   }

    @SuppressWarnings("InfiniteLoopStatement")
    private void takeLoop() {
        try {
            while (true) {
                Msg msg = take();
                LOG.debug("Take the message :{}", msg);
                if ( ! handleMsg(msg)) {
                    LOG.error("Can't handle the message: {}", msg);
                }
                Thread.sleep(PAUSE_MS);
            }
        } catch (Exception e) {
            LOG.error(e);
        } finally {
            close();
            executor.shutdown();
        }
    }

    @SuppressWarnings("InfiniteLoopStatement")
    private void pingLoop() {
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
            executor.shutdown();
        }
    }

    public void loops() throws Exception {
        executor = Executors.newFixedThreadPool(WORKERS_COUNT);
        Msg registerMsg = new RegisterDBServerMsg(address);

        LOG.info("DB Server Address:{}", getAddress());

        send(registerMsg);
        executor.execute(this::takeLoop);
        executor.execute(this::pingLoop);
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
