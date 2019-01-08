package ru.otus.homework.services.dao;

import org.junit.jupiter.api.*;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import ru.otus.homework.models.Book;
import ru.otus.homework.models.Publisher;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static ru.otus.outside.utils.TestData.*;

@DisplayName("Class JdbcPublisherDao")
class JdbcPublisherDaoTest
{
    private DataSource dataSource;

    private JdbcPublisherDao dao;

    @Test
    @DisplayName("is instantiated with new JdbcPublisherDao()")
    void isInstantiatedWithNew()
    {
        dataSource = injectTestDataSource();
        new JdbcPublisherDao(dataSource);
    }

    private void printFindAll()
    {
        System.out.println("findAll = " + dao.findAll());
    }

    @Nested
    @DisplayName("when new default")
    class WhenNew
    {

        @BeforeEach
        void createNewQuestions()
        {
            dataSource = injectTestDataSource();
            dao = new JdbcPublisherDao(dataSource);
        }

        @Test
        @DisplayName("injected values in JdbcPublisherDao()")
        void defaults()
        {
            assertThat(dao).hasFieldOrPropertyWithValue("dataSource", dataSource);
            assertThat(dao).hasFieldOrProperty("namedParameterJdbcTemplate").isNotNull();
            assertThat(dao).hasFieldOrProperty("selectPublisherByName").isNotNull();
            assertThat(dao).hasFieldOrProperty("insertPublisher").isNotNull();
            assertThat(dao).hasFieldOrProperty("updatePublisher").isNotNull();
            assertThat(dao).hasFieldOrProperty("deletePublisher").isNotNull();
        }
    }

    @Nested
    @DisplayName("when new DAO operation")
    class WhenNewDAOoperation
    {
        private Connection connection;

        @BeforeEach
        void createNewDAO() throws SQLException
        {
            dataSource = injectTestDataSource();
            inserToTables(dataSource);
            dao = new JdbcPublisherDao(dataSource);
        }

        @AfterEach
        void deleteFromTable() throws SQLException
        {
            clearTables(dataSource);
        }

        @Test
        @DisplayName("setter and getter for dataSource")
        void testSetGetDataSource()
        {
            dao.setDataSource(null);
            assertNull(dao.getDataSource());
            dao.setDataSource(dataSource);
            assertEquals(dataSource, dao.getDataSource());
        }

        @Test
        @DisplayName("find ALL")
        void testFindAll()
        {
            List<Publisher> expected = Collections.singletonList(createTestPublisher13());
            assertEquals(expected, dao.findAll());
        }

        @Test
        @DisplayName("find by name")
        void testFindByFirstName()
        {
            List<Publisher> expected = Collections.singletonList(createTestPublisher13());
            assertEquals(expected, dao.findByName(TEST_PUBLISHER_NAME));
            assertTrue(dao.findByName("__NONE__").isEmpty());
        }

        @Test
        @DisplayName("find by id")
        void testFindById()
        {
            assertEquals(createTestPublisher13(), dao.findById(TEST_ID));
            assertThrows(EmptyResultDataAccessException.class, () -> dao.findById(Long.MAX_VALUE));
        }

        @Test
        @DisplayName("find ALL with detail")
        void findAllWithDetail()
        {
            Book expectedBook = createTestBook13();
            expectedBook.setAuthors(new ArrayList<>());
            expectedBook.getAuthors().add(createTestAuthor13());

            Publisher expectedPublisher = createTestPublisher13();
            expectedPublisher.setBooks(new ArrayList<>());
            expectedPublisher.getBooks().add(expectedBook);

            List<Publisher> expected = Collections.singletonList(expectedPublisher);

            List<Publisher> publishers = dao.findAllWithDetail();
            assertEquals(expected, publishers);
        }

        @Test
        @DisplayName("insert")
        void testInsert() throws SQLException
        {
            Publisher publisher = createTestPublisherAnother();
            assertEquals(0L, publisher.getId());

            boolean autoCommit = dataSource.getConnection().getAutoCommit();
            dataSource.getConnection().setAutoCommit(true);
            dao.insert(publisher);
            dataSource.getConnection().setAutoCommit(autoCommit);

            assertTrue(publisher.getId() > 0);
            assertEquals(2, dao.findAll().size());
            assertEquals(publisher, dao.findById(publisher.getId()));
        }

        @Test
        @DisplayName("update")
        void testUpdate() throws SQLException
        {
            Publisher publisherModify = dao.findById(TEST_ID);
            publisherModify.setPublisherName(TEST_PUBLISHER_NAME + TEST);

            boolean autoCommit = dataSource.getConnection().getAutoCommit();
            dataSource.getConnection().setAutoCommit(true);
            dao.update(publisherModify);
            assertEquals(publisherModify, dao.findById(TEST_ID));
            dataSource.getConnection().setAutoCommit(autoCommit);
        }

        @SuppressWarnings("Duplicates")
        @Test
        @DisplayName("delete")
        void testDelete() throws SQLException
        {
            assertEquals(1, dao.findAll().size());
            boolean autoCommit = dataSource.getConnection().getAutoCommit();
            dataSource.getConnection().setAutoCommit(true);
            assertThrows(DataIntegrityViolationException.class, () -> dao.delete(TEST_ID));

            Statement statement = dataSource.getConnection().createStatement();
            statement.execute(DELETE_FROM_AUTHOR_ISBN);
            statement.execute(DELETE_FROM_BOOK);
            dao.delete(TEST_ID);

            // TODO printFindAll();
            // assertEquals(0, dao.findAll().size());
            dao.delete(Long.MAX_VALUE);
            dataSource.getConnection().setAutoCommit(autoCommit);
        }
    }
}