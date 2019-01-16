package ru.otus.homework.repository;

import ru.otus.homework.models.DataSet;

import java.util.List;

public interface Dao<T extends DataSet>
{
    String[] FIND_ALL_HEADER = {};

    List<T> findAll();

    T findById(long id);

    void insert(T entity);

    void update(T entity);

    void delete(long id);
}
