package ru.otus.homework.services;

import ru.otus.homework.models.Book;

import java.util.List;

public interface BooksService extends FindService<Book>
{
    Book findByIsbn(String isbn);

    List<Book> findByTitle(String title);

    List<Book> findAllBooksAndTheirAuthors();

    long insert(String isbn, String title, int editionNumber, String copyright, long publisherId, long genreId);

    long update(long id, String isbn, String title, int editionNumber, String copyright, long publisherId, long genreId);

    void delete(long id);
}
