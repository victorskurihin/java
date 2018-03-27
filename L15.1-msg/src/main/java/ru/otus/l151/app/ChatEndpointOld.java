package ru.otus.l151.app;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;
import ru.otus.l151.dataset.UserDataSet;
import ru.otus.l151.messageSystem.ControlBlock;

import javax.websocket.CloseReason;
import javax.websocket.EndpointConfig;
import javax.websocket.RemoteEndpoint;
import javax.websocket.Session;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

public class ChatEndpointOld
    extends FrontendEndpoint implements StringHandler.Whole<String> {

    private static final Logger LOG = Log.getLogger(ChatEndpointOld.class);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    private Session session;
    private RemoteEndpoint.Async remote;
    private String auth = null;

    //private final Map<String, Long> auths = new ConcurrentHashMap<>();
    private final Set<String> auths = new ConcurrentSkipListSet<>();
    private final Map<String, Session> mapAuths = new ConcurrentHashMap<>();

    public ChatEndpointOld() {
        LOG.info("class loaded {}", this.getClass());
    }

    @Override
    public void onOpen(Session session, EndpointConfig endpointConfig) {
        LOG.info("Session opened, id: {}", session.getId());

        this.session = session;
        remote = this.session.getAsyncRemote();
        // attach echo message handler
        session.addMessageHandler(this);

        try {
            session.getBasicRemote().sendText("onOpen");
        } catch (IOException e) {
            LOG.debug(e);
        }
    }

    @Override
    public void onClose(Session session, CloseReason close) {
        super.onClose(session, close);
        this.session = null;
        remote = null;
        LOG.info(
            "WebSocket Close session({}): {} - {}",
            session.getId(), close.getCloseCode(), close.getReasonPhrase()
        );
    }

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
        if ( ! auths.contains(user)) {
            doAuth(user);
            // TODO chat
        } else {
            LOG.info(
                "Session id: {} Message: {} from user {}",
                session.getId(), text, user
            );
            // TODO chat
        }
    }

    @Override
    public void deliverUserDataSet(ControlBlock control, UserDataSet user) {
        LOG.info("deliverUserDataSet Chat User: {} exists!", user);
        super.deliverUserDataSet(control, user);
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
