package ru.otus.homework.services.dao;

import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.MappingSqlQuery;
import org.springframework.jdbc.object.SqlUpdate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.homework.models.Author;
import ru.otus.homework.models.Book;
import ru.otus.outside.exeptions.SQLRuntimeException;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.*;

import static ru.otus.homework.services.dao.JdbcAuthorDao.*;

@Repository("bookDao")
public class JdbcBookDao extends JdbcAbstractDao<Book> implements BookDao
{
    public static final String F_BOOK_ID = "book_id";

    public static final String F_ISBN = "isbn";

    public static final String F_TITLE = "title";

    public static final String F_EDITION_NUMBER = "edition_number";

    public static final String F_COPYRIGHT = "copyright";

    public static final String F_PUBLISHER_ID  = "publisher_id";

    public static String[] FIND_ALL_HEADER = {F_BOOK_ID, F_ISBN, F_TITLE, F_EDITION_NUMBER, F_COPYRIGHT, F_PUBLISHER_ID};

    public static String[] FIND_ALL_WITH_DETAIL_HEADER = {
        F_BOOK_ID, F_ISBN, F_TITLE, F_EDITION_NUMBER, F_COPYRIGHT, F_FIRST_NAME, F_LAST_NAME
    };

    public static final String TBL_BOOK = "book";

    public static final String LIST_DATA_FIELDS = F_ISBN + ", "
                                                + F_TITLE + ", "
                                                + F_EDITION_NUMBER + ", "
                                                + F_COPYRIGHT + ", "
                                                + F_PUBLISHER_ID;
    public static final String LIST_DATA_MARKER = ':' + F_ISBN + ", "
                                                + ':' + F_TITLE + ", "
                                                + ':' + F_EDITION_NUMBER + ", "
                                                + ':' + F_COPYRIGHT + ", "
                                                + ':' + F_PUBLISHER_ID;
    public static final String LIST_ALL_FIELDS = F_BOOK_ID + ", " + LIST_DATA_FIELDS;

    public static final String SELECT_ALL = "SELECT " + LIST_ALL_FIELDS + " FROM " + TBL_BOOK;

    public static final String BOOK_ID = "bookId";

    public static final String BOOK_ISBN = "bookIsbn";

    public static final String BOOK_TITLE = "bookTitle";

    public static final String SELECT_ALL_WHERE_ID = SELECT_ALL + " WHERE " + F_BOOK_ID + " = :" + BOOK_ID;

    public static final String SELECT_ALL_WHERE_ISBN = SELECT_ALL + " WHERE " + F_ISBN + " = :" + BOOK_ISBN;

    public static final String SELECT_ALL_WITH_DETAIL = "SELECT "
        + "b." + F_BOOK_ID + ", "
        + "b." + F_ISBN + ", "
        + "b." + F_TITLE + ", "
        + "b." + F_EDITION_NUMBER + ", "
        + "b." + F_COPYRIGHT + ", "
        + "b." + F_PUBLISHER_ID + ", "
        + "a." + F_AUTHOR_ID + ", "
        + "a." + F_FIRST_NAME + ", "
        + "a." + F_LAST_NAME
        + " FROM " + TBL_BOOK + " b"
        + " LEFT OUTER JOIN author_isbn ai ON b." + F_ISBN + " = ai." + F_ISBN
        + " LEFT OUTER JOIN " + TBL_AUTHOR + " a ON ai." + F_AUTHOR_ID + " = a." + F_AUTHOR_ID;

    private DataSource dataSource;
    public class SelectBookByTitle extends MappingSqlQuery<Book>
    {
        public static final String SQL = SELECT_ALL + " WHERE " + F_TITLE + " = :" + BOOK_TITLE;

        SelectBookByTitle(DataSource dataSource)
        {
            super(dataSource, SQL);
            super.declareParameter(new SqlParameter(BOOK_TITLE, Types.VARCHAR));
        }

        @Override
        protected Book mapRow(ResultSet resultSet, int i) throws SQLException
        {
            return fetchBook(resultSet);
        }
    }

    private SelectBookByTitle selectBookByTitle;

    public class Insert extends SqlUpdate
    {
        public static final String SQL = "INSERT INTO " + TBL_BOOK + " (" + LIST_DATA_FIELDS
                                       + ") values (" + LIST_DATA_MARKER + ')';

        Insert(DataSource dataSource)
        {
            super(dataSource, SQL);
            super.declareParameter(new SqlParameter(F_ISBN, Types.VARCHAR));
            super.declareParameter(new SqlParameter(F_TITLE, Types.VARCHAR));
            super.declareParameter(new SqlParameter(F_EDITION_NUMBER, Types.INTEGER));
            super.declareParameter(new SqlParameter(F_COPYRIGHT, Types.VARCHAR));
            super.declareParameter(new SqlParameter(F_PUBLISHER_ID, Types.BIGINT));
            super.setGeneratedKeysColumnNames(F_BOOK_ID);
            super.setReturnGeneratedKeys(true);
        }
    }

    private Insert insertBook;

    public class Update extends SqlUpdate
    {
        public static final String SQL = "UPDATE " + TBL_BOOK
                                       + " SET " + F_ISBN + " = :" + F_ISBN
                                       + ", " + F_TITLE + "= :" + F_TITLE
                                       + ", " + F_EDITION_NUMBER + "= :" + F_EDITION_NUMBER
                                       + ", " + F_COPYRIGHT + "= :" + F_COPYRIGHT
                                       + ", " + F_PUBLISHER_ID + "= :" + F_PUBLISHER_ID
                                       + " WHERE " + F_BOOK_ID + " = :" + F_BOOK_ID;

