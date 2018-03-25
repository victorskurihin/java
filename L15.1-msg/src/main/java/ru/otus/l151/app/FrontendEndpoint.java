package ru.otus.l151.app;

import ru.otus.l151.db.MsgGetUserId;
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

    private final Map<Integer, String> users = new HashMap<>();

    public void init() {
        context.getMessageSystem().addAddressee(this);
    }

    void setAddress(Address address) {
        System.err.printf("Set Address: %s for %s%n", address.getId(), this);
        this.address = address;
    }

    void setContext(MessageSystemContext context) {
        System.err.printf("Set Message System Context: %s for %s%n", context.toString(), this);
        this.context = context;
    }

    @Override
    public Address getAddress() {
        return address;
    }

    public void handleRequest(String login) {
        System.err.printf("Address: %s for %s%n", getAddress(), this);
        System.err.printf("Context: %s for %s%n", context, this);
        Message message = new MsgGetUserId(getAddress(), context.getDbAddress(), login);
        context.getMessageSystem().sendMessage(message);
    }

    public void addUser(int id, String name) {
        users.put(id, name);
        System.out.println("User: " + name + " has id: " + id);
    }

    @Override
    public MessageSystem getMS() {
        return context.getMessageSystem();
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
