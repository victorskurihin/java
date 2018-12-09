/*
 * ConnectionHelper.java
 * This file was last modified at 2018.12.03 20:05 by Victor N. Skurikhin.
 * $Id$
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */

package ru.otus.db;

import ru.otus.exceptions.ExceptionSQL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionHelper
{
    static Connection getConnection(String url)
    {
        try {
            DriverManager.registerDriver(new org.postgresql.Driver());
            return DriverManager.getConnection(url);
        }
        catch (SQLException e) {
            throw new ExceptionSQL(e);
        }
    }

    public static Connection getConnection(
            String host, String port, String db, String user, String password)
    {
        return getConnection(String.format(
                "jdbc:postgresql://%s:%s/%s?user=%s&password=%s",
                host, port, db, user, password
        ));
    }

    public static Connection getConnection(String host, String db, String user, String password)
    {
        return getConnection(String.format(
                "jdbc:postgresql://%s:5432/%s?user=%s&password=%s",
                host, db, user, password
        ));
    }

    public static Connection getConnection(String db, String user, String password)
    {
        return getConnection(String.format(
                "jdbc:postgresql://localhost:5432/%s?user=%s&password=%s",
                db, user, password
        ));
    }

    public static Connection getConnection(String db, String user)
    {
        return getConnection(String.format(
                "jdbc:postgresql://localhost:5432/%s?user=%s", db, user
        ));
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
