package ru.otus.homework.services;

import java.util.List;

public interface GenreService
{
    List<String[]> findAll();

    List<String[]> findById(long id);

    List<String[]> findByGenre(String genre);

    long insert(String genre);

    long update(long id, String genre);

    void delete(long id);
}
