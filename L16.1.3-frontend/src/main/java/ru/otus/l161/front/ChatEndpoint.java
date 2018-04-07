package ru.otus.l161.front;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;

import ru.otus.l161.messages.*;

import javax.websocket.CloseReason;
import javax.websocket.EndpointConfig;
import javax.websocket.RemoteEndpoint;
import javax.websocket.Session;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class ChatEndpoint extends FrontEndpoint {

    private static final int DELAY_MS = 100;
    private static final Logger LOG = Log.getLogger(ChatEndpoint.class);
    private final Address address = new Address();
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    private final Map<String, Integer> auths = new ConcurrentHashMap<>();
    private final AuthenticatedSessions sessions = new AuthenticatedSessions();

    private Address dbServerAddress;

    public ChatEndpoint() {
        LOG.warn("class loaded {}", this.getClass());
    }

    private boolean isAuthExists(String user, int authId) {
        Integer id = auths.getOrDefault(user, null);
        return null != id && id == authId;
    }

    private void doAuth(String user, int authId, Session session) {
        for(int i = 0; i < 9 && ! isAuthExists(user, authId); ++i) {
            try {
                Thread.sleep(2*DELAY_MS);
            } catch (InterruptedException e) {
                LOG.warn(e);
            }

        }
        if (isAuthExists(user, authId)) {
            LOG.info("User {} authId {}", user, authId);
            sessions.put(user, session);
        } else {
            try {
                session.close();
            } catch (IOException e) {
                LOG.warn(e);
            }
        }
    }

    private void sendTextToRemote(String sessionId, String text) {
        String user = sessions.getAuth(sessionId);
        if (null != user) {
            RemoteEndpoint.Async remote = sessions.get(user).getAsyncRemote();
            remote.sendText(text);
        }
    }

    private void greeting(String user, Session session) {
        Objects.requireNonNull(session);
        sendTextToRemote(session.getId(), "Server> Hello " + user);
        sendTextToRemote(session.getId(),
            "Server> You are now connected to " + this.getClass().getName()
        );
    }

    @Override
    public void onOpen(Session session, EndpointConfig endpointConfig) {
        LOG.warn("Session opened, id: {}", session.getId());

        // attach message handler
        session.addMessageHandler(new StringHandler(endpointConfig, session) {

            @Override
            public void onMessage(String message) {
                String sid = session.getId();

                LOG.info("Message received. Session id: {} Message: {}", sid, message );

                Map<String, String> map = new HashMap<>();
                //noinspection unchecked
                map = GSON.fromJson(message, map.getClass());

                String user = map.getOrDefault("auth", null);
                int authId = Integer.parseInt(
                    map.getOrDefault("authid", null)
                );
                LOG.info("Get from html form auth:{} authId:{}", user, authId);
                String text = map.getOrDefault("text", null);

                if ( ! sessions.containsAuth(user)) {
                    doAuth(user, authId, session);
                    greeting(user, session);
                    // TODO chat
                } else if (null != text) {
                    client.send(new UserToChatMsg(address, sid, address, user, text));
                    // TODO chat
                }
            }
        });
    }

    @Override
    public void onClose(Session session, CloseReason close) {
        String user = sessions.getAuth(session.getId());
        sessions.remove(user);
        auths.remove(user);

        LOG.warn(
            "WebSocket Close session({}): {} - {}, user:{}",
            session.getId(), close.getCloseCode(), close.getReasonPhrase(), user
        );
        super.onClose(session, close);
    }

    @Override
    public boolean knowsHisAddress(Address address) {
        return this.address.equals(address); // || addresses.containsKey(address);
    }

    @Override
    public Address getAddress() {
        return address;
    }

    @Override
    public void setChatEndpoint(FrontEndpoint chat) { /* None TODO */ }

    @Override
    public void deliver(Msg msg) {
        String mid = msg.getId();
        LOG.debug("Message is delivered: {}", msg.toString());

        if (RequestDBServerMsg.ID.equals(mid)) {
            dbServerAddress = msg.getFrom();
            LOG.warn("Got DB Server Address: {}", dbServerAddress);
        } else if (AuthenticatedMsg.ID.equals(mid)) {
            AuthenticatedMsg authenticated = (AuthenticatedMsg) msg;
            auths.put(authenticated.getUser(), authenticated.getAuth());

            LOG.warn(
                "Message is delivered and put user:{} auth:{}",
                authenticated.getUser(), authenticated.getAuth()
            );
        } else if (ChatToUsersMsg.ID.equals(mid)) {
            ChatToUsersMsg chatMsg = (ChatToUsersMsg) msg;

            for (Map.Entry<String, Session> entry : sessions.entrySet()) {
                Session session = entry.getValue();
                String outputMessage = chatMsg.getUser() + "> " + chatMsg.getText();
                sendTextToRemote(session.getId(), outputMessage);
            }
        }
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
