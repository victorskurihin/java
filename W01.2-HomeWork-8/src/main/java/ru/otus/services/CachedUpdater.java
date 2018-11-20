package ru.otus.services;

/*
 * Created by VSkurikhin at autumn 2018.
 */

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Hashtable;

public class CachedUpdater extends SimpleQueuedUpdater
{
    private static final Logger LOGGER = LogManager.getLogger(CachedUpdater.class.getName());
    private Hashtable<DataOrigin, Integer> hashes = new Hashtable<>();
    private Hashtable<DataOrigin, Integer> sizes = new Hashtable<>();

    @Override
    protected void sendDataFromOrigin(String name, DataOrigin origin)
    {
        try {
            /* Send the new rate to all open WebSocket sessions */
            if( ! getSession().isOpen()) {
                LOGGER.error("Closed session: {}", getSession().getId());
            }
            else {
                String msg = origin.getDataJSON();
                int hash = hashes.getOrDefault(origin, 0);
                int size = sizes.getOrDefault(origin, 0);
                if (msg.length() != size || msg.hashCode() != hash) {
                    hashes.put(origin, msg.hashCode());
                    sizes.put(origin, msg.length());
                    LOGGER.info("Prepare Sending size:{}, hash:{} to session: {}", size, hash, getSession().getId());
                    getSession().getBasicRemote().sendText(msg);
                    LOGGER.info("Sending {} to session: {}", msg.substring(0, 100), getSession().getId());
                }
            }
        } catch (Throwable e) {
            LOGGER.info("sendDataFromOrigin: catch({}): {}", e.getClass(), e);
            e.printStackTrace();
        }
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
