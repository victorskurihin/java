package ru.otus.homework.services;

import java.util.List;

public interface BooksService
{
    List<String[]> findAll();

    List<String[]> findById(long id);

    List<String[]> findByIsbn(String isbn);

    List<String[]> findByTitle(String title);

    List<String[]> findAllBooksAndTheirAuthors();

    long insert(String isbn, String title, int editionNumber, String copyright, long publisherId, long genreId);

    long update(long id, String isbn, String title, int editionNumber, String copyright, long publisherId, long genreId);

    void delete(long id);
}
