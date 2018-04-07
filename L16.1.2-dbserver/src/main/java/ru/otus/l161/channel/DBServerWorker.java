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

    private void singUp(SingupMsg msg) {
        LOG.warn("Message received: {}", msg.toString());
        UserDataSet user = msg.getUser();
        SingedMsg singed = null;

        if (userExists(user.getName())) {
            singed = msg.createAnswer(false, "User exists!");
        } else if (user.getPassword().length() < 2) {
            singed = msg.createAnswer(false, "Password must be more than 2 symbols!");
        } else {
            dbService.save(user);

            if (userExists(user.getName())) {
                singed = msg.createAnswer(true, "Ok");
            } else {
                singed = msg.createAnswer(false, "DB error!");
            }
        }
        if (null != singed) {
            send(singed);
        }
    }

    private boolean doAuthenticate(AuthenticateMsg msg, UserDataSet user) {
        UserDataSet dbUser = dbService.loadByName(msg.getUser().getName());

        return ( dbUser.getName().equals(user.getName())
                && dbUser.getPassword().equals(user.getPassword()));
    }

    private void authenticate(AuthenticateMsg msg) {
        LOG.warn("Message received: {}", msg.toString());
        AuthenticatedMsg authenticated = null;
        UserDataSet user = msg.getUser();

        if (doAuthenticate(msg, user)) {
            authenticated = msg.createAnswer(true, "ok");
        } else if (userExists(msg.getUser().getName())) {
            authenticated = msg.createAnswer(false, "Bad password!");
        } else {
            authenticated = msg.createAnswer(false, "User doesn't exists!");
        }

        if (null != authenticated) {
            send(authenticated);
        }
    }

    @SuppressWarnings("InfiniteLoopStatement")
    public void loop() throws Exception {

        LOG.info("DB Server Address:{}", getAddress());

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(() -> {
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
        });

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
    public void close() throws IOException, InterruptedException {
        super.close();
        socket.close();
    }

    @Override
    public void deliver(Msg msg) {
        LOG.warn("Message is delivered: {}", msg.toString());
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
