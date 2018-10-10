package ru.otus.db;

/*
 * Created by VSkurikhin at winter 2018.
 */

import java.sql.Connection;

public class DBConf
{
    public static final String userName = "dbuser";
    public static final String dbName = "db";
    public static final String password = "password";
    private final Connection connection;

    public DBConf(Connection connection)
    {
        this.connection = connection;
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
