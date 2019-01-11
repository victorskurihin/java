package ru.otus.homework.services;

import org.junit.jupiter.api.*;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import ru.otus.homework.services.dao.JdbcGenreDao;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static ru.otus.outside.utils.TestData.*;

@DisplayName("Class GenreServiceImpl")
class GenreServiceImplTest
{
    private DataSource dataSource;

    private JdbcGenreDao dao;

    private GenreServiceImpl service;

    @Test
    @DisplayName("is instantiated with new GenreServiceImpl()")
    void isInstantiatedWithNew()
    {
        new GenreServiceImpl(null);
    }

    private void printFindAll()
    {
        System.out.println("findAll = " + service.findAll());
    }

    private GenreServiceImpl createService()
    {
        dataSource = injectTestDataSource();
        NamedParameterJdbcTemplate jdbc = new NamedParameterJdbcTemplate(dataSource);
        dao = new JdbcGenreDao(jdbc);
        return new GenreServiceImpl(dao);
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
        @DisplayName("injected values in GenreServiceImpl()")
        void defaults()
        {
            assertThat(service).hasFieldOrPropertyWithValue("genreDao", dao);
        }
    }

    @Nested
    @DisplayName("GenreServiceImpl methods")
    class ServiceMethods
    {
        private final String[] TEST_RECORD = new String[]{Long.toString(0L), TEST_GENRE_NAME};
        @BeforeEach
        void createNewService() throws SQLException
        {
            service = createService();
            Statement statement = dataSource.getConnection().createStatement();
            statement.execute(INSERT_INTO_GENRE);
        }

        @AfterEach
        void deleteFromTable() throws SQLException
        {
            clearTables(dataSource);
            Statement statement = dataSource.getConnection().createStatement();
            statement.execute(DELETE_FROM_GENRE);
        }

        @Test
        void findAll()
        {
            List<String[]> expected = new ArrayList<>();
            expected.add(JdbcGenreDao.FIND_ALL_HEADER);
            expected.add(TEST_RECORD);
            assertArrayEquals(expected.get(0), service.findAll().get(0));
            assertArrayEquals(expected.get(1), service.findAll().get(1));
        }

        @Test
        void findById()
        {
            List<String[]> expected = new ArrayList<>();
            expected.add(JdbcGenreDao.FIND_ALL_HEADER);
            expected.add(TEST_RECORD);

            List<String[]> testList = service.findById(0L);
            assertArrayEquals(expected.get(0), testList.get(0));
            assertArrayEquals(expected.get(1), testList.get(1));
        }

        @Test
        void findByGenre()
        {
            List<String[]> expected = new ArrayList<>();
            expected.add(JdbcGenreDao.FIND_ALL_HEADER);
            expected.add(TEST_RECORD);

            List<String[]> testList = service.findByGenre(TEST_GENRE_NAME);
            assertArrayEquals(expected.get(0), testList.get(0));
            assertArrayEquals(expected.get(1), testList.get(1));
        }

        @Test
        void insert()
        {
            assertTrue(service.insert(TEST_GENRE_NAME + TEST) > 0L);
        }

        @Test
        void update()
        {
            long id = service.update(0L, TEST_GENRE_NAME + TEST);
            assertEquals(0, id);

            List<String[]> expected = new ArrayList<>();
            expected.add(JdbcGenreDao.FIND_ALL_HEADER);
            expected.add(new String[]{Long.toString(id), TEST_GENRE_NAME + TEST});

            List<String[]> testList = service.findById(id);
            assertArrayEquals(expected.get(0), testList.get(0));
            assertArrayEquals(expected.get(1), testList.get(1));
        }

        @Test
        void delete() throws SQLException
        {
            assertEquals(2, service.findAll().size());
            boolean autoCommit = autoCommitOn(dataSource);
            service.delete(0L);
            assertEquals(1, service.findAll().size());
            autoCommitRestore(dataSource, autoCommit);
        }
    }
}