package ru.otus.l151.app;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;

import javax.websocket.*;

public abstract class StringHandler implements MessageHandler.Whole<String> {

    private static final Logger LOG = Log.getLogger(StringHandler.class);

    private Session session;
    private EndpointConfig config;
    private RemoteEndpoint.Async remote;

    public StringHandler(EndpointConfig config, Session session) {
        LOG.debug("class loaded {}", this.getClass());
        this.config = config;
        this.session = session;
        remote = this.session.getAsyncRemote();
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
