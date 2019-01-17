package ru.otus.homework.services;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import ru.otus.homework.models.Review;
import ru.otus.homework.repository.ReviewRepositoryJpa;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewsServiceImpl implements ReviewsService
{
    private ReviewRepositoryJpa reviewRepository;

    public ReviewsServiceImpl(ReviewRepositoryJpa reviewRepository)
    {
        this.reviewRepository = reviewRepository;
    }

    static String[] unfoldReview(Review r)
    {
        if (null == r) {
            return new String[]{"NULL", "NULL"};
        }

        return new String[]{Long.toString(r.getId()), r.getReview()};
    }

    private String[] unfold(Review g)
    {
        return unfoldReview(g);
    }

    @Override
    public List<String[]> findAll()
    {
        List<String[]> head = new ArrayList<>();
        head.add(ReviewRepositoryJpa.FIND_ALL_HEADER);

        List<String[]> tail = reviewRepository.findAll().stream().map(this::unfold).collect(Collectors.toList());
        head.addAll(tail);

        return head;
    }

    @Override
    public List<String[]> findById(long id)
    {
        List<String[]> head = new ArrayList<>();
        head.add(ReviewRepositoryJpa.FIND_ALL_HEADER);

        try {
            Review review;
            review = reviewRepository.findById(id);
            head.add(unfold(review));

            return head;
        }
        catch (EmptyResultDataAccessException e) {
            return head;

        }
    }

    @Override
    public List<String[]> findByReview(String review)
    {
        List<String[]> head = new ArrayList<>();
        head.add(ReviewRepositoryJpa.FIND_ALL_HEADER);

        List<String[]> tail = reviewRepository.findByReview(review)
            .stream()
            .map(this::unfold)
            .collect(Collectors.toList());
        head.addAll(tail);

        return head;
    }

    @Override
    public long insert(String value)
    {
        Review review = new Review();
        review.setReview(value);
        reviewRepository.save(review);

        return review.getId();
    }

    @Override
    public long update(long id, String value)
    {
        Review review = reviewRepository.findById(id);
        review.setReview(value);
        reviewRepository.save(review);

        return review.getId();
    }

    @Override
    public void delete(long id)
    {
        reviewRepository.delete(id);
    }
}
