package ru.otus.l151.front;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;

import ru.otus.l151.app.StringHandler;
import ru.otus.l151.dataset.UserDataSet;
import ru.otus.l151.messageSystem.ControlBlock;

import javax.websocket.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class AuthEndpoint extends FrontendEndpoint {

    private static final String AUTH = "auth";
    private static final String SINGUP = "singup";
    private static final String K_FORM = ControlBlock.Keys.FORM.toString();
    private static final String K_LOGIN = ControlBlock.Keys.LOGIN.toString();
    private static final String K_PASSWORD = ControlBlock.Keys.PASSWORD.toString();
    private static final String K_SESSION = ControlBlock.Keys.SESSION.toString();
    private static final Logger LOG = Log.getLogger(AuthEndpoint.class);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    private final Map<String, RemoteEndpoint.Async> sessions = new ConcurrentHashMap<>();

    public AuthEndpoint() {
        LOG.debug("class loaded {}", this.getClass());
    }

    void toHandleRequest(String login, String password, String form, String sessionId) {
        ControlBlock control = new ControlBlock();
        control.put(K_FORM, form);
        control.put(K_LOGIN, login);
        control.put(K_PASSWORD, password);
        control.put(K_SESSION, sessionId);
        handleRequest(msgGetUserDataSet(control, login));
    }

    private void singup(String login, String password, Session session) {
        if (null != login) {
            LOG.warn("singup User: {}", login);
            toHandleRequest(login, password, SINGUP, session.getId());
        }
    }

    private void authenticate(String login, String password, Session session) {
        if (null != login && null != password) {
            LOG.debug("authenticate User: {} password: {}", login, password);
            toHandleRequest(login, password, AUTH, session.getId());
        }
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

    private Map<String, String> getOkResult(String sessionId) {
        Map<String, String> result = new HashMap<>();
        result.put("result", "ok");
        result.put("session", sessionId);
        return result;
    }

    private Map<String, String> getErrorResult(String msg) {
        Map<String, String> result = new HashMap<>();
        result.put("result", "Error! " + msg);
        return result;
    }

    private void sendJsonToRemote(String sessionId, String jsonString) {
        RemoteEndpoint.Async remote = sessions.get(sessionId);
        remote.sendText(jsonString);
    }

    @Override
    public void deliverUserDataSet(ControlBlock control, UserDataSet user) {
        if (null == control) { /* TODO Execption */ return; }
        String sessionId = control.get(K_SESSION);
        LOG.debug("deliverUserDataSet Form: {}", control.get(K_FORM));
        switch (control.getOrDefault(K_FORM)) {
            case SINGUP:
                if (null != user) {
                    LOG.info("deliverUserDataSet User: {} exists!", user);
                    sendJsonToRemote(
                        sessionId, GSON.toJson(getErrorResult("User exists!"))
                    );
                } else {
                    LOG.info("New User: {}",control.get(K_LOGIN));
                    control.put(K_FORM, AUTH);
                    user = new UserDataSet(control.get(K_LOGIN), control.get(K_PASSWORD));
                    handleRequest(msgNewUserDataSet(control, user));
                }
                break;
            case AUTH:
                super.deliverUserDataSet(control, user);
                if (! auth(user.getName(), control.getOrDefault(K_PASSWORD)))  {
                    LOG.info("deliverUserDataSet Bad User {} or password!", user);
                    sendJsonToRemote(sessionId,
                        GSON.toJson(getErrorResult("Bad User or password!"))
                    );
                } else {
                    handleRequest(msgChatUserDataSet(control, user.getName()));
                    sendJsonToRemote(sessionId, GSON.toJson(getOkResult(sessionId)));
                }
                break;
        }
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
