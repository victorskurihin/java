package ru.otus.homework.services.dao;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.homework.models.Book;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

@Repository("bookDao")
public class JdbcBookDao implements BookDao
{
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
            b.setIsbn(resultSet.getString("isbn"));
            b.setTitle(resultSet.getString("title"));
            b.setEditionNumber(resultSet.getInt("edition_number"));
            b.setCopyright(resultSet.getString("copyright"));
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
            + " WHERE b.title = :title",
            new MapSqlParameterSource("title", title),
            (rs, rowNum) -> buildBy(rs)
        );
    }

    @Override
    public List findAllBooksAndTheirAuthors()
    {
        // TODO
        // Map<Book, Author> map
        List<Book> result = jdbc.query(
            "SELECT b.book_id, b.isbn, b.title, b.edition_number, b.copyright, b.publisher_id"
            + ", p.publisher_name, b.genre_id, g.genre"
            + " FROM book b"
            + " LEFT JOIN publisher p ON b.publisher_id = p.publisher_id"
            + " LEFT JOIN genre g ON b.genre_id = g.genre_id",
            (rs, rowNum) -> buildBy(rs)
        );

        return null;
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