        public Update(DataSource dataSource)
        {
            super(dataSource, SQL);
            super.declareParameter(new SqlParameter(F_ISBN, Types.VARCHAR));
            super.declareParameter(new SqlParameter(F_TITLE, Types.VARCHAR));
            super.declareParameter(new SqlParameter(F_EDITION_NUMBER, Types.INTEGER));
            super.declareParameter(new SqlParameter(F_COPYRIGHT, Types.VARCHAR));
            super.declareParameter(new SqlParameter(F_PUBLISHER_ID, Types.BIGINT));
            super.declareParameter(new SqlParameter(F_BOOK_ID, Types.BIGINT));
        }
    }

    private Update updateBook;

    private Delete deleteBook;

    public JdbcBookDao(DataSource dataSource)
    {
        super(dataSource);
        this.dataSource = dataSource;
        this.selectBookByTitle = new SelectBookByTitle(dataSource);
        this.insertBook = new Insert(dataSource);
        this.updateBook = new Update(dataSource);
        this.deleteBook = new Delete(dataSource, TBL_BOOK, F_BOOK_ID, BOOK_ID);
    }

    public DataSource getDataSource()
    {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource)
    {
        this.dataSource = dataSource;
    }

    public static Book fetchBook(ResultSet resultSet) throws SQLException
    {
        Book book = new Book();
        book.setId(resultSet.getLong(F_BOOK_ID));
        book.setIsbn(resultSet.getString(F_ISBN));
        book.setTitle(resultSet.getString(F_TITLE));
        book.setEditionNumber(resultSet.getInt(F_EDITION_NUMBER));
        book.setCopyright(resultSet.getString(F_COPYRIGHT));
        book.setPublisherId(resultSet.getLong(F_PUBLISHER_ID));

        return book;
    }

    public static List<Book> fetchListFrom(ResultSet rs)
    {
        Map<Long, Book> map = new HashMap<>();
        Book book;

        try {
            while (rs.next()) {
                Long id = rs.getLong(F_BOOK_ID);
                book = map.get(id);

                if (null == book) {
                    book = fetchBook(rs);
                    book.setAuthors(new ArrayList<>());
                    map.put(id, book);
                }

                long authorId = rs.getLong(F_AUTHOR_ID);

                if (authorId > 0) {
                    JdbcAuthorDao.fetchAuthor(rs);
                    Author author = JdbcAuthorDao.fetchAuthor(rs);
                    book.getAuthors().add(author);
                }
            }
        }
        catch (SQLException e) {
            throw new SQLRuntimeException(e);
        }

        return new ArrayList<>(map.values());
    }

    @Override
    public Book fetchFrom(ResultSet resultSet) throws SQLException
    {
        return fetchBook(resultSet);
    }
    @Override
    public List<Book> findAll()
    {
        return super.findAll(SELECT_ALL);
    }

    @Override
    public Book findById(long id)
    {
        return super.findById(SELECT_ALL_WHERE_ID, BOOK_ID, id);
    }

    @Override
    public Book findByIsbn(String isbn)
    {
        return super.findBySKey(SELECT_ALL_WHERE_ISBN, BOOK_ISBN, isbn);
    }

    @Override
    public String findIsbnById(long id)
    {
        return findStringFieldNameById(TBL_BOOK, F_ISBN, F_BOOK_ID, BOOK_ID, id);
    }

    @Override
    public List<Book> findByTitle(String title)
    {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put(BOOK_TITLE, title);

        return selectBookByTitle.executeByNamedParam(paramMap);
    }

    @Override
    public List<Book> findAllWithDetail()
    {
        return super.findAllWithFunction(SELECT_ALL_WITH_DETAIL, JdbcBookDao::fetchListFrom);
    }

    @Override
    public void insert(Book book)
    {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put(F_ISBN, book.getIsbn());
        paramMap.put(F_TITLE, book.getTitle());
        paramMap.put(F_EDITION_NUMBER, book.getEditionNumber());
        paramMap.put(F_COPYRIGHT, book.getCopyright());
        paramMap.put(F_PUBLISHER_ID, book.getPublisherId());

        KeyHolder keyHolder = new GeneratedKeyHolder();
        insertBook.updateByNamedParam(paramMap, keyHolder);
        book.setId(Objects.requireNonNull(keyHolder.getKey()).longValue());
    }

    @Override
    public void update(Book book)
    {
        Map<String, Object> paramMap = new HashMap<>();

        paramMap.put(F_ISBN, book.getIsbn());
        paramMap.put(F_TITLE, book.getTitle());
        paramMap.put(F_EDITION_NUMBER, book.getEditionNumber());
        paramMap.put(F_COPYRIGHT, book.getCopyright());
        paramMap.put(F_PUBLISHER_ID, book.getPublisherId());
        paramMap.put(F_BOOK_ID, book.getId());

        updateBook.updateByNamedParam(paramMap);
    }

    @Override
    public void delete(long contactId)
    {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put(BOOK_ID, contactId);
        deleteBook.updateByNamedParam(paramMap);
    }
}
