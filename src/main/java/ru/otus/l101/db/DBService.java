package ru.otus.l101.db;

import ru.otus.l101.dataset.DataSet;

import java.sql.Connection;
import java.util.List;

public interface DBService extends AutoCloseable {
    Connection getConnection();
    <T extends DataSet> void createTables(Class<T> clazz);
    <T extends DataSet> void save(T user);
    <T extends DataSet> T load(long id, Class<T> clazz);
    <T extends DataSet> List<T> readAll();
    void shutdown();
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
