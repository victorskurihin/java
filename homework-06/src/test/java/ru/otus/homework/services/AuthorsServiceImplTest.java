package ru.otus.homework.services;

import org.junit.jupiter.api.*;
import ru.otus.homework.models.Author;
import ru.otus.homework.repository.AuthorRepositoryJpa;

import java.sql.SQLException;
import java.util.Arrays;
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

    private AuthorsServiceImpl createService()
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
            service = createService();
        }

        @Test
        @DisplayName("injected values in AuthorsServiceImpl()")
        void defaults()
        {
            assertThat(service).hasFieldOrPropertyWithValue("authorRepository", repository);
        }
    }

    @Nested
    @DisplayName("AuthorsServiceImpl methods")
    class ServiceMethods
    {
        private final String[] TEST_AUTHOR6_RECORD = new String[]{
            Long.toString(6L), TEST_AUTHOR6_FIRST_NAME, TEST_AUTHOR6_LAST_NAME
        };

        @BeforeEach
        void createNewService() throws SQLException
        {
            service = createService();
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

            printListStrings(System.out, testList);
            assertEquals(expected.get(0).length, testList.get(0).length);
            assertListStringsEquals(expected, testList);
        }
    }
/*    private DataSource dataSource;

    private AuthorRepositoryJpa repository;

    private AuthorsServiceImpl service;

    @Test
    @DisplayName("is instantiated with new AuthorsServiceImpl()")
    void isInstantiatedWithNew()
    {
        new AuthorsServiceImpl(null);
    }

    private void printFindAll()
    {
        System.out.println("findAll = " + service.findAll());
    }

    @Nested
    @DisplayName("AuthorsServiceImpl methods")
    class ServiceMethods
    {
        private final String[] TEST_RECORD = new String[]{Long.toString(TEST_ID), TEST_FIRST_NAME, TEST_LAST_NAME};
        @BeforeEach
        void createNewService() throws SQLException
        {
            service = createService();
            Statement statement = dataSource.getConnection().createStatement();
            statement.execute(INSERT_INTO_AUTHOR);
        }

        @AfterEach
        void deleteFromTable() throws SQLException
        {
            clearTables(dataSource);
            Statement statement = dataSource.getConnection().createStatement();
            statement.execute(DELETE_FROM_AUTHOR);
        }

        @Test
        void findByFirstName()
        {
            List<String[]> expected = new ArrayList<>();
            expected.add(AuthorRepositoryJpa.FIND_ALL_HEADER);
            expected.add(TEST_RECORD);

            List<String[]> testList = service.findByFirstName(TEST_FIRST_NAME);
            assertArrayEquals(expected.get(0), testList.get(0));
            assertArrayEquals(expected.get(1), testList.get(1));
            assertEquals(testList.get(0).length, testList.get(1).length);
        }

        @Test
        void findByLastName()
        {
            List<String[]> expected = new ArrayList<>();
            expected.add(AuthorRepositoryJpa.FIND_ALL_HEADER);
            expected.add(TEST_RECORD);

            List<String[]> testList = service.findByLastName(TEST_LAST_NAME);
            assertArrayEquals(expected.get(0), testList.get(0));
            assertArrayEquals(expected.get(1), testList.get(1));
            assertEquals(testList.get(0).length, testList.get(1).length);
        }

        @Test
        void save()
        {
            assertTrue(service.save(TEST_FIRST_NAME + TEST, TEST_LAST_NAME + TEST) > 0);
        }

        @Test
        void update()
        {
            long id = service.update(TEST_ID, TEST_FIRST_NAME + TEST, TEST_LAST_NAME + TEST);
            assertTrue(id > 0);

            List<String[]> expected = new ArrayList<>();
            expected.add(AuthorRepositoryJpa.FIND_ALL_HEADER);
            expected.add(new String[]{Long.toString(id), TEST_FIRST_NAME + TEST, TEST_LAST_NAME + TEST});

            List<String[]> testList = service.findById(id);
            assertArrayEquals(expected.get(0), testList.get(0));
            assertArrayEquals(expected.get(1), testList.get(1));
        }

        @Test
        void delete() throws SQLException
        {
            assertEquals(2, service.findAll().size());
            boolean autoCommit = autoCommitOn(dataSource);
            service.delete(TEST_ID);
            assertEquals(1, service.findAll().size());
            autoCommitRestore(dataSource, autoCommit);
        }
    }*/
}