package ru.otus.l151.app;

import ru.otus.l151.db.MsgGetUser;
import ru.otus.l151.db.MsgNewUser;
import ru.otus.l151.messageSystem.Address;
import ru.otus.l151.messageSystem.Message;
import ru.otus.l151.messageSystem.MessageSystem;

import javax.websocket.Endpoint;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by tully.
 */
public abstract class FrontendEndpoint extends Endpoint implements FrontendService {

    private Address address;
    private MessageSystemContext context;

    private final Map<String, Long> users = new HashMap<>();
    private final Map<Long, String> passwords = new HashMap<>();

    public void init() {
        context.getMessageSystem().addAddressee(this);
    }

    void setAddress(Address address) {
        this.address = address;
    }

    void setContext(MessageSystemContext context) {
        this.context = context;
    }

    @Override
    public Address getAddress() {
        return address;
    }

    @Override
    public void handleRequest(Message message) {
        context.getMessageSystem().sendMessage(message);
    }

    @Override
    public void idUser(long id, String name) {
        if (id > -1) {
            users.put(name, id);
        }
    }

    @Override
    public void idPassword(long id, String password) {
        if (id > -1) {
            passwords.put(id, password);
        }
    }

    @Override
    public MessageSystem getMS() {
        return context.getMessageSystem();
    }

    public Message msgGetUser(String login, String password) {
        return new MsgGetUser(getAddress(), context.getDbAddress(), login, password);
    }

    public Message msgNewUser(String login, String password) {
        return new MsgNewUser(getAddress(), context.getDbAddress(), login, password);
    }


    public long lookup(String username) {
        return users.getOrDefault(username, (long) -1);
    }

    public boolean isUserExists(String username) {
        return lookup(username) > -1;

    }

    public boolean auth(String username, String password) {
        long id = users.getOrDefault(username, (long) -1);

        if (id < 0) {
            return false;
        }
        String pwd = passwords.getOrDefault(id, null);

        return pwd != null && password.equals(pwd);
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
