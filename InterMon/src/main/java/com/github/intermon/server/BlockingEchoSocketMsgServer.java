package com.github.intermon.server;

import com.github.intermon.app.MsgWorker;
import com.github.intermon.channel.Blocks;
import com.github.intermon.channel.SocketMsgWorker;
import com.github.intermon.messages.Msg;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BlockingEchoSocketMsgServer {
    private static final Logger logger = Logger.getLogger(NonBlockingEchoSocketMsgServer.class.getName());

    private static final int THREADS_NUMBER = 1;
    private static final int PORT = 5050;
    private static final int ECHO_DELAY = 100;

    private final ExecutorService executor;
    private final List<MsgWorker> workers;

    public BlockingEchoSocketMsgServer() {
        executor = Executors.newFixedThreadPool(THREADS_NUMBER);
        workers = new CopyOnWriteArrayList<>();
    }

    @Blocks
    public void start() throws Exception {
        executor.submit(this::echo);

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            logger.info("Server started on port: " + serverSocket.getLocalPort());
            while (!executor.isShutdown()) {
                Socket socket = serverSocket.accept(); //blocks
                SocketMsgWorker worker = new SocketMsgWorker(socket);
                worker.init();
                worker.addShutdownRegistration(() -> workers.remove(worker));
                workers.add(worker);
            }
        }
    }

    @SuppressWarnings("InfiniteLoopStatement")
    private void echo() {
        while (true) {
            for (MsgWorker client : workers) {
                Msg msg = client.pool(); //get
                while (msg != null) {
                    System.out.println("Echoing the message: " + msg.toString());
                    client.send(msg);
                    msg = client.pool();
                }
            }
            try {
                Thread.sleep(ECHO_DELAY);
            } catch (InterruptedException e) {
                logger.log(Level.SEVERE, e.toString());
            }
        }
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
