package ru.otus.homework.services;

import java.util.List;

public interface PublishersService extends RepositoryService
{
    List<String[]> findByPublisher(String publisher);

    long insert(String publisher);

    long update(long id, String publisher);
}
