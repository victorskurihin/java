package ru.otus.homework.services;

import org.junit.jupiter.api.*;
import ru.otus.homework.services.dao.JdbcBookDao;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.sql.Statement;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static ru.otus.outside.utils.TestData.*;

@DisplayName("Class AuthorsService")
class BooksServiceImplTest
{
    private BooksServiceImpl service;

    private DataSource dataSource = injectTestDataSource();

    private JdbcBookDao dao = new JdbcBookDao(dataSource);

    @Test
    @DisplayName("is instantiated with new AuthorsService()")
    void isInstantiatedWithNew()
    {
        new AuthorsServiceImpl(null);
    }

    @Nested
    @DisplayName("when new default")
    class WhenNew
    {
        @BeforeEach
        void createNew()
        {
            service = new BooksServiceImpl(dao);
        }

        @Test
        @DisplayName("injected values in AuthorsService()")
        void defaults()
        {
            assertThat(service).hasFieldOrPropertyWithValue("bookDao", dao);
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
        void createNew() throws SQLException
        {
            inserToTables(dataSource);
            service = new BooksServiceImpl(dao);
        }

        @AfterEach
        void deleteFromTables() throws SQLException
        {
            clearTables(dataSource);
        }

        @Test
        @DisplayName("create table for find All")
        void testCreateTableForFindAll() throws SQLException
        {
            assertEquals(1, service.createTableForFindAll());
        }

        @Test
        @DisplayName("create table for find All with details")
        void createTableForFindAllWithDetail()
        {
            assertEquals(1, service.createTableForFindAllWithDetail());
        }

        @Test
        @DisplayName("insert book to table")
        void insert() throws SQLException
        {
            long id = service.insert(TEST_ISBN + TEST, TEST_TITLE + TEST, 1, TEST_COPYRIGHT + TEST, TEST_ID);
            assertTrue(id > 0);
        }

        @Test
        @DisplayName("update book in table")
        void update()
        {
            long id = service.update(13L, TEST_ISBN, TEST_TITLE + TEST, 1, TEST_COPYRIGHT + TEST, TEST_ID);
            assertTrue(id > 0);
        }

        @Test
        @DisplayName("delete book from table")
        void delete()
        {
            clearTableAuthorISBN(dataSource);
            service.delete(TEST_ID);
            assertTrue(dao.findAll().isEmpty());
        }
    }
}