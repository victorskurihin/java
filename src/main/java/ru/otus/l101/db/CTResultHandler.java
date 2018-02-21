package ru.otus.l101.db;

import ru.otus.l101.dataset.DataSet;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@FunctionalInterface
public interface CTResultHandler<T extends DataSet> {
    List<T> handle(ResultSet resultSet) throws SQLException;
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
