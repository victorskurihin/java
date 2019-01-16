package ru.otus.homework.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import ru.otus.homework.models.Book;
import ru.otus.outside.db.JpaDedicatedEntityManagerTest;
import ru.otus.outside.db.JpaSharedEntityManagerTest;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static ru.otus.outside.utils.TestData.*;

@DisplayName("Class BookRepositoryJpa")
class BookRepositoryJpaTest
{
    private BookRepositoryJpa repository;

    @Nested
    @DisplayName("when new default")
    class WhenNew
    {
        @BeforeEach
        void createNew()
        {
            repository = new BookRepositoryJpa();
        }

        @Test
        @DisplayName("default values in BookRepositoryJpa()")
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
            repository = new BookRepositoryJpa(entityManager);
        }

        @Test
        @DisplayName("Constructor injected values in BookRepositoryJpa()")
        void defaults()
        {
            assertThat(repository).hasFieldOrPropertyWithValue("em", entityManager);
        }

        @DisplayName("find by id from table book success")
        @Test
        void findById_success()
        {
            Book expected = new Book();
            expected.setIsbn("testIsbn");

            when(entityManager.find(Book.class,1L)).thenReturn(expected);

            Book book = repository.findById(1L);
            assertEquals(expected, book);
        }

        @DisplayName("find by id from table book return null")
        @Test
        void findById_null()
        {
            when(entityManager.find(Book.class,1L)).thenReturn(null);

            Book book = repository.findById(1L);
            assertNull(book);
        }
    }

    @Nested
    @DisplayName("JPA H2 read-only tests for BookRepositoryJpa")
    class JpaH2ReadOnlyTests extends JpaSharedEntityManagerTest
    {
        @BeforeEach
        void createNew()
        {
            repository = new BookRepositoryJpa(entityManager);
        }

        @Test
        @DisplayName("Constructor injected values in BookRepositoryJpa()")
        void defaults()
        {
            assertThat(repository).hasFieldOrPropertyWithValue("em", entityManager);
        }

        @Test
        void findByTitle_success()
        {
            Book expected = createBook3();
            List<Book> bookList = repository.findByTitle(expected.getTitle());
            assertTrue(bookList.contains(createBook3()));
        }

        @Test
        void findByTitle_empty()
        {
            List<Book> bookList = repository.findByTitle("");
            assertTrue(bookList.isEmpty());
        }

        @Test
        void findByIsbn_exception()
        {
            // Book book = repository.findByIsbn("");
            assertThrows(NoResultException.class, () -> repository.findByIsbn(""));
        }

        @Test
        void findByIsbn_success()
        {
            Book expected = createBook3();
            Book book = repository.findByIsbn(expected.getIsbn());
            book.getComments().clear();
            expected.getComments().clear();
            assertEquals(expected, book);
        }

        @DisplayName("find all records from table book, success")
        @Test
        void findAll_success()
        {
            List<Book> bookList = repository.findAll();
            assertEquals(3, bookList.size());
            System.out.println("bookList = " + bookList);
//            Book book3 = createBook3();
//            assertTrue(bookList.contains(createBook3()));
//            assertTrue(bookList.contains(createBook4()));
//            assertTrue(bookList.contains(createBook5()));
        }

        @DisplayName("find all records from empty table book, empty list")
        @Test
        void findAll_empty()
        {
            clearAuthorIsbn();
            clearComment();
            clearBook();
            List<Book> bookList = repository.findAll();
            assertTrue(bookList.isEmpty());
        }

        @DisplayName("find by id from table book, success")
        @Test
        void findById_success()
        {
            Book expected = createBook3();
            expected.getComments().clear();
            Book book = repository.findById(expected.getId());
            book.getComments().clear();
            assertEquals(expected, book);
        }

        @DisplayName("when finding by id from table book, id doesn't exist, return null")
        @Test
        void findById_null()
        {
            Book book = repository.findById(Integer.MAX_VALUE);
            assertNull(book);
        }
    }

    @Nested
    @DisplayName("JPA H2 create/update tests for BookRepositoryJpa")
    class JpaH2CreateUpdateTests extends JpaDedicatedEntityManagerTest
    {
        @BeforeEach
        void createNew()
        {
            repository = new BookRepositoryJpa(entityManager);
        }

        @DisplayName("persists new when save")
        @Test
        void save_persists()
        {
            Book book = new Book();
            book.setIsbn("test");
            book.setTitle("test");
            book.setCopyright("test");
            book.setPublisher(createPublisher1());
            book.setGenre(createGenre2());
            runInTransaction(() -> repository.save(book));
            assertEquals(book, repository.findById(book.getId()));
        }

        @DisplayName("merge detached object when save")
        @Test
        void save_megre()
        {
            Book book = createBook3();
            book.setTitle("test");
            runInTransaction(() -> repository.save(book));
            assertEquals(book, repository.findById(book.getId()));
        }

        @Test
        void delete()
        {
            assertEquals(3, repository.findAll().size());
            clearAuthorIsbn();
            runInTransaction(() -> repository.delete(3L));
            assertEquals(2, repository.findAll().size());
        }
    }
}