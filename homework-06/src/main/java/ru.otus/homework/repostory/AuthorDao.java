package ru.otus.homework.repostory;

import ru.otus.homework.models.Author;
import ru.otus.homework.repostory.Dao;

import java.util.List;

public interface AuthorDao extends Dao<Author>
{
    List<Author> findByFirstName(String firstName);

    List<Author> findByLastName(String lastName);
}
