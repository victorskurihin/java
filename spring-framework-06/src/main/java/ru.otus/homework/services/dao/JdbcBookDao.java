package ru.otus.homework.services.dao;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.homework.models.Author;
import ru.otus.homework.models.Book;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static ru.otus.outside.utils.JdbcHelper.getIntegerOrNull;
import static ru.otus.outside.utils.JdbcHelper.getStringOrNull;

@Repository("bookDao")
public class JdbcBookDao implements BookDao
{
    public static String[] FIND_ALL_HEADER = {
        "book_id", "isbn", "title", "edition_number", "copyright", "publisher_id", "genre_id"
    };

    public static String[] FIND_ALL_HEADER_BOOKS_AUTHORS = {
        "book_id", "isbn", "title", "edition_number", "copyright", "publisher_name", "genre", "first_name", "last_name"
    };

    private NamedParameterJdbcTemplate jdbc;

    public JdbcBookDao(NamedParameterJdbcTemplate jdbc)
    {
        this.jdbc = jdbc;
    }

    static Book buildBy(ResultSet resultSet)
    {
        Book b = new Book();
        try {
            b.setId(resultSet.getLong("book_id"));
            b.setIsbn(getStringOrNull(resultSet, "isbn"));
            b.setTitle(getStringOrNull(resultSet, "title"));
            Integer editionNumber = getIntegerOrNull(resultSet, "edition_number");
            b.setEditionNumber(editionNumber == null ? 0 : editionNumber);
            b.setCopyright(getStringOrNull(resultSet, "copyright"));
            b.setPublisher(JdbcPublisherDao.buildBy(resultSet));
            b.setGenre(JdbcGenreDao.buildBy(resultSet));

            return b;
        }
        catch (SQLException e) {
            return null;
        }
    }

    @Override
    public List<Book> findAll()
    {
        return jdbc.query(
            "SELECT b.book_id, b.isbn, b.title, b.edition_number, b.copyright, b.publisher_id"
            + ", p.publisher_name, b.genre_id, g.genre"
            + " FROM book b"
            + " LEFT JOIN publisher p ON b.publisher_id = p.publisher_id"
            + " LEFT JOIN genre g ON b.genre_id = g.genre_id",
            (rs, rowNum) -> buildBy(rs)
        );
    }

    @Override
    public Book findById(long id)
    {
        return jdbc.queryForObject(
            "SELECT b.book_id, b.isbn, b.title, b.edition_number, b.copyright, b.publisher_id"
            + ", p.publisher_name, b.genre_id, g.genre"
            + " FROM book b"
            + " LEFT JOIN publisher p ON b.publisher_id = p.publisher_id"
            + " LEFT JOIN genre g ON b.genre_id = g.genre_id"
            + " WHERE b.book_id = :id",
            new MapSqlParameterSource("id", id),
            (rs, numRow) -> buildBy(rs)
        );
    }

    @Override
    public Book findByIsbn(String isbn)
    {
        return jdbc.queryForObject(
            "SELECT b.book_id, b.isbn, b.title, b.edition_number, b.copyright, b.publisher_id"
            + ", p.publisher_name, b.genre_id, g.genre"
            + " FROM book b"
            + " LEFT JOIN publisher p ON b.publisher_id = p.publisher_id"
            + " LEFT JOIN genre g ON b.genre_id = g.genre_id"
            + " WHERE b.isbn = :isbn",
            new MapSqlParameterSource("isbn", isbn),
            (rs, numRow) -> buildBy(rs)
        );
    }

    @Override
    public List<Book> findByTitle(String title)
    {
        return jdbc.query(
            "SELECT b.book_id, b.isbn, b.title, b.edition_number, b.copyright, b.publisher_id"
            + ", p.publisher_name, b.genre_id, g.genre"
            + " FROM book b"
            + " LEFT JOIN publisher p ON b.publisher_id = p.publisher_id"
            + " LEFT JOIN genre g ON b.genre_id = g.genre_id"
            + " WHERE b.title LIKE :title",
            new MapSqlParameterSource("title", title),
            (rs, rowNum) -> buildBy(rs)
        );
    }

    @Override
    public Map<Book, Author> findAllBooksAndTheirAuthors()
    {
        return jdbc.query(
            "SELECT b.book_id, b.isbn, b.title, b.edition_number, b.copyright, b.publisher_id"
            + ", p.publisher_name, b.genre_id, g.genre, a.author_id, a.first_name, a.last_name"
            + " FROM book b"
            + " LEFT JOIN publisher p ON b.publisher_id = p.publisher_id"
            + " LEFT JOIN genre g ON b.genre_id = g.genre_id"
            + " LEFT OUTER JOIN author_isbn ai ON b.book_id = ai.book_id"
            + " LEFT OUTER JOIN author a ON ai.author_id = a.author_id"
            , rs -> {
                Map<Book, Author> map = new HashMap<>();

                while (rs.next()) {
                    Book book = buildBy(rs);
                    Author author = JdbcAuthorDao.buildBy(rs);
                    /*
                    System.out.println("book = " + book);
                    System.out.println("author = " + author);
                    System.out.println();
                    */
                    map.put(book, author);
                }

                return map;
            }

        );
    }

    @Override
    public void insert(Book entity)
    {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("id", entity.getId());
        namedParameters.addValue("isbn", entity.getIsbn());
        namedParameters.addValue("title", entity.getTitle());
        namedParameters.addValue("edition_number", entity.getEditionNumber());
        namedParameters.addValue("copyright", entity.getCopyright());
        namedParameters.addValue("publisher_id", entity.getPublisher().getId());
        namedParameters.addValue("genre_id", entity.getGenre().getId());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update(
            "INSERT INTO book (isbn, title, edition_number, copyright, publisher_id, genre_id)"
            + " VALUES (:isbn, :title, :edition_number, :copyright, :publisher_id, :genre_id)"
            , namedParameters, keyHolder
        );
        entity.setId(Objects.requireNonNull(keyHolder.getKey()).longValue());
    }

    @Override
    public void update(Book entity)
    {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("isbn", entity.getIsbn());
        namedParameters.addValue("title", entity.getTitle());
        namedParameters.addValue("edition_number", entity.getEditionNumber());
        namedParameters.addValue("copyright", entity.getCopyright());
        namedParameters.addValue("publisher_id", entity.getPublisher().getId());
        namedParameters.addValue("genre_id", entity.getGenre().getId());
        namedParameters.addValue("id", entity.getId());
        jdbc.update(
            "UPDATE book SET isbn = :isbn, title = :title, edition_number = :edition_number, copyright = :copyright"
            + ", publisher_id = :publisher_id, genre_id = :genre_id"
            + " WHERE book_id = :id",
            namedParameters
        );
    }

    @Override
    public void delete(long id)
    {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("id", id);
        jdbc.update("DELETE FROM book WHERE book_id = :id", namedParameters);
    }
}
