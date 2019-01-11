package ru.otus.homework.services.dao;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.homework.models.Author;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

@Repository("authorDao")
public class JdbcAuthorDao implements AuthorDao
{
    public static String[] FIND_ALL_HEADER = {"author_id", "first_name", "last_name"};

    private NamedParameterJdbcTemplate jdbc;

    public JdbcAuthorDao(NamedParameterJdbcTemplate jdbc)
    {
        this.jdbc = jdbc;
    }

    static Author buildBy(ResultSet resultSet)
    {
        Author a = new Author();
        try {
            a.setId(resultSet.getLong("author_id"));
            a.setFirstName(resultSet.getString("first_name"));
            a.setLastName(resultSet.getString("last_name"));

            return a;
        }
        catch (SQLException e) {
            return null;
        }
    }

    @Override
    public List<Author> findAll()
    {
        return jdbc.query("SELECT author_id, first_name, last_name FROM author", (rs, rowNum) -> buildBy(rs));
    }

    @Override
    public Author findById(long id)
    {
        return jdbc.queryForObject(
            "SELECT author_id, first_name, last_name FROM author WHERE author_id = :id",
            new MapSqlParameterSource("id", id),
            (rs, numRow) -> buildBy(rs)
        );
    }

    @Override
    public List<Author> findByFirstName(String firstName)
    {
        return jdbc.query(
            "SELECT author_id, first_name, last_name FROM author WHERE first_name LIKE :name",
            new MapSqlParameterSource("name", firstName),
            (rs, rowNum) -> buildBy(rs)
        );
    }

    @Override
    public List<Author> findByLastName(String lastName)
    {
        return jdbc.query(
            "SELECT author_id, first_name, last_name FROM author WHERE last_name LIKE :name",
            new MapSqlParameterSource("name", lastName),
            (rs, rowNum) -> buildBy(rs)
        );
    }

    @Override
    public void insert(Author entity)
    {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("first_name", entity.getFirstName());
        namedParameters.addValue("last_name", entity.getLastName());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update(
            "INSERT INTO author (first_name, last_name) VALUES (:first_name, :last_name)",
            namedParameters, keyHolder
        );
        entity.setId(Objects.requireNonNull(keyHolder.getKey()).longValue());
    }

    @Override
    public void update(Author entity)
    {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("first_name", entity.getFirstName());
        namedParameters.addValue("last_name", entity.getLastName());
        namedParameters.addValue("id", entity.getId());
        jdbc.update(
            "UPDATE author SET first_name = :first_name, last_name = :last_name WHERE author_id = :id",
            namedParameters
        );
    }

    @Override
    public void delete(long id)
    {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("id", id);
        jdbc.update("DELETE FROM author WHERE author_id = :id", namedParameters);
    }
}
