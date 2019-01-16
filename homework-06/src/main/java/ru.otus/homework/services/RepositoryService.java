package ru.otus.homework.services;

import java.util.List;

public interface RepositoryService
{
    List<String[]> findAll();

    List<String[]> findById(long id);

    void delete(long id);
}
