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
    public abstract List<E> getAll() throws ExceptionThrowable;
    public abstract E getEntityById(K id);
    public abstract E update(E entity);
    public abstract boolean delete(K id);
    public abstract boolean create(E entity);
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
