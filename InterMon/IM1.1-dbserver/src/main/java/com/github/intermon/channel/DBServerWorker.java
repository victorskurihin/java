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
    private static final int PAUSE_MS = 100;
    private static final int PING_PAUSE_MS = 10000;
    private static final Logger LOG = LogManager.getLogger(DBServerMain.class);

    private final Address address = new Address();
    private final Socket socket;
    private DBService dbService;
    private ExecutorService executor;
    private double load = 0.0;

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

    private boolean replyLogin(LoginMsg msg) {
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
                return replyLogin((LoginMsg) msg);
        }

        return false;
    }

    @SuppressWarnings("InfiniteLoopStatement")
    private void workLoop() {
        try {
            while (true) {
                long startNs = System.nanoTime();
                Msg msg = take();
                assert msg != null;

                if ( ! handleMsg(msg)) {
                    LOG.error("Can't handle Msg: {}", msg.toString());
                } else if (LOG.isInfoEnabled()) { // TODO debug
                    LOG.info("Take the message:{}", msg);
                }

                load += (((double) (System.nanoTime() - startNs)) * 10E-9);
                long delta = (System.nanoTime() - startNs)/1_000_000;
                Thread.sleep(PAUSE_MS - (delta < PAUSE_MS ? delta: 0));
            }
        } catch (InterruptedException e) {
            LOG.error(e);
        }
    }

    @SuppressWarnings("InfiniteLoopStatement")
    public void loops() throws Exception {
        executor = Executors.newSingleThreadExecutor();
        executor.execute(this::workLoop);

        Msg registerMsg = new RegisterDBServerMsg(address);
        send(registerMsg);

        LOG.info("DB Server Address:{}", getAddress());

        try {
            while (true) {
                Msg msg = new LoadMsg(address, address, load);
                send(msg);
                LOG.debug("load:{}", msg);
                Thread.sleep(PING_PAUSE_MS);
            }
        } catch (Exception e) {
            LOG.error(e);
        } finally {
            close();
            executor.shutdown();
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
