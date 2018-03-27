package ru.otus.l151.app;

import ru.otus.l151.dataset.UserDataSet;
import ru.otus.l151.db.MsgGetUserDataSet;
import ru.otus.l151.db.MsgNewUserDataSet;
import ru.otus.l151.messageSystem.Address;
import ru.otus.l151.messageSystem.ControlBlock;
import ru.otus.l151.messageSystem.Message;
import ru.otus.l151.messageSystem.MessageSystem;

import javax.websocket.Endpoint;
import javax.websocket.RemoteEndpoint;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by tully.
 */
public abstract class FrontendEndpoint extends Endpoint implements FrontendService {

    private Address address;
    private MessageSystemContext context;

    private final Map<String, Long> users = new ConcurrentHashMap<>();
    private final Map<Long, String> passwords = new ConcurrentHashMap<>();

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
    public MessageSystem getMS() {
        return context.getMessageSystem();
    }

    @Override
    public void deliverUserDataSet(ControlBlock control, UserDataSet user) {
        if (null != user && user.getId() > -1) {
            users.put(user.getName(), user.getId());
            passwords.put(user.getId(), user.getPassword());
        }
    }

    public Message msgGetUserDataSet(ControlBlock control, String login) {
        return new MsgGetUserDataSet(getAddress(), context.getDbAddress(), control, login);
    }

    public Message msgNewUserDataSet(ControlBlock control, UserDataSet user) {
        return new MsgNewUserDataSet(getAddress(), context.getDbAddress(), control, user);
    }

    public Message msgChatUserDataSet(ControlBlock control, String login) {
        Address chatAddress = new Address("Frontend service chat");
        return new MsgGetUserDataSet(chatAddress, context.getDbAddress(), control, login);
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
