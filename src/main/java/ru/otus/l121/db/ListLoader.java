package ru.otus.l121.db;

import ru.otus.l121.dataset.DataSet;
import ru.otus.l121.exeption.RuntimeSQLException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

/**
 * The executor as a design pattern.
 * The class load the object from the designated table and return this object.
 */
public class ListLoader {
    private static final String SELECT = "SELECT * FROM \"%s\" WHERE parent_id=%s";
    private Connection connection;

    public ListLoader(Connection connection) {
        this.connection = connection;
    }

    public
    <T extends DataSet>
    List<T> load(long id, String tableName, ListTResultHandler<T> handler) {

        try {
            ListTExecutor execT = new ListTExecutor(connection);
            String sql = String.format(
                SELECT, tableName, id
            );

            return execT.execQuery(sql, handler);
        } catch (SQLException e) {
            throw new RuntimeSQLException(e);
        }
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
