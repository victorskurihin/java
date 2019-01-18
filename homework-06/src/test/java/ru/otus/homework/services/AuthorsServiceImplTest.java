package ru.otus.homework.services;

import org.junit.jupiter.api.*;
import ru.otus.homework.models.Author;
import ru.otus.homework.repository.AuthorRepositoryJpa;
import ru.otus.outside.db.JpaDedicatedEntityManagerTest;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static ru.otus.outside.utils.LIstStringsHelper.assertListStringsEquals;
import static ru.otus.outside.utils.LIstStringsHelper.printListStrings;
import static ru.otus.outside.utils.TestData.*;

@DisplayName("Class AuthorsServiceImpl")
class AuthorsServiceImplTest
{
    private AuthorRepositoryJpa repository;

    private AuthorsServiceImpl service;

    @Test
    @DisplayName("is instantiated with new AuthorsServiceImpl()")
    void isInstantiatedWithNew()
    {
        new AuthorsServiceImpl(null);
    }

    private AuthorsServiceImpl createMockService()
    {
        repository = mock(AuthorRepositoryJpa.class);

        return new AuthorsServiceImpl(repository);
    }

    private List<String[]> createEmptyStringsAuthors()
    {
        List<String[]> result = new LinkedList<>();
        result.add(AuthorRepositoryJpa.FIND_ALL_HEADER);

        return result;
    }

    @Nested
    @DisplayName("when new default")
    class WhenNew
    {
        @BeforeEach
        void createNewQuestions()
        {
            service = createMockService();
        }

        @Test
        @DisplayName("injected values in AuthorsServiceImpl()")
        void defaults()
        {
            assertThat(service).hasFieldOrPropertyWithValue("authorRepository", repository);
        }
    }

    @Nested
    @DisplayName("when mock AuthorRepositoryJpa")
    class ServiceMethods
    {
        private final String[] TEST_AUTHOR6_RECORD = new String[]{
            Long.toString(6L), TEST_AUTHOR6_FIRST_NAME, TEST_AUTHOR6_LAST_NAME
        };

        @BeforeEach
        void createNewService() throws SQLException
        {
            service = createMockService();
        }

        @Test
        void findAll_empty()
        {
            List<Author> authors = new LinkedList<>();

            when(repository.findAll()).thenReturn(authors);

            List<String[]> testList = service.findAll();
            List<String[]> expected = createEmptyStringsAuthors();

            assertEquals(expected.get(0).length, testList.get(0).length);
            assertArrayEquals(expected.get(0), testList.get(0));
        }

        @Test
        void findAll()
        {
            List<Author> authors = new LinkedList<>();
            authors.add(createAuthor6());

            when(repository.findAll()).thenReturn(authors);

            List<String[]> testList = service.findAll();
            List<String[]> expected = createEmptyStringsAuthors();
            expected.add(TEST_AUTHOR6_RECORD);

            // printListStrings(System.out, testList);
            assertEquals(expected.get(0).length, testList.get(0).length);
            assertListStringsEquals(expected, testList);
        }

        @Test
        void findById()
        {
            when(repository.findById(6L)).thenReturn(createAuthor6());

            List<String[]> testList = service.findById(6L);
            List<String[]> expected = createEmptyStringsAuthors();
            expected.add(TEST_AUTHOR6_RECORD);

            // printListStrings(System.out, testList);
            assertEquals(expected.get(0).length, testList.get(0).length);
            assertListStringsEquals(expected, testList);
        }

        @Test
        void findByFirstName()
        {
            List<Author> authors = new LinkedList<>();
            authors.add(createAuthor6());

            when(repository.findByFirstName(TEST_AUTHOR6_FIRST_NAME)).thenReturn(authors);

            List<String[]> testList = service.findByFirstName(TEST_AUTHOR6_FIRST_NAME);
            List<String[]> expected = createEmptyStringsAuthors();
            expected.add(TEST_AUTHOR6_RECORD);

            // printListStrings(System.out, testList);
            assertEquals(expected.get(0).length, testList.get(0).length);
            assertListStringsEquals(expected, testList);
        }

        @Test
        void findByLastName()
        {
            List<Author> authors = new LinkedList<>();
            authors.add(createAuthor6());

            when(repository.findByLastName(TEST_AUTHOR6_LAST_NAME)).thenReturn(authors);

            List<String[]> testList = service.findByLastName(TEST_AUTHOR6_LAST_NAME);
            List<String[]> expected = createEmptyStringsAuthors();
            expected.add(TEST_AUTHOR6_RECORD);

            // printListStrings(System.out, testList);
            assertEquals(expected.get(0).length, testList.get(0).length);
            assertListStringsEquals(expected, testList);
        }
    }

    @Nested
    @DisplayName("JPA H2 insert/update tests for AuthorRepositoryJpa")
    class JpaH2CreateUpdateTests extends JpaDedicatedEntityManagerTest
    {
        @BeforeEach
        void createNew()
        {
            repository = new AuthorRepositoryJpa(entityManager);
            service = new AuthorsServiceImpl(repository);
        }

        @DisplayName("insert")
        @Test
        void insert()
        {
            runInTransaction(() -> {
                service.insert("test", "test");
                List<Author> result = repository.findByFirstName("test");
                assertEquals(1, result.size());
            });
        }

        @DisplayName("update")
        @Test
        void update()
        {
            runInTransaction(() -> {
                Author author6 = createAuthor6();
                author6.setFirstName("test");
                author6.setLastName("test");
                service.update(author6.getId(), author6.getFirstName(), author6.getLastName());
                Author test = repository.findById(author6.getId());
                assertEquals(author6, test);
            });
        }

        @DisplayName("delete")
        @Test
        void delete()
        {
            clearAuthorIsbn();
            runInTransaction(() -> {
                assertEquals(3, repository.findAll().size());
                service.delete(6L);
                assertEquals(2, repository.findAll().size());
            });
        }
    }
}