package ru.otus.l111.exeption;

import java.sql.SQLException;

public class RuntimeSQLException extends RuntimeException {
    public RuntimeSQLException(SQLException e) {
        super(e);
        e.printStackTrace();
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
