package com.github.intermon.channel;

/*
 * Created by VSkurikhin at Sun Apr 15 13:21:03 MSK 2018.
 */

import com.github.intermon.messages.Msg;
import com.github.intermon.app.MsgWorker;

import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * The class receives a message from client socket and put this message into
 * the input blocking queue and can give this message by the methods pull or
 * take. Also the class gets the message, when the message is in the queue,
 * it will be sent to the client.
 */
public class SocketMsgWorker implements MsgWorker {
    private static final Logger LOG = LogManager.getLogger(SocketMsgWorker.class);
    private static final int WORKERS_COUNT = 2;

    private final BlockingQueue<Msg> output = new LinkedBlockingQueue<>();
    private final BlockingQueue<Msg> input = new LinkedBlockingQueue<>();

    private final ExecutorService executor;
    private final Socket socket;
    private final List<Runnable> shutdownRegistrations;

    /**
     * Creates an MsgWorker, specifying the client socket.
     *
     * @param socket the client socket
     */
    public SocketMsgWorker(Socket socket) {
        this.socket = socket;
        this.shutdownRegistrations = new ArrayList<>();
        this.executor = Executors.newFixedThreadPool(WORKERS_COUNT);
    }

    /**
     * Gets a message and puts this message into output blocking queue.
     *
     * @param msg a message.
     */
    @Override
    public void send(Msg msg) {
        output.add(msg);
    }

    /**
     * Retrieves and removes the message from the head of input blocking queue,
     * or returns {@code null} if this queue is empty.
     *
     * @return a message or null.
     */
    @Override
    public Msg pool() {
        return input.poll();
    }

    /**
     * Retrieves and removes the head of input blocking queue, waiting if
     * necessary until an element becomes available.
     *
     * @return a message
     * @throws InterruptedException if system will be interrupted
     */
    @Override
    public Msg take() throws InterruptedException {
        return input.take();
    }

    /**
     * Attempts to stop all actively executing tasks, halts the
     * processing of waiting tasks, and returns a list of the tasks
     * that were awaiting execution.
     */
    @Override
    public void close() {
        shutdownRegistrations.forEach(Runnable::run);
        shutdownRegistrations.clear();

        executor.shutdown();
    }

    /**
     * Executes two tasks, the first task for sending messages and second for
     * receiving messages, sometime in the future. The task may execute in
     * a new thread or in an existing pooled thread.
     */
    public void init() {
        executor.execute(this::sendMessage);
        executor.execute(this::receiveMessage);
    }

    /**
     * Adds the runnable object, this object will be executing on close the
     * MsgWorker.
     *
     * @param runnable the runnable object
     */
    public void addShutdownRegistration(Runnable runnable) {
        this.shutdownRegistrations.add(runnable);
    }

    @Blocks
    private void receiveMessage() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            String inputLine;
            StringBuilder stringBuilder = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                stringBuilder.append(inputLine);
                if (inputLine.isEmpty()) { //empty line is the end of the message
                    String json = stringBuilder.toString();
                    if (json.isEmpty())
                        continue;
                    Msg msg = getMsgFromJSON(json);
                    if (msg != null)
                        input.add(msg);
                    stringBuilder = new StringBuilder();
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            LOG.error("ReceiveMessage catch exeption: {}", e);
        } finally {
            close();
        }
    }

    @Blocks
    private void sendMessage() {
        try (PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {
            while (socket.isConnected()) {
                Msg msg = output.take(); //blocks
                String json = new Gson().toJson(msg);
                out.println(json);
                out.println(); //end of the message
            }
        } catch (InterruptedException | IOException e) {
            LOG.error("SendMessage catch execption: {}", e);
        }
    }

    private static Msg getMsgFromJSON(String json) throws ClassNotFoundException {
        try {
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(json);
            String className = (String) jsonObject.get(Msg.CLASS_NAME_VARIABLE);
            Class<?> msgClass = Class.forName(className);
            return (Msg) new Gson().fromJson(json, msgClass);
        } catch (ParseException e) {
            LOG.error("Parsing error: {}", e);
            return null;
        }
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
