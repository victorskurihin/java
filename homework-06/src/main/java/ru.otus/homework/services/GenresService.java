package ru.otus.homework.services;

import java.util.List;

public interface GenresService extends RepositoryService
{
    List<String[]> findByGenre(String genre);

    long insert(String genre);

    long update(long id, String genre);
}
