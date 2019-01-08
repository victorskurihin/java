package ru.otus.homework.services;

import org.junit.jupiter.api.*;
import ru.otus.homework.services.dao.JdbcPublisherDao;

import javax.sql.DataSource;
import java.sql.SQLException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static ru.otus.outside.utils.TestData.*;

@DisplayName("Class PublishersService")
class PublishersServiceImplTest
{
    private PublishersServiceImpl service;

    private DataSource dataSource = injectTestDataSource();

    private JdbcPublisherDao dao = new JdbcPublisherDao(dataSource);

    @Test
    @DisplayName("is instantiated with new PublishersService()")
    void isInstantiatedWithNew()
    {
        new AuthorsServiceImpl(null);
    }

    @Nested
    @DisplayName("when new default")
    class WhenNew
    {
        @BeforeEach
        void createNewQuestions()
        {
            service = new PublishersServiceImpl(dao);
        }

        @Test
        @DisplayName("injected values in PublishersService()")
        void defaults()
        {
            assertThat(service).hasFieldOrPropertyWithValue("publisherDao", dao);
            assertThat(service).hasFieldOrPropertyWithValue("tableBuilder", null);
        }

        @Test
        @DisplayName("Getter for tableBuilder")
        void testGetSetFirstName()
        {
            assertNull(service.getTableBuilder());
        }
    }

    @Nested
    @DisplayName("methods")
    class Methots
    {
        @BeforeEach
        void createNew()
        {
            inserToTables(dataSource);
            service = new PublishersServiceImpl(dao);
        }

        @AfterEach
        void deleteFromTables() throws SQLException
        {
            clearTables(dataSource);
        }

        @Test
        @DisplayName("create table for find All ")
        void testCreateTableForFindAll() throws SQLException
        {
            assertEquals(1, service.createTableForFindAll());
        }

        @Test
        @DisplayName("create table for find All with details")
        void createTableForFindAllWithDetail()
        {
            assertEquals(1, service.createTableAllBookWithDetail());
        }

        @Test
        @DisplayName("insert publisher to table")
        void insert() throws SQLException
        {
            long id = service.insert("publishersServiceFirstName");
            assertTrue(id > 0);
        }

        @Test
        @DisplayName("update publisher in table")
        void update()
        {
            long id = service.update(13L, "publishersServiceFirstName");
            assertTrue(id > 0);
        }

        @Test
        @DisplayName("delete publisher from table")
        void delete()
        {
            clearTableAuthorIsbnBook(dataSource);
            service.delete(TEST_ID);
            assertTrue(dao.findAll().isEmpty());
        }
    }
}