package ru.otus.l151.app;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;
import ru.otus.l151.db.MsgNewUser;
import ru.otus.l151.messageSystem.Message;

import javax.websocket.*;
import java.util.HashMap;
import java.util.Map;

public class AuthEndpoint
    extends FrontendEndpoint implements MessageHandler.Whole<String> {

    private static final Logger LOG = Log.getLogger(AuthEndpoint.class);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private Session session;
    private RemoteEndpoint.Async remote;
    private boolean signup = true;

    public AuthEndpoint() {
        LOG.info("class loaded {}", this.getClass());
    }

    @Override
    public void onOpen(Session session, EndpointConfig endpointConfig) {
        LOG.info("Session opened, id: {}", session.getId());

        this.session = session;
        remote = this.session.getAsyncRemote();
        // attach echo message handler
        session.addMessageHandler(this);

        remote.sendText(GSON.toJson(
            "You are now connected to " + this.getClass().getName()
        ));
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

    private Map<String, String> getOkResult() {
        Map<String, String> result = new HashMap<>();
        result.put("result", "ok");
        result.put("session", session.getId());
        return result;
    }

    private Map<String, String> getErrorResult() {
        Map<String, String> result = new HashMap<>();
        result.put("result", "Error!");
        return result;
    }

    private Map<String, String> getErrorResult(String msg) {
        Map<String, String> result = new HashMap<>();
        result.put("result", "Error!" + msg);
        return result;
    }

    private void singup(String username, String password) {
        signup = true;
        if (null != username && null != password) {
            LOG.info("singup User: {} password: {}", username, password);
            handleRequest(msgNewUser(username, password));
        }
    }

    private void authenticate(String username, String password) {
        signup = false;
        if (null != username && null != password) {
            LOG.info("authenticate User: {} password: {}", username, password);
            handleRequest(msgGetUser(username, password));
        }
    }

    @Override
    public void onMessage(String message) {
        LOG.info(
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
            case "singup":
                singup(username, password);
                break;
            case "auth": authenticate(username, password);
        }
    }

    @Override
    public void idUser(long id, String name) {
        if (signup) {
            if (isUserExists(name)) {
                remote.sendText(GSON.toJson(getErrorResult("User exists!")));
                return;
            }
        } else {
            if ( ! isUserExists(name)) {
                remote.sendText(GSON.toJson(getErrorResult("User doesn't exist!")));
                return;
            }
        }
        super.idUser(id, name);
    }

    @Override
    public void idPassword(long id, String password) {
        if (! signup && id < 0) {
            remote.sendText(GSON.toJson(getErrorResult("Wrong password!")));
            return;
        }
        super.idPassword(id, password);
        remote.sendText(GSON.toJson(getOkResult()));
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
