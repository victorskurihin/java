package ru.otus.services;

/*
 * Created by VSkurikhin at autumn 2018.
 */

import java.sql.SQLException;

public interface DataOrigin
{
    boolean isReady();

    void fetchData() throws SQLException;

    String getDataXML();

    String getDataJSON();
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
