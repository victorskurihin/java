package ru.otus.homework.services.dao;

import org.junit.jupiter.api.*;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import ru.otus.homework.models.Book;

import javax.sql.DataSource;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static ru.otus.outside.utils.TestData.*;

@DisplayName("Class JdbcBookDao")
class JdbcBookDaoTest
{
    private DataSource dataSource;

    private JdbcBookDao dao;

    @Test
    @DisplayName("is instantiated with new JdbcPersonDao()")
    void isInstantiatedWithNew()
    {
        dataSource = injectTestDataSource();
        new JdbcBookDao(dataSource);
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
            dao = new JdbcBookDao(dataSource);
        }

        @Test
        @DisplayName("injected values in JdbcBookDao()")
        void defaults()
        {
            assertThat(dao).hasFieldOrPropertyWithValue("dataSource", dataSource);
            assertThat(dao).hasFieldOrProperty("namedParameterJdbcTemplate").isNotNull();
            assertThat(dao).hasFieldOrProperty("selectBookByTitle").isNotNull();
            assertThat(dao).hasFieldOrProperty("insertBook").isNotNull();
            assertThat(dao).hasFieldOrProperty("updateBook").isNotNull();
            assertThat(dao).hasFieldOrProperty("deleteBook").isNotNull();
        }
    }

    @Nested
    @DisplayName("when new DAO operation")
    class WhenNewDAOoperation
    {
        @BeforeEach
        void createNewDAO() throws SQLException
        {
            dataSource = injectTestDataSource();
            inserToTables(dataSource);
            dao = new JdbcBookDao(dataSource);
        }

        @AfterEach
        void deleteFromTables() throws SQLException
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
            List<Book> expected = Collections.singletonList(createTestBook13());
            assertEquals(expected, dao.findAll());
        }

        @Test
        @DisplayName("find by id")
        void testFindById()
        {
            assertEquals(createTestBook13(), dao.findById(TEST_ID));
            assertThrows(EmptyResultDataAccessException.class, () -> dao.findById(Long.MAX_VALUE));
        }

        @Test
        @DisplayName("find by ISBN")
        void testFindByIsbn()
        {
            assertEquals(createTestBook13(), dao.findByIsbn(TEST_ISBN));
            assertThrows(EmptyResultDataAccessException.class, () -> dao.findByIsbn(""));
        }

        @Test
        @DisplayName("find by title")
        void testFindByTitle()
        {
            List<Book> expected = Collections.singletonList(createTestBook13());
            assertEquals(expected, dao.findByTitle(TEST_TITLE));
        }

        @Test
        @DisplayName("find ISBN by id")
        void testFindIsbnById()
        {
            Book expected = createTestBook13();
            assertEquals(expected.getIsbn(), dao.findIsbnById(TEST_ID));
        }

        @Test
        @DisplayName("find ALL with detail")
        void findAllWithDetail()
        {
            Book expectedBook = createTestBook13();
            expectedBook.setAuthors(new ArrayList<>());
            expectedBook.getAuthors().add(createTestAuthor13());
            List<Book> expected = Collections.singletonList(expectedBook);
            List<Book> books = dao.findAllWithDetail();
            assertEquals(expected, books);
            // System.out.println("books = " + books);
        }

        @Test
        @DisplayName("insert")
        void testInsert() throws SQLException
        {
            Book book = createTestBookAnother();
            assertEquals(0L, book.getId());

            boolean autoCommit = autoCommitOn(dataSource);
            dao.insert(book);
            autoCommitRestore(dataSource, autoCommit);

            assertTrue(book.getId() > 0);
            assertEquals(2, dao.findAll().size());
            assertEquals(book, dao.findById(book.getId()));
        }

        @Test
        @DisplayName("update")
        void testUpdate() throws SQLException
        {
            Book bookModify = dao.findById(TEST_ID);
            bookModify.setTitle(TEST_TITLE + TEST);
            bookModify.setCopyright(TEST_COPYRIGHT + TEST);

            boolean autoCommit = autoCommitOn(dataSource);
            dao.update(bookModify);
            assertEquals(bookModify, dao.findById(TEST_ID));
            autoCommitRestore(dataSource, autoCommit);
        }

        @SuppressWarnings("Duplicates")
        @Test
        @DisplayName("delete")
        void testDelete() throws SQLException
        {
            assertEquals(1, dao.findAll().size());
            assertThrows(DataIntegrityViolationException.class, () -> dao.delete(TEST_ID));

            boolean autoCommit = autoCommitOn(dataSource);
            Statement statement = dataSource.getConnection().createStatement();
            statement.execute(DELETE_FROM_AUTHOR_ISBN);
            dao.delete(TEST_ID);

            dao.delete(Long.MAX_VALUE);
            autoCommitRestore(dataSource, autoCommit);

            assertEquals(0, dao.findAll().size());
        }
    }
}