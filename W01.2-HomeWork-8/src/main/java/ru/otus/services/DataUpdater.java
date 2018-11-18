package ru.otus.services;

/*
 * Created by VSkurikhin at autumn 2018.
 */

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
