package ru.otus.services;

/*
 * Created by VSkurikhin at autumn 2018.
 */

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
