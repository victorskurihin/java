/*
 * InsideEndpoint.java
 * This file was last modified at 2018.12.03 20:05 by Victor N. Skurikhin.
 * $Id$
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */

package ru.otus.server.websockes;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.otus.server.services.CachedUpdater;
import ru.otus.server.services.DataBroadcaster;
import ru.otus.server.servlets.ServletAwareConfig;

import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;

import static ru.otus.shared.Constants.BROADCASTER_INSIDE_SERVICE;
import static ru.otus.shared.Constants.ENDPOINT_INSIDE;

@ServerEndpoint(value = "/" + ENDPOINT_INSIDE, configurator = ServletAwareConfig.class)
public class InsideEndpoint
{
    private static final Logger LOGGER = LogManager.getLogger(InsideEndpoint.class.getName());
    private DataBroadcaster broadcaster;
    private EndpointConfig config;

    @OnMessage
    public void onMessage(Session session, String msg)
    {
        //provided for completeness, in out scenario clients don't send any msg.
        try {
            LOGGER.info("Received msg {} from {}.", msg, session.getId());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private DataBroadcaster getBroadcaster()
    {
        HttpSession httpSession = (HttpSession) config.getUserProperties().get("httpSession");
        return (DataBroadcaster) httpSession.getServletContext().getAttribute(BROADCASTER_INSIDE_SERVICE);
    }

    @OnOpen
    public void open(Session session, EndpointConfig config)
    {
        this.config = config;
        this.broadcaster = getBroadcaster();

        if (null != broadcaster) {
            broadcaster.registerDataUpdater(session, new CachedUpdater());
            LOGGER.info("New session opened: {}", session.getId());
        }
    }

    @OnError
    public void error(Session session, Throwable t)
    {
        if (null != broadcaster) {
            broadcaster.unregisterDataUpdater(session);
            LOGGER.error("Error on session {}", session.getId());
        }
    }

    @OnClose
    public void closedConnection(Session session)
    {
        if (null != broadcaster) {
            broadcaster.unregisterDataUpdater(session);
            LOGGER.info("Session closed: {}", session.getId());
        }
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
