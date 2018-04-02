package ru.otus.l161.front;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;

import ru.otus.l161.dataset.UserDataSet;
import ru.otus.l161.messages.Msg;
import ru.otus.l161.messages.Address;
import ru.otus.l161.messages.SingupMsg;

import javax.websocket.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class AuthEndpoint extends FrontEndpoint {

    private static final String AUTH = "auth";
    private static final String SINGUP = "singup";
    private static final Logger LOG = Log.getLogger(AuthEndpoint.class);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private final Address address = new Address();

    private final Map<String, RemoteEndpoint.Async> sessions = new ConcurrentHashMap<>();
    private final Map<Address, RemoteEndpoint.Async> addresses = new ConcurrentHashMap<>();

    private Address dbServerAddress;

    public AuthEndpoint() {
        LOG.debug("class loaded {}", this.getClass());
    }

    private void singup(String username, String password, Session session) {
        Msg msg = new SingupMsg(
            address, session.getId(), dbServerAddress, new UserDataSet(username, password)
        );
        client.send(msg);
        LOG.info("sent {}", msg);
    }

    private void authenticate(String username, String password, Session session) {

    }

    @Override
    public void onOpen(Session session, EndpointConfig endpointConfig) {
        LOG.warn("Session opened, id: {}", session.getId());

        sessions.put(session.getId(), session.getAsyncRemote());
        addresses.put(new Address(), session.getAsyncRemote());
        // attach message handler
        session.addMessageHandler(new StringHandler(endpointConfig, session) {

            @Override
            public void onMessage(String message) {
                LOG.warn(
                    "Message received. Session id: {} Message: {}",
                    session.getId(), message
                );
                Map<String, String> authData = new HashMap<>();
                //noinspection unchecked
                authData = GSON.fromJson(message, authData.getClass());

                String form = authData.getOrDefault("form", null);
                String username = authData.getOrDefault("user", null);
                String password = authData.getOrDefault("pass", null);

                switch (form) {
                    case SINGUP: singup(username, password, session);
                        break;
                    case AUTH: authenticate(username, password, session);
                }
          }
        });
    }

    @Override
    public void onClose(Session session, CloseReason close) {
        sessions.remove(session.getId());
        LOG.warn(
            "WebSocket Close session({}): {} - {}",
            session.getId(), close.getCloseCode(), close.getReasonPhrase()
        );
        super.onClose(session, close);
    }

    private void sendJsonToRemote(String sessionId, String jsonString) {
        RemoteEndpoint.Async remote = sessions.get(sessionId);
        remote.sendText(jsonString);
    }

    @Override
    public boolean knowsHisAddress(Address address) {
        return addresses.containsKey(address);
    }

    @Override
    public void setDbServerAddress(Address dbServerAddress) {
        this.dbServerAddress = dbServerAddress;
    }

    @Override
    public Address getAddress() {
        return address;
    }

    @Override
    public void deliver(Msg msg) {
        LOG.info("Message is delivered: {}", msg.toString());
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
