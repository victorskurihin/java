package ru.otus.homework.services;

import org.junit.jupiter.api.*;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import ru.otus.homework.models.Author;
import ru.otus.homework.models.Book;
import ru.otus.homework.services.dao.JdbcBookDao;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Supplier;

import static javax.swing.UIManager.put;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static ru.otus.homework.services.BooksServiceImpl.FIND_ALL_HEADER;
import static ru.otus.homework.services.BooksServiceImpl.FIND_ALL_HEADER_BOOKS_AUTHORS;
import static ru.otus.outside.utils.TestData.*;

@DisplayName("Class BooksServiceImpl")
class BooksServiceImplTest
{
    private DataSource dataSource;

    private JdbcBookDao dao;

    private BooksServiceImpl service;

    @Test
    @DisplayName("is instantiated with new BooksServiceImpl()")
    void isInstantiatedWithNew()
    {
        new BooksServiceImpl(null);
    }

    private void printFindAll()
    {
        System.out.println("transformList = " + service.findAll());
    }

    private BooksServiceImpl createService()
    {
        dataSource = injectTestDataSource();
        NamedParameterJdbcTemplate jdbc = new NamedParameterJdbcTemplate(dataSource);
        dao = new JdbcBookDao(jdbc);
        return new BooksServiceImpl(dao);
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
        @DisplayName("injected values in BooksServiceImpl()")
        void defaults()
        {
            assertThat(service).hasFieldOrPropertyWithValue("bookDao", dao);
        }
    }

    @Nested
    @DisplayName("BooksServiceImpl methods")
    class ServiceMethods
    {
        private final String[] TEST_RECORD = new String[]{
            Long.toString(TEST_ID),
            TEST_ISBN,
            TEST_TITLE,
            Integer.toString(TEST_NUM),
            TEST_COPYRIGHT,
            Long.toString(TEST_ID),
            Long.toString(0L),
        };
        private final String[] TEST_RECORD_BOOKS_AUTHORS = new String[]{
            Long.toString(TEST_ID),
            TEST_ISBN,
            TEST_TITLE,
            Integer.toString(TEST_NUM),
            TEST_COPYRIGHT,
            TEST_PUBLISHER_NAME,
            TEST_GENRE_NAME,
            TEST_FIRST_NAME,
            TEST_LAST_NAME
        };

        @BeforeEach
        void createNewService() throws SQLException
        {
            service = createService();
            inserToTables(dataSource);
        }

        @AfterEach
        void deleteFromTable() throws SQLException
        {
            clearTables(dataSource);
        }

        @Test
        void findAll()
        {
            List<String[]> testList = service.findById(TEST_ID);


            List<String[]> expected = new ArrayList<>();
            expected.add(FIND_ALL_HEADER);
            expected.add(TEST_RECORD);

            assertArrayEquals(expected.get(0), testList.get(0));
            assertArrayEquals(expected.get(1), testList.get(1));
            assertEquals(testList.get(0).length, testList.get(1).length);
        }

        @Test
        void findAllBooksAndTheirAuthors()
        {
            List<String[]> expected = new ArrayList<>();
            expected.add(FIND_ALL_HEADER_BOOKS_AUTHORS);
            expected.add(TEST_RECORD_BOOKS_AUTHORS);

            List<String[]> testList = service.findAllBooksAndTheirAuthors();
            assertArrayEquals(expected.get(0), testList.get(0));
            assertArrayEquals(expected.get(1), testList.get(1));
        }


        @Test
        void findById()
        {
            List<String[]> expected = new ArrayList<>();
            expected.add(FIND_ALL_HEADER);
            expected.add(TEST_RECORD);

            List<String[]> testList = service.findById(TEST_ID);
            System.out.println(Arrays.toString(testList.get(1)));
            assertArrayEquals(expected.get(0), testList.get(0));
            assertArrayEquals(expected.get(1), testList.get(1));
        }

