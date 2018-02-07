package ru.otus.l091;

import java.sql.ResultSet;
import java.sql.SQLException;

@FunctionalInterface
public interface TResultHandler<T extends DataSet> {
    T handle(ResultSet resultSet) throws SQLException;
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
