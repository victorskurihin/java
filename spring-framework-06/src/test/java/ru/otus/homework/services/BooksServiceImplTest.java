package ru.otus.homework.services;

import org.junit.jupiter.api.*;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import ru.otus.homework.services.dao.JdbcBookDao;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
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
        System.out.println("findAll = " + service.findAll());
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
        private final String[] TEST_RECORD = new String[]{Long.toString(TEST_ID), TEST_FIRST_NAME, TEST_LAST_NAME};

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
            List<String[]> expected = new ArrayList<>();
            expected.add(JdbcBookDao.FIND_ALL_HEADER);
            expected.add(TEST_RECORD);
            assertArrayEquals(expected.get(0), service.findAll().get(0));
            assertArrayEquals(expected.get(1), service.findAll().get(1));
        }

        @Test
        void findById()
        {
            List<String[]> expected = new ArrayList<>();
            expected.add(JdbcBookDao.FIND_ALL_HEADER);
            expected.add(TEST_RECORD);

            List<String[]> testList = service.findById(TEST_ID);
            assertArrayEquals(expected.get(0), testList.get(0));
            assertArrayEquals(expected.get(1), testList.get(1));
        }

        @Test
        void findByIsbn()
        {
            List<String[]> expected = new ArrayList<>();
            expected.add(JdbcBookDao.FIND_ALL_HEADER);
            expected.add(TEST_RECORD);

            List<String[]> testList = service.findByIsbn(TEST_ISBN);
            assertArrayEquals(expected.get(0), testList.get(0));
            assertArrayEquals(expected.get(1), testList.get(1));
        }

        @Test
        void findByTitle()
        {
            List<String[]> expected = new ArrayList<>();
            expected.add(JdbcBookDao.FIND_ALL_HEADER);
            expected.add(TEST_RECORD);

            List<String[]> testList = service.findByTitle(TEST_TITLE);
            assertArrayEquals(expected.get(0), testList.get(0));
            assertArrayEquals(expected.get(1), testList.get(1));
        }

        @Test
        void findAllBooksAndTheirAuthors()
        {
        }

        @Test
        void insert()
        {
            // assertTrue(service.insert(TEST_FIRST_NAME + TEST, TEST_LAST_NAME + TEST) > 0);
        }

        @Test
        void update()
        {
            /*
            long id = service.update(TEST_ID, TEST_FIRST_NAME + TEST, TEST_LAST_NAME + TEST);
            assertTrue(id > 0);

            List<String[]> expected = new ArrayList<>();
            expected.add(JdbcBookDao.FIND_ALL_HEADER);
            expected.add(new String[]{Long.toString(id), TEST_FIRST_NAME + TEST, TEST_LAST_NAME + TEST});

            List<String[]> testList = service.findById(id);
            assertArrayEquals(expected.get(0), testList.get(0));
            assertArrayEquals(expected.get(1), testList.get(1));
            */
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
    }
}