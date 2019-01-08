package ru.otus.homework.services.dao;

import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.MappingSqlQuery;
import org.springframework.jdbc.object.SqlUpdate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.homework.models.Author;
import ru.otus.homework.models.Book;
import ru.otus.homework.models.Publisher;
import ru.otus.outside.exeptions.SQLRuntimeException;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.*;

import static ru.otus.homework.services.dao.JdbcAuthorDao.*;
import static ru.otus.homework.services.dao.JdbcBookDao.*;

@Repository("publisherDao")
public class JdbcPublisherDao extends JdbcAbstractDao<Publisher> implements PublisherDao
{
    public static final String F_PUBLISHER_ID = "publisher_id";

    public static final String F_PUBLISHER_NAME = "publisher_name";

    public static final String TBL_PUBLISHER = "publisher";

    public static String[] FIND_ALL_HEADER = {F_PUBLISHER_ID, F_PUBLISHER_NAME};

    public static String[] FIND_ALL_WITH_DETAIL_HEADER = {
        F_BOOK_ID, F_ISBN, F_TITLE, F_EDITION_NUMBER, F_PUBLISHER_NAME, F_COPYRIGHT, F_FIRST_NAME, F_LAST_NAME
    };

    public static final String SELECT_ALL = "SELECT " + F_PUBLISHER_ID + ", " + F_PUBLISHER_NAME
                                          + " FROM " + TBL_PUBLISHER;

    public static final String PUBLISHER_ID = "publisherId";

    public static final String PUBLISHER_N = "publisherN";

    public static final String SELECT_ALL_WHERE_ID = SELECT_ALL + " WHERE " + F_PUBLISHER_ID + " = :" + PUBLISHER_ID;

    public static final String SELECT_ALL_WITH_DETAIL = "SELECT "
        + "p." + F_PUBLISHER_ID + ", "
        + "p." + F_PUBLISHER_NAME + ", "
        + "b." + F_BOOK_ID + ", "
        + "b." + F_ISBN + ", "
        + "b." + F_TITLE + ", "
        + "b." + F_EDITION_NUMBER + ", "
        + "b." + F_COPYRIGHT + ", "
        + "b." + F_PUBLISHER_ID + ", "
        + "a." + F_AUTHOR_ID + ", "
        + "a." + F_FIRST_NAME + ", "
        + "a." + F_LAST_NAME
        + " FROM " + TBL_PUBLISHER + " p"
        + " LEFT JOIN " + TBL_BOOK + " b ON p." + F_PUBLISHER_ID + " = b." + F_PUBLISHER_ID
        + " LEFT OUTER JOIN author_isbn ai ON b." + F_ISBN + " = ai." + F_ISBN
        + " LEFT OUTER JOIN " + TBL_AUTHOR + " a ON ai." + F_AUTHOR_ID + " = a." + F_AUTHOR_ID;

    private DataSource dataSource;

    public class SelectPublisherByName extends MappingSqlQuery<Publisher>
    {
        SelectPublisherByName(DataSource dataSource, String fieldName, String sid)
        {
            super(dataSource, SELECT_ALL + " WHERE " + fieldName + " LIKE :" + sid);
            super.declareParameter(new SqlParameter(sid, Types.VARCHAR));
        }

        @Override
        protected Publisher mapRow(ResultSet resultSet, int i) throws SQLException
        {
            return fetchPublisher(resultSet);
        }
    }

    private SelectPublisherByName selectPublisherByName;

    public class Insert extends SqlUpdate
    {
        public static final String SQL = "INSERT INTO " + TBL_PUBLISHER  + " (" + F_PUBLISHER_NAME
                                       + ") values (:" + F_PUBLISHER_NAME + ')';

        Insert(DataSource dataSource)
        {
            super(dataSource, SQL);
            super.declareParameter(new SqlParameter(F_PUBLISHER_NAME, Types.VARCHAR));
            super.setGeneratedKeysColumnNames(F_PUBLISHER_ID);
            super.setReturnGeneratedKeys(true);
        }
    }

    private Insert insertPublisher;

    public class Update extends SqlUpdate
    {
        public static final String SQL = "UPDATE " + TBL_PUBLISHER
                                       + " SET " + F_PUBLISHER_NAME + " = :" + F_PUBLISHER_NAME
                                       + " WHERE  " + F_PUBLISHER_ID + " = :" + F_PUBLISHER_ID;

