package ru.otus.l151.db;

/*
 * Created by VSkurikhin at winter 2018.
 */

import ru.otus.l151.dataset.DataSet;
import ru.otus.l151.dataset.UserDataSet;

import java.sql.Connection;
import java.util.List;

/**
 * This is contract for the database services classes.
 */
public interface DBService extends AutoCloseable {

    /**
     * Get the current local status of a transaction.
     *
     * @return the current local status
     */
    String getLocalStatus();

    /**
     * Obtain a database connection.
     *
     * @return a database connection
     */
    Connection getConnection();

    /**
     * Save a instance with type of subclass of DataSet to the DB.
     *
     * @param user a instance
     * @param <T> type of subclass of DataSet
     */
    <T extends DataSet> void save(T user);

    /**
     * Originates, loads from the DB and constructs a instance of T by
     * identifier.
     *
     * @param id a identifier
     * @param clazz a class object of type T
     * @param <T> type of subclass of DataSet
     * @return
     */
    <T extends DataSet> T load(long id, Class<T> clazz);

    /**
     * Originates, loads from the DB and constructs a instance of UserDataSet
     * by a mame of user.
     *
     * @param name of user
     * @return a instance
     */
    UserDataSet loadByName(String name);

    /**
     * Originates, loads from the DB and constructs all instances of UserDataSet.
     *
     * @return a list of UserDataSet objects
     */
    List<UserDataSet> loadAll();

    int getUserId(String name);

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
