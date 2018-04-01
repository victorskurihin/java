package ru.otus.l161.front;

import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;

import ru.otus.l161.messages.Address;
import ru.otus.l161.messages.Msg;

import javax.websocket.CloseReason;
import javax.websocket.EndpointConfig;
import javax.websocket.RemoteEndpoint;
import javax.websocket.Session;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ChatEndpoint extends FrontEndpoint {

    private static final Logger LOG = Log.getLogger(ChatEndpoint.class);
    private final Address address = new Address();
    // private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    private final Map<String, RemoteEndpoint.Async> sessions = new ConcurrentHashMap<>();
    private final Map<Address, RemoteEndpoint.Async> addresses = new ConcurrentHashMap<>();

    private Address dbServerAddress;

    public ChatEndpoint() {
        LOG.debug("class loaded {}", this.getClass());
    }

    @Override
    public void onOpen(Session session, EndpointConfig endpointConfig) {
        LOG.warn("Session opened, id: {}", session.getId());

        sessions.put(session.getId(), session.getAsyncRemote());
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
                //authData = GSON.fromJson(message, authData.getClass());
                //
                //String form = authData.getOrDefault("form", null);
                //String username = authData.getOrDefault("user", null);
                //String password = authData.getOrDefault("pass", null);
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
