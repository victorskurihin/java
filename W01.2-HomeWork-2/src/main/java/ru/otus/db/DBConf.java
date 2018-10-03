package ru.otus.db;

/*
 * Created by VSkurikhin at winter 2018.
 */

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConf {
    public static final String hostname = "localhost";
    public static final String userName = "dbuser";
    public static final String dbName = "db";
    public static final String password = "password";
    private final Connection connection;

    public DBConf(Connection connection) {
        this.connection = connection;
    }

    private void execSimpleSQL(String update) throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(update);
        }
    }

    public void dropTables(String tableName) throws SQLException {
        String dropTablesSQL = "DROP TABLE IF EXISTS %s CASCADE";

        try (Statement stmt = connection.createStatement()) {
            stmt.execute(String.format(dropTablesSQL, tableName));
        }
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