        public Update(DataSource dataSource)
        {
            super(dataSource, SQL);
            super.declareParameter(new SqlParameter(F_PUBLISHER_NAME, Types.VARCHAR));
            super.declareParameter(new SqlParameter(F_PUBLISHER_ID, Types.BIGINT));
        }
    }

    private Update updatePublisher;

    private Delete deletePublisher;

    public JdbcPublisherDao(DataSource dataSource)
    {
        super(dataSource);
        this.dataSource = dataSource;
        this.selectPublisherByName = new SelectPublisherByName(dataSource, F_PUBLISHER_NAME, PUBLISHER_N);
        this.insertPublisher = new Insert(dataSource);
        this.updatePublisher = new Update(dataSource);
        this.deletePublisher = new Delete(dataSource, TBL_PUBLISHER, F_PUBLISHER_ID, PUBLISHER_ID);
    }

    public DataSource getDataSource()
    {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource)
    {
        this.dataSource = dataSource;
    }

    public static Publisher fetchPublisher(ResultSet resultSet) throws SQLException
    {
        Publisher publisher = new Publisher();
        publisher.setId(resultSet.getLong(F_PUBLISHER_ID));
        publisher.setPublisherName(resultSet.getString(F_PUBLISHER_NAME));

        return publisher;
    }

    @Override
    public Publisher fetchFrom(ResultSet resultSet) throws SQLException
    {
        return fetchPublisher(resultSet);
    }

    @Override
    public List<Publisher> findAll()
    {
        return super.findAll(SELECT_ALL);
    }

    @Override
    public Publisher findById(long id)
    {
        return super.findById(SELECT_ALL_WHERE_ID, PUBLISHER_ID, id);
    }

    @Override
    public List<Publisher> findByName(String name)
    {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put(PUBLISHER_N, name);

        return selectPublisherByName.executeByNamedParam(paramMap);
    }

    @Override
    public List<Publisher> findAllWithDetail()
    {
        return super.findAllWithFunction(SELECT_ALL_WITH_DETAIL, JdbcPublisherDao::fetchListFrom);
    }

    private static List<Publisher> fetchListFrom(ResultSet rs)
    {
        Map<Long, Book> booksMap = new HashMap<>();
        Map<Long, Publisher> publishersMap = new HashMap<>();
        Publisher publisher;

        try {
            int i = 1;
            while (rs.next()) {
                Long id = rs.getLong(F_PUBLISHER_ID);
                publisher = publishersMap.get(id);

                if (null == publisher) {
                    publisher = fetchPublisher(rs);
                    publisher.setBooks(new ArrayList<>());
                    publishersMap.put(id, publisher);
                }

                long bookId = rs.getLong(F_BOOK_ID);

                if (bookId > 0) {
                    JdbcAuthorDao.fetchAuthor(rs);
                    Book book = booksMap.get(bookId);

                    if (null == book) {
                        book = JdbcBookDao.fetchBook(rs);
                        book.setAuthors(new ArrayList<>());
                        booksMap.put(bookId, book);
                        publisher.getBooks().add(book);
                    }

                    long authorId = rs.getLong(F_AUTHOR_ID);

                    if (authorId > 0) {
                        JdbcAuthorDao.fetchAuthor(rs);
                        Author author = JdbcAuthorDao.fetchAuthor(rs);
                        book.getAuthors().add(author);
                    }
                }
            }
        }
        catch (SQLException e) {
            throw new SQLRuntimeException(e);
        }

        return new ArrayList<>(publishersMap.values());
    }

    @Override
    public void insert(Publisher publisher)
    {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put(F_PUBLISHER_NAME, publisher.getPublisherName());

        KeyHolder keyHolder = new GeneratedKeyHolder();
        insertPublisher.updateByNamedParam(paramMap, keyHolder);
        publisher.setId(Objects.requireNonNull(keyHolder.getKey()).longValue());
    }

    @Override
    public void update(Publisher publisher)
    {
        Map<String, Object> paramMap = new HashMap<>();

        paramMap.put(F_PUBLISHER_NAME, publisher.getPublisherName());
        paramMap.put(F_PUBLISHER_ID, publisher.getId());

        updatePublisher.updateByNamedParam(paramMap);
    }

    @Override
    public void delete(long id)
    {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put(PUBLISHER_ID, id);
        deletePublisher.updateByNamedParam(paramMap);
    }
}
