package ru.otus.homework.services;

import org.springframework.shell.table.TableBuilder;

public interface BooksService
{
    TableBuilder getTableBuilder();

    int createTableForFindAll();

    int createTableForFindAllWithDetail();

    long insert(String isbn, String title, int editionNumber, String copyright, long publisherId);

    long update(long id, String isbn, String title, int editionNumber, String copyright, long publisherId);

    void delete(long id);
}
