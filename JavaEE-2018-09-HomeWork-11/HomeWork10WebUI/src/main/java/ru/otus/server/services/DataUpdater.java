/*
 * DataUpdater.java
 * This file was last modified at 2018.12.03 20:05 by Victor N. Skurikhin.
 * $Id$
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */

package ru.otus.server.services;

import javax.websocket.Session;
import java.util.Map;

public interface DataUpdater
{
    void setSession(Session session);

    void setSources(Map<String, DataOrigin> sources);

    void handleOnDataUpdate();
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
