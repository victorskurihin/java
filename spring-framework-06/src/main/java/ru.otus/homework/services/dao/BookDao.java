package ru.otus.homework.services.dao;

import ru.otus.homework.models.Author;
import ru.otus.homework.models.Book;

import java.util.List;
import java.util.Map;

public interface BookDao extends Dao<Book>
{
    Book findByIsbn(String isbn);

    List<Book> findByTitle(String title);

    Map<Book, Author> findAllBooksAndTheirAuthors();
}
