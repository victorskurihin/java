package ru.otus.outside.utils;

import org.h2.jdbcx.JdbcDataSource;
import ru.otus.homework.models.Author;
import ru.otus.homework.models.Book;
import ru.otus.homework.models.DataSet;
import ru.otus.homework.models.Publisher;
import ru.otus.homework.services.dao.JdbcAuthorDao;
import ru.otus.homework.services.dao.JdbcPublisherDao;

import javax.sql.DataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static ru.otus.homework.services.dao.JdbcAuthorDao.*;
import static ru.otus.homework.services.dao.JdbcBookDao.F_ISBN;
import static ru.otus.homework.services.dao.JdbcBookDao.LIST_ALL_FIELDS;
import static ru.otus.homework.services.dao.JdbcBookDao.TBL_BOOK;
import static ru.otus.homework.services.dao.JdbcPublisherDao.F_PUBLISHER_NAME;
import static ru.otus.homework.services.dao.JdbcPublisherDao.TBL_PUBLISHER;

public class TestData
{
    public static final int TEST_NUM = 3;
    public static final long TEST_ID = 13L;
    public static final String TEST = "test";
    public static final String TEST_FIRST_NAME = "testFirstName";
    public static final String TEST_LAST_NAME = "testLastName";
    public static final String TEST_ISBN = "testIsbn";
    public static final String TEST_TITLE = "testTitle";
    public static final String TEST_COPYRIGHT = "testCopyright";
    public static final String TEST_PUBLISHER_NAME = "testPublisherNamr";
    public static final String JDBC_URL = "jdbc:h2:mem:test;INIT=runscript from 'classpath:createTestDB.sql'";

    public static final String INSERT_INTO_AUTHOR = "INSERT INTO " + TBL_AUTHOR + '('
        + JdbcAuthorDao.F_AUTHOR_ID + ", " + F_FIRST_NAME + ", " + F_LAST_NAME + ") VALUES (13, '" + TEST_FIRST_NAME + "', '"
        + TEST_LAST_NAME + "')";

    public static final String INSERT_INTO_BOOK = "INSERT INTO " + TBL_BOOK + '(' + LIST_ALL_FIELDS + ") VALUES (13, '"
        + TEST_ISBN + "', '" + TEST_TITLE + "', " + TEST_NUM + ", '" + TEST_COPYRIGHT + "', " + TEST_ID + ')';

    public static final String INSERT_INTO_PUBLISHER = "INSERT INTO " + TBL_PUBLISHER + "(" + JdbcPublisherDao.F_PUBLISHER_ID
        + ", " + F_PUBLISHER_NAME + ")" + " VALUES (" + TEST_ID + ", '" + TEST_PUBLISHER_NAME + "')";

    public static final String INSERT_INTO_AUTHOR_ISBN = " INSERT INTO author_isbn (" + F_ISBN + ", " + F_AUTHOR_ID
        + ") VALUES ('" + TEST_ISBN + "', " + TEST_ID + ')';

    public static final String DELETE_FROM_AUTHOR_ISBN = "DELETE FROM author_isbn";

    public static final String DELETE_FROM_AUTHOR = "DELETE FROM " + TBL_AUTHOR;

    public static final String DELETE_FROM_BOOK = "DELETE FROM " + TBL_BOOK;

    public static final String DELETE_FROM_PUBLISHER = "DELETE FROM " + TBL_PUBLISHER;

    public static DataSource injectTestDataSource()
    {
        JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setURL(JDBC_URL);

        return dataSource;
    }

    public static Author createTestAuthor13()
    {
        return new Author(TEST_ID, TEST_FIRST_NAME, TEST_LAST_NAME, null);
    }

    public static Author createTestAuthorAnother()
    {
        Author author = new Author();
        author.setFirstName(TEST_FIRST_NAME + TEST);
        author.setLastName(TEST_LAST_NAME + TEST);

        return author;
    }

    public static Book createTestBook13()
    {
        return new Book(TEST_ID, TEST_ISBN, TEST_TITLE, TEST_NUM, TEST_COPYRIGHT, TEST_ID, null);
    }

    public static Book createTestBookAnother()
    {
        Book book = new Book();
        book.setIsbn(TEST_ISBN + TEST);
        book.setTitle(TEST_TITLE + TEST);
        book.setCopyright(TEST_COPYRIGHT + TEST);
        book.setPublisherId(TEST_ID);

        return book;
    }

    public static Publisher createTestPublisher13()
    {
        return new Publisher(TEST_ID, TEST_PUBLISHER_NAME, null);
    }

    public static Publisher createTestPublisherAnother()
    {
        Publisher publisher = new Publisher();
        publisher.setPublisherName(TEST_PUBLISHER_NAME + TEST);

        return publisher;
    }

    public static void inserToTables(DataSource dataSource)
    {
        //noinspection Duplicates
        try (Statement statement = dataSource.getConnection().createStatement()) {
            statement.execute(INSERT_INTO_PUBLISHER);
            statement.execute(INSERT_INTO_BOOK);
            statement.execute(INSERT_INTO_AUTHOR);
            statement.execute(INSERT_INTO_AUTHOR_ISBN);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void clearTables(DataSource dataSource)
    {
        //noinspection Duplicates
        try (Statement statement = dataSource.getConnection().createStatement()) {
            statement.execute(DELETE_FROM_AUTHOR_ISBN);
            statement.execute(DELETE_FROM_AUTHOR);
            statement.execute(DELETE_FROM_BOOK);
            statement.execute(DELETE_FROM_PUBLISHER);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void clearTableAuthorISBN(DataSource dataSource)
    {
        //noinspection Duplicates
        try (Statement statement = dataSource.getConnection().createStatement()) {
            statement.execute(DELETE_FROM_AUTHOR_ISBN);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void clearTableAuthorIsbnBook(DataSource dataSource)
    {
        //noinspection Duplicates
        try (Statement statement = dataSource.getConnection().createStatement()) {
            statement.execute(DELETE_FROM_AUTHOR_ISBN);
            statement.execute(DELETE_FROM_BOOK);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean autoCommitOn(DataSource dataSource) throws SQLException
    {
        boolean result = dataSource.getConnection().getAutoCommit();
        dataSource.getConnection().setAutoCommit(true);

        return result;
    }

    public static void autoCommitRestore(DataSource dataSource, boolean state) throws SQLException
    {
        dataSource.getConnection().setAutoCommit(state);
    }
}
