package ru.otus.l151.app;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;

import javax.websocket.*;
import java.util.HashMap;
import java.util.Map;

public class AuthEndpoint
    extends FrontendEndpoint implements MessageHandler.Whole<String> {

    private static final Logger LOG = Log.getLogger(AuthEndpoint.class);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private Session session;
    private RemoteEndpoint.Async remote;

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

    @Override
    public void onMessage(String message) {
        LOG.info(
            "Message received. Session id: {} Message: {}",
            session.getId(), message
        );
        if (true) {
            remote.sendText(GSON.toJson(getOkResult()));
        } else {
            remote.sendText(GSON.toJson(getErrorResult()));
        }
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
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
