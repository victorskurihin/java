package ru.otus.homework.services;

import java.util.List;

public interface AuthorsService extends RepositoryService
{
    List<String[]> findByFirstName(String firstName);

    List<String[]> findByLastName(String lastName);

    long insert(String firstName, String lastName);

    long update(long id, String firstName, String lastName);

    long updateFirstName(long id, String firstName);

    long updateLastName(long id, String lastName);
}
