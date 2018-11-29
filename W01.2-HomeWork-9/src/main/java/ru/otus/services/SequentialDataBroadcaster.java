/*
 * SequentialDataBroadcaster.java
 * This file was last modified at 29.11.18 11:11 by Victor N. Skurikhin.
 * $Id$
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */

package ru.otus.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.websocket.Session;

import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static ru.otus.gwt.shared.Constants.DEFAULT_UPDATE_PERIOD;

public class SequentialDataBroadcaster implements DataBroadcaster
{
    private static final Logger LOGGER = LogManager.getLogger(SequentialDataBroadcaster.class.getName());

    private final ConcurrentHashMap<String, DataOrigin> SOURCES = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<Session, DataUpdater> HANDLERS = new ConcurrentHashMap<>();

    private boolean running = false;
    private int updatePeriod = DEFAULT_UPDATE_PERIOD;
    private Thread thread = null;

    public synchronized boolean isRunning()
    {
        return running;
    }

    private synchronized void setRunning(boolean running)
    {
        this.running = running;
    }

    public int getUpdatePeriod()
    {
        return updatePeriod;
    }

    public void setUpdatePeriod(int updatePeriod)
    {
        this.updatePeriod = updatePeriod;
    }

    @Override
    public void registerDataOrigin(String name, DataOrigin origin)
    {
        SOURCES.put(name, origin);
    }

    @Override
    public void unregisterDataOrigin(String name)
    {
        SOURCES.remove(name);
    }

    @Override
    public void registerDataUpdater(Session session, DataUpdater updater)
    {
        updater.setSession(session);
        updater.setSources(SOURCES);
        HANDLERS.put(session, updater);
    }

    @Override
    public void unregisterDataUpdater(Session session)
    {
        HANDLERS.remove(session);
    }

    private void fetchForDataOrigin(String name, DataOrigin origin)
    {
        LOGGER.info("fetchForDataOrigin for: {}", name);
        try {
            origin.fetchData();
        } catch (SQLException e) {
            LOGGER.error("fetchForDataOrigin: catch exception {} {}", e.getClass(), e);
            e.printStackTrace();
        }
    }

    private void sendToSession(Session session, DataUpdater updater)
    {
        LOGGER.info("sendToSession for: {}", session.getId());
        updater.handleOnDataUpdate();
    }

    private boolean sessionIsClosed(Map.Entry<Session, DataUpdater> entry)
    {
        return ! entry.getKey().isOpen();
    }

    private void run()
    {
        setRunning(true);

        //noinspection InfiniteLoopStatement
        while (isRunning()) {
            long startNs = System.nanoTime();

            LOGGER.info("run: iterate run in {}", this);

            SOURCES.forEach(this::fetchForDataOrigin);
            HANDLERS.forEach(this::sendToSession);
            HANDLERS.entrySet().removeIf(this::sessionIsClosed);

            long delta = (System.nanoTime() - startNs)/1_000_000;
            long delay = (updatePeriod - (delta < updatePeriod ? delta : 0)) / 10;

            try {
                for (int i = 0; i < 10 && isRunning(); ++i)
                    Thread.sleep(delay);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                LOGGER.info("run: catch({}): {}", e.getClass(), e);
            }
        }
    }

    @Override
    public void start()
    {
        thread = new Thread(this::run);
        thread.setDaemon(true);
        thread.start();
    }

    @Override
    public void shutdown() throws InterruptedException
    {
        setRunning(false);
        if (null != thread && thread.isAlive()) {
            Thread.sleep(updatePeriod / 10);
            thread.interrupt();
        }
    }

    @Override
    public void close() throws Exception
    {
        shutdown();
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
