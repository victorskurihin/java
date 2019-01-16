package ru.otus.homework.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import ru.otus.homework.models.Genre;
import ru.otus.outside.db.JpaDedicatedEntityManagerTest;
import ru.otus.outside.db.JpaSharedEntityManagerTest;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static ru.otus.outside.utils.TestData.*;

@DisplayName("Class GenreRepositoryJpa")
class GenreRepositoryJpaTest
{
    private GenreRepositoryJpa repository;

    @Nested
    @DisplayName("when new default")
    class WhenNew
    {
        @BeforeEach
        void createNew()
        {
            repository = new GenreRepositoryJpa();
        }

        @Test
        @DisplayName("default values in GenreRepositoryJpa()")
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
            repository = new GenreRepositoryJpa(entityManager);
        }

        @Test
        @DisplayName("Constructor injected values in GenreRepositoryJpa()")
        void defaults()
        {
            assertThat(repository).hasFieldOrPropertyWithValue("em", entityManager);
        }

        @DisplayName("find by id from table genre success")
        @Test
        void findById_success()
        {
            Genre expected = new Genre();
            expected.setGenre("testGenre");

            when(entityManager.find(Genre.class,1L)).thenReturn(expected);

            Genre genre = repository.findById(1L);
            assertEquals(expected, genre);
        }

        @DisplayName("find by id from table genre return null")
        @Test
        void findById_null()
        {
            when(entityManager.find(Genre.class,1L)).thenReturn(null);

            Genre genre = repository.findById(1L);
            assertNull(genre);
        }
    }

    @Nested
    @DisplayName("JPA H2 read-only tests for GenreRepositoryJpa")
    class JpaH2ReadOnlyTests extends JpaSharedEntityManagerTest
    {
        @BeforeEach
        void createNew()
        {
            repository = new GenreRepositoryJpa(entityManager);
        }

        @Test
        @DisplayName("Constructor injected values in GenreRepositoryJpa()")
        void defaults()
        {
            assertThat(repository).hasFieldOrPropertyWithValue("em", entityManager);
        }

        @Test
        void findByFirstName_success()
        {
            Genre expected = createGenre2();
            List<Genre> genreList = repository.findByGenre(expected.getGenre());
            assertTrue(genreList.contains(createGenre2()));
        }

        @Test
        void findByFirstName_empty()
        {
            List<Genre> genreList = repository.findByGenre("");
            assertTrue(genreList.isEmpty());
        }

        @Test
        void findByLastName_success()
        {
            Genre expected = createGenre2();
            List<Genre> genreList = repository.findByGenre(expected.getGenre());
            assertTrue(genreList.contains(createGenre2()));
        }

        @Test
        void findByLastName_empty()
        {
            List<Genre> genreList = repository.findByGenre("");
            assertTrue(genreList.isEmpty());
        }

        @DisplayName("find all records from table genre, success")
        @Test
        void findAll_success()
        {
            List<Genre> genreList = repository.findAll();
            assertEquals(1, genreList.size());
            assertTrue(genreList.contains(createGenre2()));
        }

        @DisplayName("find all records from empty table genre, empty list")
        @Test
        void findAll_empty()
        {
            clearAuthorIsbn();
            clearComment();
            clearBook();
            clearGenre();
            List<Genre> genreList = repository.findAll();
            assertTrue(genreList.isEmpty());
        }

        @DisplayName("find by id from table genre, success")
        @Test
        void findById_success()
        {
            Genre expected = createGenre2();
            Genre genre = repository.findById(expected.getId());
            assertEquals(expected, genre);
        }

        @DisplayName("when finding by id from table genre, id doesn't exist, return null")
        @Test
        void findById_null()
        {
            Genre genre = repository.findById(Integer.MAX_VALUE);
            assertNull(genre);
        }
    }

    @Nested
    @DisplayName("JPA H2 create/update tests for GenreRepositoryJpa")
    class JpaH2CreateUpdateTests extends JpaDedicatedEntityManagerTest
    {
        @BeforeEach
        void createNew()
        {
            repository = new GenreRepositoryJpa(entityManager);
        }

        @DisplayName("persists new when save")
        @Test
        void save_persists()
        {
            Genre genre = new Genre();
            genre.setGenre("test");
            runInTransaction(() -> repository.save(genre));
            assertEquals(genre, repository.findById(genre.getId()));
        }

        @DisplayName("merge detached object when save")
        @Test
        void save_megre()
        {
            Genre genre = createGenre2();
            genre.setGenre("test");
            runInTransaction(() -> repository.save(genre));
            assertEquals(genre, repository.findById(genre.getId()));
        }

        @Test
        void delete()
        {
            assertEquals(1, repository.findAll().size());
            clearAuthorIsbn();
            clearBook();
            runInTransaction(() -> repository.delete(2L));
            assertEquals(0, repository.findAll().size());
        }
    }
}