package ru.otus.l151.app;

import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;

import javax.websocket.*;

public class ChatEndpoint
    extends FrontendEndpoint implements MessageHandler.Whole<String> {

    private static final Logger LOG = Log.getLogger(ChatEndpoint.class);
    private Session session;
    private RemoteEndpoint.Async remote;

    public ChatEndpoint() {
        LOG.info("class loaded {}", this.getClass());
    }

    @Override
    public void onOpen(Session session, EndpointConfig endpointConfig) {
        LOG.info("Session opened, id: {}", session.getId());
        System.err.printf("Session opened, id: %s%n", session.getId());

        this.session = session;
        this.remote = this.session.getAsyncRemote();
        // attach echo message handler
        session.addMessageHandler(this);

        this.remote.sendText("You are now connected to " + this.getClass().getName());
        // session.getBasicRemote().sendText("Hi there, we are successfully connected.");
    }

    @Override
    public void onClose(Session session, CloseReason close) {
        super.onClose(session, close);
        this.session = null;
        this.remote = null;
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
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
