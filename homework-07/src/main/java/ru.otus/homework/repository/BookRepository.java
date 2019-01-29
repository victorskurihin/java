package ru.otus.homework.repository;

import org.springframework.data.repository.CrudRepository;
import ru.otus.homework.models.Book;

import java.util.List;

public interface BookRepository extends CrudRepository<Book, Long>
{
    Book findByIsbn(String isbn);

    List<Book> findByTitle(String title);

    List<Book> findAll();
}
