package ru.otus.homework.services;

import java.util.List;

public interface PublishersService
{
    List<String[]> findAll();

    List<String[]> findById(long id);

    List<String[]> findByPublisherName(String publisherName);

    long insert(String publisherName);

    long update(long id, String publisherName);

    void delete(long id);
}
