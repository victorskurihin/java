/*
 * DataBroadcaster.java
 * This file was last modified at 2018.12.03 20:05 by Victor N. Skurikhin.
 * $Id$
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */

package ru.otus.server.services;

import javax.websocket.Session;

public interface DataBroadcaster extends AutoCloseable
{
    void registerDataOrigin(String name, DataOrigin origin);

    void unregisterDataOrigin(String name);


    void registerDataUpdater(Session session, DataUpdater updater);

    void unregisterDataUpdater(Session session);


    void start();

    void shutdown() throws InterruptedException;
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
