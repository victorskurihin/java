package ru.otus.l161.channel;

import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import ru.otus.l161.MsgServerMain;
import ru.otus.l161.messages.Msg;
import ru.otus.l161.app.MsgWorker;
import ru.otus.l161.messages.Address;
import ru.otus.l161.messages.CloseSocketMsg;
import ru.otus.l161.server.OnSocketClose;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Objects;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by tully.
 */
public class SocketMsgWorker implements MsgWorker {
    private static final Logger LOG = LogManager.getLogger(MsgServerMain.class);
    private static final int WORKERS_COUNT = 2;
    private static final int CLOSE_PAUSE_MS = 100;

    private final Address ADDRESS = new Address();
    private final BlockingQueue<Msg> output = new LinkedBlockingQueue<>();
    private final BlockingQueue<Msg> input = new LinkedBlockingQueue<>();

    private final ExecutorService executor;
    private final Socket socket;
    private final OnSocketClose onClose;

    public SocketMsgWorker(Socket socket, OnSocketClose onClose) {
        this.socket = socket;
        this.onClose = onClose;
        this.executor = Executors.newFixedThreadPool(WORKERS_COUNT);
    }

    @Override
    public Address getAddress() {
        return ADDRESS;
    }

    @Override
    public void send(Msg msg) {
        output.add(msg);
    }

    @Override
    public Msg pool() {
        return input.poll();
    }

    @Override
    public Msg take() throws InterruptedException {
        return input.take();
    }

    @Override
    public void close() throws IOException, InterruptedException {
        Msg closeMsg = new CloseSocketMsg(getAddress());
        send(closeMsg);
        Thread.sleep(CLOSE_PAUSE_MS);
        executor.shutdown();
    }

    public void init() {
        executor.execute(this::sendMessage);
        executor.execute(this::receiveMessage);
    }

    @Blocks
    private void sendMessage() {
        try (PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {
            while (socket.isConnected()) {
                Msg msg = output.take(); //blocks
                String json = new Gson().toJson(msg);
                out.println(json);
                out.println();//line with json + an empty line
            }
        } catch (InterruptedException | IOException e) {
            onClose.onClose(socket);
            LOG.error(e);
        }
        if ( ! socket.isConnected()) {
            onClose.onClose(socket);
        }
    }

    @Blocks
    private void receiveMessage() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            String inputLine;
            StringBuilder stringBuilder = new StringBuilder();
            while ((inputLine = in.readLine()) != null) { //blocks

                stringBuilder.append(inputLine);
                if (inputLine.isEmpty()) { //empty line is the end of the message
                    String json = stringBuilder.toString();
                    Msg msg = getMsgFromJSON(json);
                    input.add(msg);
                    stringBuilder = new StringBuilder();
                }
            }
        } catch (IOException | ParseException e) {
            onClose.onClose(socket);
            LOG.error(e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static Msg getMsgFromJSON(String json) throws ParseException, ClassNotFoundException {
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(json);
        String className = (String) jsonObject.get(Msg.CLASS_NAME_VARIABLE);
        Class<?> msgClass = Class.forName(className);
        return (Msg) new Gson().fromJson(json, msgClass);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SocketMsgWorker that = (SocketMsgWorker) o;
        return Objects.equals(ADDRESS, that.ADDRESS);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ADDRESS);
    }
}
