package ru.otus.homework.repository;

import org.springframework.data.repository.CrudRepository;
import ru.otus.homework.models.Review;

import java.util.List;

public interface ReviewRepository extends CrudRepository<Review, Long>
{
    List<Review> findByReview(String value);

    List<Review> findAll();
}
