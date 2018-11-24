package ru.otus.db.dao;

/*
 * Created by VSkurikhin at winter 2018.
 */

import ru.otus.exeptions.ExceptionThrowable;
import ru.otus.models.DataSet;

import java.sql.SQLException;
import java.util.List;

public interface DAOController<E extends DataSet, K>
{
    List<E> getAll() throws ExceptionThrowable;

    E getEntityById(K id) throws ExceptionThrowable;

    E update(E entity) throws ExceptionThrowable;

    boolean delete(K id) throws ExceptionThrowable;

    boolean create(E entity) throws ExceptionThrowable;
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
