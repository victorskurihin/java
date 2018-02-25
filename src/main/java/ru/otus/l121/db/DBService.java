package ru.otus.l121.db;

import ru.otus.l121.dataset.DataSet;
import ru.otus.l121.dataset.UserDataSet;

import java.sql.Connection;
import java.util.List;

public interface DBService extends AutoCloseable {

    /**
     * TODO
     * @return
     */
    String getLocalStatus();

    /**
     * TODO
     * @return
     */
    Connection getConnection();

    /**
     * TODO
     * @param user
     * @param <T>
     */
    <T extends DataSet> void save(T user);

    /**
     * TODO
     * @param id
     * @param clazz
     * @param <T>
     * @return
     */
    <T extends DataSet> T load(long id, Class<T> clazz);

    /**
     * TODO
     * @param name
     * @return
     */
    UserDataSet loadByName(String name);

    /**
     * TODO
     * @return
     */
    List<UserDataSet> loadAll();

    /**
     * TODO
     * @return
     */
    int getHitCount();

    /**
     * TODO
     * @return
     */
    int getMissCount();

    void shutdown();
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
