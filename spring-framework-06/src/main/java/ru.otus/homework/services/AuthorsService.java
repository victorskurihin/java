package ru.otus.homework.services;

import java.util.List;

public interface AuthorsService
{
    List<String[]> findAll();

    List<String[]> findById(long id);

    List<String[]> findByFirstName(String firstName);

    List<String[]> findByLastName(String lastName);

    long insert(String firstName, String lastName);

    long update(long id, String firstName, String lastName);

    void delete(long id);
}
