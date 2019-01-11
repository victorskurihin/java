package ru.otus.homework.services;

import org.springframework.shell.table.TableBuilder;

public interface GenreService
{
    long insert(String genre);

    long update(long id, String genre);

    void delete(long id);
}
