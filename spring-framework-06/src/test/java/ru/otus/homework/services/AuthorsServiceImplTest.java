package ru.otus.homework.services;

import org.jooq.DSLContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import ru.otus.homework.services.dao.JdbcAuthorDao;

import javax.sql.DataSource;

import java.sql.SQLException;
import java.sql.Statement;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static ru.otus.outside.utils.TestData.*;

@DisplayName("Class AuthorsService")
class AuthorsServiceImplTest
{
    private AuthorsServiceImpl service;

    private DataSource dataSource = injectTestDataSource();

    private DSLContext dsl;

    private JdbcAuthorDao dao = new JdbcAuthorDao(dataSource, dsl);

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
        void createNewQuestions()
        {
            service = new AuthorsServiceImpl(dao);
        }

        @Test
        @DisplayName("injected values in AuthorsService()")
        void defaults()
        {
            assertThat(service).hasFieldOrPropertyWithValue("authorDao", dao);
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
        void createNewQuestions()
        {
            service = new AuthorsServiceImpl(dao);
        }

        @SuppressWarnings("Duplicates")
        @Test
        @DisplayName("create table for find All ")
        void testGetSetFirstName() throws SQLException
        {
            assertEquals(0, service.createTableForFindAll());

            Statement statement = dataSource.getConnection().createStatement();
            statement.execute(INSERT_INTO_AUTHOR);
            assertEquals(1, service.createTableForFindAll());
            statement.execute(DELETE_FROM_AUTHOR);
        }

        @Test
        @DisplayName("insert author to table")
        void insert() throws SQLException
        {
            long id = service.insert("authorsServiceFirstName", "authorsServiceLastName");
            assertTrue(id > 0);
            // TODO assertNotNull(dao.findById(id));
            Statement statement = dataSource.getConnection().createStatement();
            statement.execute(DELETE_FROM_AUTHOR);
        }

        @Test
        @DisplayName("update author in table")
        void update()
        {
            long id = service.update(13L, "authorsServiceFirstName", "authorsServiceLastName");
            assertTrue(id > 0);
        }

        @Test
        @DisplayName("delete author from table")
        void delete()
        {
            service.delete(TEST_ID);
            assertTrue(dao.findAll().isEmpty());
        }
    }
}