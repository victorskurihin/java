package ru.otus.l161.front;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;

import ru.otus.l161.app.RandomUnsignedInt;
import ru.otus.l161.dataset.UserDataSet;
import ru.otus.l161.messages.*;

import javax.websocket.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

public class AuthEndpoint extends FrontEndpoint {

    private static final String AUTH = "auth";
    private static final String SINGUP = "singup";
    private static final Logger LOG = Log.getLogger(AuthEndpoint.class);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private final Address address = new Address();

    private final Map<String, RemoteEndpoint.Async> sessions = new ConcurrentHashMap<>();

    private Address dbServerAddress;
    private ChatEndpoint chat;

    public AuthEndpoint() {
        LOG.warn("class loaded {}", this.getClass());
    }

    private void singup(String username, String password, Session session) {
        Msg msg = new SingupMsg(
            address, session.getId(), dbServerAddress, new UserDataSet(username, password)
        );
        client.send(msg);
        LOG.debug("singup msg:{}", msg);
    }

    private void authenticate(String username, String password, Session session) {
        Msg msg = new AuthenticateMsg(
            address, session.getId(), dbServerAddress, new UserDataSet(username, password)
        );
        client.send(msg);
        LOG.debug("authenticate msg:{}", msg);
    }

    @Override
    public void onOpen(Session session, EndpointConfig endpointConfig) {
        LOG.warn("Session opened, id: {}", session.getId());

        sessions.put(session.getId(), session.getAsyncRemote());
        // attach message handler
        session.addMessageHandler(new StringHandler(endpointConfig, session) {

            @Override
            public void onMessage(String message) {
                String sid = session.getId();
                LOG.info("Message received. Session id: {} Message: {}", sid, message);

                Map<String, String> authData = new HashMap<>();
                //noinspection unchecked
                authData = GSON.fromJson(message, authData.getClass());

                String form = authData.getOrDefault("form", null);
                String username = authData.getOrDefault("user", null);
                String password = authData.getOrDefault("pass", null);

                if (null != dbServerAddress) {
                    switch (form) {
                        case SINGUP:
                            singup(username, password, session);
                            break;
                        case AUTH:
                            authenticate(username, password, session);
                    }
                } else {
                    sendJsonToRemote(sid, GSON.toJson(
                            getErrorResult("Can't connect to DB Server!")
                    ));
                    // TODO reconnect
                }
            }
        });
    }

    @Override
    public void onClose(Session session, CloseReason close) {
        String sid = session.getId();
        sessions.remove(sid);
        LOG.info("WebSocket Close session({}): {} - {}",
            sid, close.getCloseCode(), close.getReasonPhrase()
        );
        super.onClose(session, close);
    }

    private void sendJsonToRemote(String sessionId, String jsonString) {
        RemoteEndpoint.Async remote = sessions.get(sessionId);
        remote.sendText(jsonString);
    }

    @Override
    public boolean knowsHisAddress(Address address) {
        return this.address.equals(address); // || addresses.containsKey(address);
    }

    @Override
    public void setChatEndpoint(FrontEndpoint chat) {
        if (chat instanceof ChatEndpoint) {
            this.chat = (ChatEndpoint) chat;
        }
    }

    @Override
    public Address getAddress() {
        return address;
    }

    private void handleSingedMsg(SingedMsg msg) {
        LOG.debug("Handle Singed Message OK: {}", msg.toString());
        String sid = msg.getSessionId();

        if (msg.isPositive()) {
            Objects.requireNonNull(chat);
            LOG.warn("Handle Singed Message Chat positive: {}", chat.getAddress());

            int authId = RandomUnsignedInt.get();
            sendJsonToRemote(sid, GSON.toJson(getOkResult(Integer.toString(authId))));

            AuthenticatedMsg authenticated = new AuthenticatedMsg(
                address, chat.getAddress(), msg.getUser(), authId
            );
            chat.deliver(authenticated);
        } else {
            sendJsonToRemote(
                sid, GSON.toJson(getErrorResult(msg.getMessage()))
            );
        }
    }

    private void handleAuthenticatedMsg(AuthenticatedMsg msg) {
        LOG.debug("Handle Authenticated Message OK: {}", msg.toString());
        String sid = msg.getSessionId();

        if (msg.isPositive()) {
            Objects.requireNonNull(chat);
            LOG.info("Handle Authenticated Message Chat positive: {}", chat.getAddress());

            sendJsonToRemote(sid, GSON.toJson(getOkResult(Integer.toString(msg.getAuth()))));
            AuthenticatedMsg authenticated = msg.forward(address, chat.getAddress());
            chat.deliver(authenticated);
        } else {
            sendJsonToRemote(
                sid, GSON.toJson(getErrorResult(msg.getMessage()))
            );
        }
    }

    @Override
    public void deliver(Msg msg) {
        LOG.debug("Message is delivered: {}", msg.toString());
        if (AuthenticatedMsg.ID.equals(msg.getId())) {
            handleAuthenticatedMsg((AuthenticatedMsg) msg);
        } else if (RequestDBServerMsg.ID.equals(msg.getId())) {
            dbServerAddress = msg.getFrom();
            LOG.info("Got DB Server Address: {}", dbServerAddress);
        } else if (SingedMsg.ID.equals(msg.getId())) {
            handleSingedMsg((SingedMsg) msg);
        }
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
