package ru.otus.l091;

import java.sql.*;

class DBConf {
    public static final String userSchemaName = "mb24681";
    private final Connection connection;

    protected DBConf(Connection connection) {
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

public class Main {

    public static void main(String[] args) throws Exception {
        try (DBService dbService = new DBServiceLog()) {
            DBConf dbConf = new DBConf(dbService.getConnection());
            dbConf.dropTables(Loader.classGetNameToTableName(UsersDataSet.class));
            dbConf.dropTables(Loader.classGetNameToTableName(ComplexDataSet.class));
            dbService.createTables(ComplexDataSet.class);
            ComplexDataSet complexDataSet = new ComplexDataSet(1);
            complexDataSet.setF0(false);
            complexDataSet.setF1((byte) 10);
            complexDataSet.setF2('a');
            complexDataSet.setF3((short) 30);
            complexDataSet.setF4(40);
            complexDataSet.setF5(50);
            dbService.save(complexDataSet);
            ComplexDataSet restoredComplexDataSet = dbService.load(1, ComplexDataSet.class);
            System.out.println("restoredComplexDataSet = " + restoredComplexDataSet);
        }
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
