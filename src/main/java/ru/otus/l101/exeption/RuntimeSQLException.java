package ru.otus.l101.exeption;

import java.sql.SQLException;

public class RuntimeSQLException extends RuntimeException {
    public RuntimeSQLException(SQLException e) {
        super(e);
        e.printStackTrace();
    }
}
