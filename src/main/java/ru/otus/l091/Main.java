package ru.otus.l091;

import java.sql.*;

public class Main {
    public static void main(String[] args) throws Exception {
        try (DBService dbService = new DBServiceLog()) {
            dbService.createTables(UsersDataSet.class);
            dbService.save(new UsersDataSet(1, "user", 35));
        }
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
