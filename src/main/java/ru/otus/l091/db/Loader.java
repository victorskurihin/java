package ru.otus.l091.db;

import ru.otus.l091.DataSet;

import java.sql.Connection;

/**
 * The executor as a design pattern.
 * The class load the object from the designated table and return this object.
 */
public class Loader {
    private static final String SELECT = "SELECT * FROM %s WHERE id=%s";
    private Connection connection;

    public static String classGetNameToTableName(Class<? extends DataSet> c) {
        return c.getName().replace('.','_');
    }

    public Loader(Connection connection) {
        this.connection = connection;
    }

    public
    <T extends DataSet> T load(long id, Class<T> clazz, TResultHandler<T> handler) {

        try {
            TExecutor execT = new TExecutor(connection);
            String sql = String.format(
                SELECT, classGetNameToTableName(clazz), id
            );

            return execT.execQuery(sql, handler);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
