package ru.otus.homework.services;

import org.springframework.shell.table.TableBuilder;

public interface PublishersService
{
    TableBuilder getTableBuilder();

    int createTableForFindAll();

    int createTableAllBookWithDetail();

    long insert(String publisherName);

    long update(long id, String publisherName);

    void delete(long id);
}
