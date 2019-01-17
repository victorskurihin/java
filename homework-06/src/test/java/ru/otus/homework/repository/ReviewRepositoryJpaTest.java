package ru.otus.homework.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import ru.otus.homework.models.Book;
import ru.otus.homework.models.Review;
import ru.otus.outside.db.JpaDedicatedEntityManagerTest;
import ru.otus.outside.db.JpaSharedEntityManagerTest;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static ru.otus.outside.utils.TestData.createReview31;

@DisplayName("Class ReviewRepositoryJpa")
class ReviewRepositoryJpaTest
{
    private ReviewRepositoryJpa repository;

    @Nested
    @DisplayName("when new default")
    class WhenNew
    {
        @BeforeEach
        void createNew()
        {
            repository = new ReviewRepositoryJpa();
        }

        @Test
        @DisplayName("default values in ReviewRepositoryJpa()")
        void defaults()
        {
            assertThat(repository).hasFieldOrPropertyWithValue("em", null);
        }
    }

    @Nested
    @DisplayName("when mock EntityManager")
    class WhenMock
    {
        private EntityManager entityManager;

        @BeforeEach
        void mockEntityManager()
        {
            entityManager = mock(EntityManager.class);
            repository = new ReviewRepositoryJpa(entityManager);
        }

        @Test
        @DisplayName("Constructor injected values in ReviewRepositoryJpa()")
        void defaults()
        {
            assertThat(repository).hasFieldOrPropertyWithValue("em", entityManager);
        }

        @DisplayName("find by id from table review success")
        @Test
        void findById_success()
        {
            Review expected = new Review();
            expected.setReview("testReview");

            when(entityManager.find(Review.class,1L)).thenReturn(expected);

            Review review = repository.findById(1L);
            assertEquals(expected, review);
        }

        @DisplayName("find by id from table review return null")
        @Test
        void findById_null()
        {
            when(entityManager.find(Review.class,1L)).thenReturn(null);

            Review review = repository.findById(1L);
            assertNull(review);
        }
    }

    @Nested
    @DisplayName("JPA H2 read-only tests for ReviewRepositoryJpa")
    class JpaH2ReadOnlyTests extends JpaSharedEntityManagerTest
    {
        @BeforeEach
        void createNew()
        {
            repository = new ReviewRepositoryJpa(entityManager);
        }

        @Test
        @DisplayName("Constructor injected values in ReviewRepositoryJpa()")
        void defaults()
        {
            assertThat(repository).hasFieldOrPropertyWithValue("em", entityManager);
        }

        @DisplayName("find all records from table review, success")
        @Test
        void findAll_success()
        {
            List<Review> reviewList = repository.findAll();
            assertEquals(3, reviewList.size());
            assertTrue(reviewList.contains(createReview31()));
        }

        @DisplayName("find all records from empty table review, empty list")
        @Test
        void findAll_empty()
        {
            clearBookReview();
            List<Review> reviewList = repository.findAll();
            assertTrue(reviewList.isEmpty());
        }

        @DisplayName("find by id from table review, success")
        @Test
        void findById_success()
        {
            Review expected = createReview31();
            Review review = repository.findById(expected.getId());
            assertEquals(expected, review);
        }

        @DisplayName("when finding by id from table review, id doesn't exist, return null")
        @Test
        void findById_null()
        {
            Review review = repository.findById(Integer.MAX_VALUE);
            assertNull(review);
        }

        @Test
        void findByReview_success()
        {
            Review expected = createReview31();
            List<Review> reviewList = repository.findByReview(expected.getReview());
            assertTrue(reviewList.contains(createReview31()));
        }

        @Test
        void findByReview_empty()
        {
            List<Review> reviewList = repository.findByReview("");
            assertTrue(reviewList.isEmpty());
        }
    }

    @Nested
    @DisplayName("JPA H2 create/update tests for ReviewRepositoryJpa")
    class JpaH2CreateUpdateTests extends JpaDedicatedEntityManagerTest
    {
        @BeforeEach
        void createNew()
        {
            repository = new ReviewRepositoryJpa(entityManager);
        }

        @DisplayName("persists new when save")
        @Test
        void save_persists()
        {
            BookRepositoryJpa bookRepository = new BookRepositoryJpa(entityManager);
            Review review = new Review();
            review.setReview("test");
            runInTransaction(() -> {
                Book book5 = bookRepository.findById(5L);
                book5.getReviews().add(review);
                repository.save(review);
                bookRepository.save(book5);
            });
            assertEquals(review, repository.findById(review.getId()));
        }

        @DisplayName("merge detached object when save")
        @Test
        void save_megre()
        {

            Review review = repository.findById(31L);
            review.setReview("test");
            runInTransaction(() -> repository.save(review));
            assertEquals(review, repository.findById(review.getId()));
        }

        @Test
        void delete()
        {
            assertEquals(3, repository.findAll().size());
            runInTransaction(() -> repository.delete(31L));
            assertEquals(2, repository.findAll().size());
        }
    }
}