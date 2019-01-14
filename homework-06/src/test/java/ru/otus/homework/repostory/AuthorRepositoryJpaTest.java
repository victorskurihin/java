package ru.otus.homework.repostory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import ru.otus.homework.models.Author;
import ru.otus.outside.db.JPAHibernateTest;

import javax.persistence.EntityManager;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static ru.otus.outside.utils.TestData.createAuthor6;

@DisplayName("Class AuthorRepositoryJpa")
class AuthorRepositoryJpaTest
{
    AuthorRepositoryJpa repository;

    @Nested
    @DisplayName("when new default")
    class WhenNew
    {
        @BeforeEach
        void createNew()
        {
            repository = new AuthorRepositoryJpa();
        }

        @Test
        @DisplayName("injected values in JdbcAuthorDao()")
        void defaults()
        {
            assertThat(repository).hasFieldOrPropertyWithValue("entityManager", null);
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
            repository = new AuthorRepositoryJpa(entityManager);
        }

        @Test
        @DisplayName("injected values in JdbcAuthorDao()")
        void defaults()
        {
            assertThat(repository).hasFieldOrPropertyWithValue("entityManager", entityManager);
        }

        @Test
        void happyPathScenario(){
            Author expected = new Author();
            expected.setFirstName("testFirstName");
            expected.setLastName("testLastName");

            when(entityManager.find(Author.class,1L)).thenReturn(expected);

            Author author = repository.findById(1L);
            assertEquals(expected, author);
        }

        @Test
        void authorNotPresentInDb(){
            when(entityManager.find(Author.class,1L)).thenReturn(null);

            Author author = repository.findById(1L);
            assertNull( author);
        }
    }

    @Nested
    @DisplayName("JPA H2 tests for AuthorRepositoryJpa")
    class JpaH2Tests extends JPAHibernateTest
    {
        @BeforeEach
        void createNew()
        {
            repository = new AuthorRepositoryJpa(entityManager);
        }

        @Test
        void findByFirstName()
        {
        }

        @Test
        void findByLastName()
        {
        }

        @Test
        void findAll()
        {
        }

        @DisplayName("find by id from table author")
        @Test
        void findById()
        {
            Author expected = createAuthor6();
            Author author = repository.findById(expected.getId());
            assertEquals(expected, author);
        }
        @Test
        void insert()
        {
        }

        @Test
        void update()
        {
        }

        @Test
        void delete()
        {
        }
    }
}