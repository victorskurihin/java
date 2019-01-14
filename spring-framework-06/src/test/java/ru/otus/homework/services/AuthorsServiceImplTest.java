package ru.otus.homework.services;

import org.junit.jupiter.api.*;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import ru.otus.homework.services.dao.JdbcAuthorDao;

import javax.sql.DataSource;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static ru.otus.outside.utils.TestData.*;

@DisplayName("Class AuthorsServiceImpl")
class AuthorsServiceImplTest
{
    private DataSource dataSource;

    private JdbcAuthorDao dao;

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

    private AuthorsServiceImpl createService()
    {
        dataSource = injectTestDataSource();
        NamedParameterJdbcTemplate jdbc = new NamedParameterJdbcTemplate(dataSource);
        dao = new JdbcAuthorDao(jdbc);
        return new AuthorsServiceImpl(dao);
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
            assertThat(service).hasFieldOrPropertyWithValue("authorDao", dao);
        }
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
        void findAll()
        {
            List<String[]> expected = new ArrayList<>();
            expected.add(JdbcAuthorDao.FIND_ALL_HEADER);
            expected.add(TEST_RECORD);
            assertArrayEquals(expected.get(0), service.findAll().get(0));
            assertArrayEquals(expected.get(1), service.findAll().get(1));
        }

        @Test
        void findById()
        {
            List<String[]> expected = new ArrayList<>();
            expected.add(JdbcAuthorDao.FIND_ALL_HEADER);
            expected.add(TEST_RECORD);

            List<String[]> testList = service.findById(TEST_ID);
            assertArrayEquals(expected.get(0), testList.get(0));
            assertArrayEquals(expected.get(1), testList.get(1));
        }

        @Test
        void findByFirstName()
        {
            List<String[]> expected = new ArrayList<>();
            expected.add(JdbcAuthorDao.FIND_ALL_HEADER);
            expected.add(TEST_RECORD);

            List<String[]> testList = service.findByFirstName(TEST_FIRST_NAME);
            assertArrayEquals(expected.get(0), testList.get(0));
            assertArrayEquals(expected.get(1), testList.get(1));
        }

        @Test
        void findByLastName()
        {
            List<String[]> expected = new ArrayList<>();
            expected.add(JdbcAuthorDao.FIND_ALL_HEADER);
            expected.add(TEST_RECORD);

            List<String[]> testList = service.findByLastName(TEST_LAST_NAME);
            assertArrayEquals(expected.get(0), testList.get(0));
            assertArrayEquals(expected.get(1), testList.get(1));
        }

        @Test
        void insert()
        {
            assertTrue(service.insert(TEST_FIRST_NAME + TEST, TEST_LAST_NAME + TEST) > 0);
        }

        @Test
        void update()
        {
            long id = service.update(TEST_ID, TEST_FIRST_NAME + TEST, TEST_LAST_NAME + TEST);
            assertTrue(id > 0);

            List<String[]> expected = new ArrayList<>();
            expected.add(JdbcAuthorDao.FIND_ALL_HEADER);
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
    }
}