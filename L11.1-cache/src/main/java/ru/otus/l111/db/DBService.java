package ru.otus.l111.db;

/*
 * Created by VSkurikhin at winter 2018.
 */

import ru.otus.l111.dataset.DataSet;
import ru.otus.l111.dataset.UserDataSet;

import java.sql.Connection;
import java.util.List;

public interface DBService extends AutoCloseable {
    String getLocalStatus();
    Connection getConnection();
    <T extends DataSet> void createTables(Class<T> clazz);
    <T extends DataSet> void save(T user);
    <T extends DataSet> T load(long id, Class<T> clazz);
    UserDataSet loadByName(String name);
    List<UserDataSet> loadAll();
    void shutdown();
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
