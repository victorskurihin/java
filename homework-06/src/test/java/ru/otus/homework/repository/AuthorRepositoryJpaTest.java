package ru.otus.homework.repository;

import org.junit.jupiter.api.*;
import ru.otus.homework.models.Author;
import ru.otus.outside.db.JpaDedicatedEntityManagerTest;
import ru.otus.outside.db.JpaSharedEntityManagerTest;

import javax.persistence.EntityManager;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static ru.otus.outside.utils.TestData.*;

@DisplayName("Class AuthorRepositoryJpa")
class AuthorRepositoryJpaTest
{
    private AuthorRepositoryJpa repository;

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
        @DisplayName("default values in AuthorRepositoryJpa()")
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
            repository = new AuthorRepositoryJpa(entityManager);
        }

        @Test
        @DisplayName("Constructor injected values in AuthorRepositoryJpa()")
        void defaults()
        {
            assertThat(repository).hasFieldOrPropertyWithValue("em", entityManager);
        }

        @DisplayName("find by id from table author success")
        @Test
        void findById_success()
        {
            Author expected = new Author();
            expected.setFirstName("testFirstName");
            expected.setLastName("testLastName");

            when(entityManager.find(Author.class,1L)).thenReturn(expected);

            Author author = repository.findById(1L);
            assertEquals(expected, author);
        }

        @DisplayName("find by id from table author return null")
        @Test
        void findById_null()
        {
            when(entityManager.find(Author.class,1L)).thenReturn(null);

            Author author = repository.findById(1L);
            assertNull(author);
        }
    }

    @Nested
    @DisplayName("JPA H2 read-only tests for AuthorRepositoryJpa")
    class JpaH2ReadOnlyTests extends JpaSharedEntityManagerTest
    {
        @BeforeEach
        void createNew()
        {
            repository = new AuthorRepositoryJpa(entityManager);
        }

        @Test
        @DisplayName("Constructor injected values in AuthorRepositoryJpa()")
        void defaults()
        {
            assertThat(repository).hasFieldOrPropertyWithValue("em", entityManager);
        }

        @Test
        void findByFirstName_success()
        {
            Author expected = createAuthor6();
            List<Author> authorList = repository.findByFirstName(expected.getFirstName());
            assertTrue(authorList.contains(createAuthor6()));
        }

        @Test
        void findByFirstName_empty()
        {
            List<Author> authorList = repository.findByFirstName("");
            assertTrue(authorList.isEmpty());
        }

        @Test
        void findByLastName_success()
        {
            Author expected = createAuthor6();
            List<Author> authorList = repository.findByLastName(expected.getLastName());
            assertTrue(authorList.contains(createAuthor6()));
        }

        @Test
        void findByLastName_empty()
        {
            List<Author> authorList = repository.findByLastName("");
            assertTrue(authorList.isEmpty());
        }

        @DisplayName("find all records from table author, success")
        @Test
        void findAll_success()
        {
            List<Author> authorList = repository.findAll();
            assertEquals(3, authorList.size());
            assertTrue(authorList.contains(createAuthor6()));
            assertTrue(authorList.contains(createAuthor7()));
            assertTrue(authorList.contains(createAuthor8()));
        }

        @DisplayName("find all records from empty table author, empty list")
        @Test
        void findAll_empty()
        {
            clearAuthorIsbn();
            clearAuthor();
            List<Author> authorList = repository.findAll();
            assertTrue(authorList.isEmpty());
        }

        @DisplayName("find by id from table author, success")
        @Test
        void findById_success()
        {
            Author expected = createAuthor6();
            Author author = repository.findById(expected.getId());
            assertEquals(expected, author);
        }

        @DisplayName("when finding by id from table author, id doesn't exist, return null")
        @Test
        void findById_null()
        {
            Author author = repository.findById(Integer.MAX_VALUE);
            assertNull(author);
        }
    }

    @Nested
    @DisplayName("JPA H2 create/update tests for AuthorRepositoryJpa")
    class JpaH2CreateUpdateTests extends JpaDedicatedEntityManagerTest
    {
        @BeforeEach
        void createNew()
        {
            repository = new AuthorRepositoryJpa(entityManager);
        }

        @DisplayName("persists new when save")
        @Test
        void save_persists()
        {
            Author author = new Author();
            author.setFirstName("test");
            author.setLastName("test");
            runInTransaction(() -> repository.save(author));
            assertEquals(author, repository.findById(author.getId()));
        }

        @DisplayName("merge detached object when save")
        @Test
        void save_megre()
        {
            Author author = createAuthor6();
            author.setFirstName("test");
            author.setLastName("test");
            runInTransaction(() -> repository.save(author));
            assertEquals(author, repository.findById(author.getId()));
        }

        @Test
        void delete()
        {
            assertEquals(3, repository.findAll().size());
            clearAuthorIsbn();
            runInTransaction(() -> repository.delete(6L));
            assertEquals(2, repository.findAll().size());
        }
    }
}