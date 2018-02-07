package ru.otus.l091;

import java.sql.ResultSet;
import java.sql.SQLException;

@FunctionalInterface
public interface TResultHandler<T extends DataSet> {
    T handle(ResultSet resultSet) throws SQLException;
}
