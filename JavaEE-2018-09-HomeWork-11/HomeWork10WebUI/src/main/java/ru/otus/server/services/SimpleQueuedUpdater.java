/*
 * SimpleQueuedUpdater.java
 * This file was last modified at 2018.12.03 20:05 by Victor N. Skurikhin.
 * $Id$
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */

package ru.otus.server.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.websocket.Session;
import java.util.Map;

public class SimpleQueuedUpdater implements DataUpdater
{
    private static final Logger LOGGER = LogManager.getLogger(SimpleQueuedUpdater.class.getName());

    private Session session;
    private Map<String, DataOrigin> sources;

    public Session getSession()
    {
        return session;
    }

    @Override
    public void setSession(Session session)
    {
        this.session = session;
    }

    @Override
    public void setSources(Map<String, DataOrigin> sources)
    {
        this.sources = sources;
    }

    protected void sendDataFromOrigin(String name, DataOrigin origin)
    {
        try {
            /* Send the new rate to all open WebSocket sessions */
            if ( ! session.isOpen()) {
                LOGGER.error("Closed session: {}", session.getId());
            }
            else {
                String msg = origin.getDataJSON();
                session.getBasicRemote().sendText(msg);
                LOGGER.info("Sending {} to session: {}", msg, session.getId());
            }
        }
        catch (Throwable e) {
            LOGGER.info("sendDataFromOrigin: catch({}): {}", e.getClass(), e);
        }
    }

    @Override
    public void handleOnDataUpdate()
    {
        if (null == session || null == sources) {
            LOGGER.error("handleOnDataUpdate not property set session: {} source {}", null != session, null != sources);
            return;
        }
        sources.forEach(this::sendDataFromOrigin);
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
