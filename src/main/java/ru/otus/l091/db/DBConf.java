package ru.otus.l091.db;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConf {
    public static final String userSchemaName = "mb24681";
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
        ResultHandler handler;
        String dropTablesSQL = "DROP TABLE IF EXISTS %s CASCADE";

        try (Statement stmt = connection.createStatement()) {
            stmt.execute(String.format(dropTablesSQL, tableName));
        }
    }
}
