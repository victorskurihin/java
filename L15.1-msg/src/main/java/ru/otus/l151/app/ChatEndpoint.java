package ru.otus.l151.app;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;
import ru.otus.l151.dataset.UserDataSet;
import ru.otus.l151.messageSystem.ControlBlock;

import javax.websocket.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ChatEndpoint extends FrontendEndpoint  {

    private static final Logger LOG = Log.getLogger(ChatEndpoint.class);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    private final AuthenticatedSessions sessions = new AuthenticatedSessions();

    public ChatEndpoint() {
        LOG.info("class loaded {}", this.getClass());
    }

    private void doAuth(String user, Session session) {
        for(int i = 0; i < 9 && ! isUserExists(user); ++i) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                LOG.warn(e);
            }
        }
        if (isUserExists(user)) {
            sessions.put(user, session);
        } else {
            try {
                session.close();
            } catch (IOException e) {
                LOG.info(e);
            }
        }
    }

    private void sendTextToRemote(String sessionId, String text) {
        String user = sessions.getAuth(sessionId);
        RemoteEndpoint.Async remote = sessions.get(user).getAsyncRemote();
        remote.sendText(text);
    }

    private void greeting(String user, Session session) {
        sendTextToRemote(session.getId(), "Hello " + user + "<br>");
        sendTextToRemote(session.getId(),
            "You are now connected to " + this.getClass().getName()
        );
    }

    @Override
    public void onOpen(Session session, EndpointConfig endpointConfig) {
        LOG.info("Session opened, id: {}", session.getId());

        // attach message handler
        session.addMessageHandler(new StringHandler(endpointConfig, session) {

            @Override
            public void onMessage(String message) {
                LOG.info(
                    "Message received. Session id: {} JSON: {}",
                    session.getId(), message
                );
                Map<String, String> map = new HashMap<>();
                //noinspection unchecked
                map = GSON.fromJson(message, map.getClass());

                String user = map.getOrDefault("auth", null);
                String text = map.getOrDefault("text", null);

                if ( ! sessions.containsAuth(user)) {
                    doAuth(user, session);
                    greeting(user, session);
                    // TODO chat
                } else {
                    // TODO chat
                }
            }
        });

    }

    @Override
    public void onClose(Session session, CloseReason close) {
        sessions.removeBySessionId(session.getId());
        LOG.warn(
            "WebSocket Close session({}): {} - {}",
            session.getId(), close.getCloseCode(), close.getReasonPhrase()
        );
        super.onClose(session, close);
    }

    @Override
    public void deliverUserDataSet(ControlBlock control, UserDataSet user) {
        LOG.info("deliverUserDataSet Chat User: {} exists!", user);
        super.deliverUserDataSet(control, user);
    }
/*
    private void doAuth(String user) {
        for(int i = 0; i < 9 && ! isUserExists(user); ++i) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                LOG.warn(e);
            }
        }
        if (isUserExists(user)) {
            auths.add(user);
            remote.sendText("Hello " + user + "<br>");
            remote.sendText("You are now connected to " + this.getClass().getName());
        } else {
            try {
                session.close(); // Timeout???
            } catch (IOException e) {
                LOG.warn(e);
            }
        }
    }
*/
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
