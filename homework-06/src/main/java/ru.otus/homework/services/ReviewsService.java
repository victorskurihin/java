package ru.otus.homework.services;

import java.util.List;

public interface ReviewsService extends RepositoryService
{
    List<String[]> findByReview(String review);

    long insert(String review);

    long update(long id, String review);
}
