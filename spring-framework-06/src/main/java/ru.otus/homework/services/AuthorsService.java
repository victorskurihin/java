package ru.otus.homework.services;

import org.springframework.shell.table.TableBuilder;

public interface AuthorsService
{
    TableBuilder getTableBuilder();

    int createTableForFindAll();

    long insert(String firstName, String lastName);

    long update(long id, String firstName, String lastName);

    void delete(long id);
}
