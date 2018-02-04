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
