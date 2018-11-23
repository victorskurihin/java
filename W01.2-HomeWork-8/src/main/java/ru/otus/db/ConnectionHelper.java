package ru.otus.db;

/*
 * Created by VSkurikhin at winter 2018.
 */

import ru.otus.exeptions.ExceptionSQL;

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
        } catch (SQLException e) {
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
