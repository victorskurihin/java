package ru.otus.homework.services;

import org.junit.jupiter.api.*;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import ru.otus.homework.services.dao.JdbcPublisherDao;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static ru.otus.homework.services.PublishersServiceImpl.FIND_ALL_HEADER;
import static ru.otus.outside.utils.TestData.*;

@DisplayName("Class PublisherServiceImpl")
class PublishersServiceImplTest
{
    private DataSource dataSource;

    private JdbcPublisherDao dao;

    private PublishersServiceImpl service;

    @Test
    @DisplayName("is instantiated with new PublisherServiceImpl()")
    void isInstantiatedWithNew()
    {
        new PublishersServiceImpl(null);
    }

    private void printFindAll()
    {
        System.out.println("transformList = " + service.findAll());
    }

    private PublishersServiceImpl createService()
    {
        dataSource = injectTestDataSource();
        NamedParameterJdbcTemplate jdbc = new NamedParameterJdbcTemplate(dataSource);
        dao = new JdbcPublisherDao(jdbc);
        return new PublishersServiceImpl(dao);
    }

    @Nested
    @DisplayName("when new default")
    class WhenNew
    {
        @BeforeEach
        void createNew()
        {
            service = createService();
        }

        @Test
        @DisplayName("injected values in PublisherServiceImpl()")
        void defaults()
        {
            assertThat(service).hasFieldOrPropertyWithValue("publisherDao", dao);
        }
    }

    @Nested
    @DisplayName("PublisherServiceImpl methods")
    class ServiceMethods
    {
        private final String[] TEST_RECORD = new String[]{Long.toString(TEST_ID), TEST_PUBLISHER_NAME};
        @BeforeEach
        void createNewService() throws SQLException
        {
            service = createService();
            Statement statement = dataSource.getConnection().createStatement();
            statement.execute(INSERT_INTO_PUBLISHER);
        }

        @AfterEach
        void deleteFromTable() throws SQLException
        {
            clearTables(dataSource);
            Statement statement = dataSource.getConnection().createStatement();
            statement.execute(DELETE_FROM_PUBLISHER);
        }

        @Test
        void findAll()
        {
            List<String[]> testList = service.findById(TEST_ID);
            // System.out.println(Arrays.toString(testList.get(1)));

            List<String[]> expected = new ArrayList<>();
            expected.add(FIND_ALL_HEADER);
            expected.add(TEST_RECORD);

            assertArrayEquals(expected.get(0), testList.get(0));
            assertArrayEquals(expected.get(1), testList.get(1));
            assertEquals(testList.get(0).length, testList.get(1).length);
        }

        @Test
        void findById()
        {
            List<String[]> expected = new ArrayList<>();
            expected.add(FIND_ALL_HEADER);
            expected.add(TEST_RECORD);

            List<String[]> testList = service.findById(TEST_ID);
            assertArrayEquals(expected.get(0), testList.get(0));
            assertArrayEquals(expected.get(1), testList.get(1));
            assertEquals(testList.get(0).length, testList.get(1).length);
        }

        @Test
        void findByPublisher()
        {
            List<String[]> expected = new ArrayList<>();
            expected.add(FIND_ALL_HEADER);
            expected.add(TEST_RECORD);

            List<String[]> testList = service.findByPublisherName(TEST_PUBLISHER_NAME);
            assertArrayEquals(expected.get(0), testList.get(0));
            assertArrayEquals(expected.get(1), testList.get(1));
            assertEquals(testList.get(0).length, testList.get(1).length);
        }

        @Test
        void insert()
        {
            assertTrue(service.insert(TEST_PUBLISHER_NAME + TEST) > 0L);
        }

        @Test
        void update()
        {
            long id = service.update(TEST_ID, TEST_PUBLISHER_NAME + TEST);
            assertEquals(TEST_ID, id);

            List<String[]> expected = new ArrayList<>();
            expected.add(FIND_ALL_HEADER);
            expected.add(new String[]{Long.toString(id), TEST_PUBLISHER_NAME + TEST});

            List<String[]> testList = service.findById(id);
            assertArrayEquals(expected.get(0), testList.get(0));
            assertArrayEquals(expected.get(1), testList.get(1));
        }

        @SuppressWarnings("Duplicates")
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