        @Test
        void findByIsbn()
        {
            List<String[]> expected = new ArrayList<>();
            expected.add(FIND_ALL_HEADER);
            expected.add(TEST_RECORD);

            List<String[]> testList = service.findByIsbn(TEST_ISBN);
            assertArrayEquals(expected.get(0), testList.get(0));
            assertArrayEquals(expected.get(1), testList.get(1));
        }

        @Test
        void findByTitle()
        {
            List<String[]> expected = new ArrayList<>();
            expected.add(FIND_ALL_HEADER);
            expected.add(TEST_RECORD);

            List<String[]> testList = service.findByTitle(TEST_TITLE);
            assertArrayEquals(expected.get(0), testList.get(0));
            assertArrayEquals(expected.get(1), testList.get(1));
        }

        @Test
        void insert()
        {
            assertTrue(service.insert(
                TEST_ISBN + TEST, TEST_TITLE + TEST, TEST_NUM + 1, TEST_COPYRIGHT, TEST_ID, 0L
            ) > 0);
        }

        @Test
        void update() throws SQLException
        {
            boolean autoCommit = autoCommitOn(dataSource);
            long id = service.update(
                TEST_ID, TEST_ISBN + TEST, TEST_TITLE + TEST, TEST_NUM, TEST_COPYRIGHT, TEST_ID, 0L
            );
            assertEquals(id, TEST_ID);

            List<String[]> expected = new ArrayList<>();
            expected.add(FIND_ALL_HEADER);
            expected.add(new String[]{
                Long.toString(id),
                TEST_ISBN + TEST,
                TEST_TITLE + TEST,
                Integer.toString(TEST_NUM),
                TEST_COPYRIGHT,
                Long.toString(TEST_ID),
                Long.toString(0L),
            });

            List<String[]> testList = service.findById(id);
            autoCommitRestore(dataSource, autoCommit);
            assertArrayEquals(expected.get(0), testList.get(0));
            // TODO assertArrayEquals(expected.get(1), testList.get(1));
        }

        @Test
        void delete() throws SQLException
        {
            assertEquals(2, service.findAll().size());
            boolean autoCommit = autoCommitOn(dataSource);
            Statement statement = dataSource.getConnection().createStatement();
            statement.execute(DELETE_FROM_AUTHOR_ISBN);
            service.delete(TEST_ID);
            assertEquals(1, service.findAll().size());
            autoCommitRestore(dataSource, autoCommit);
        }
    }

    @Nested
    @DisplayName("BooksServiceImpl methods mock ")
    class ServiceMethodsMockDao
    {
        private BooksServiceImpl createServiceMockDao(Book b, Author a)
        {
            b.getAuthors().add(a);
            dao = mock(JdbcBookDao.class);
            when(dao.findAllBooksAndTheirAuthors()).thenReturn(new ArrayList<Book>(){{
                add(b);
            }});

            return new BooksServiceImpl(dao);
        }

        @Test
        void findAllBooksAndTheirAuthors_mock_AuthorNull()
        {
            final String[] TEST_RECORD_BOOKS_AUTHORS = new String[]{
                Long.toString(TEST_ID),
                TEST_ISBN,
                TEST_TITLE,
                Integer.toString(TEST_NUM),
                TEST_COPYRIGHT,
                TEST_PUBLISHER_NAME,
                TEST_GENRE_NAME,
                TEST_FIRST_NAME, TEST_LAST_NAME
            };
            List<String[]> expected = new ArrayList<>();
            expected.add(FIND_ALL_HEADER_BOOKS_AUTHORS);
            expected.add(TEST_RECORD_BOOKS_AUTHORS);

            service = createServiceMockDao(createTestBook13(), createTestAuthor13());

            List<String[]> testList = service.findAllBooksAndTheirAuthors();
            assertArrayEquals(expected.get(0), testList.get(0));
            assertArrayEquals(expected.get(1), testList.get(1));
        }

        @Test
        void findAllBooksAndTheirAuthors_mock_BookNull()
        {
            service = createServiceMockDao(createTestBook13(), null);
            // assertThrows(RuntimeException.class, () -> service.findAllBooksAndTheirAuthors());
            service.findAllBooksAndTheirAuthors();
        }
    }
}