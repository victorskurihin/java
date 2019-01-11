package ru.otus.homework.services.dao;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.homework.models.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository("genreDao")
public class JdbcGenreDao implements GenreDao
{
    private NamedParameterJdbcTemplate jdbc;

    public JdbcGenreDao(NamedParameterJdbcTemplate jdbc)
    {
        this.jdbc = jdbc;
    }

    static Genre buildBy(ResultSet resultSet)
    {
        Genre g = new Genre();
        try {
            g.setId(resultSet.getLong("genre_id"));
            g.setGenre(resultSet.getString("genre"));

            return g;
        }
        catch (SQLException e) {
            return null;
        }
    }

    @Override
    public List<Genre> findByGenre(String genre)
    {
        return jdbc.query(
            "SELECT genre_id, genre FROM genre WHERE genre = :genre",
            new MapSqlParameterSource("genre", genre),
            (rs, rowNum) -> buildBy(rs)
        );
    }

    @Override
    public List<Genre> findAll()
    {
        return jdbc.query("SELECT genre_id, genre FROM genre", (rs, rowNum) -> buildBy(rs));
    }

    @Override
    public Genre findById(long id)
    {
        return jdbc.queryForObject(
            "SELECT genre_id, genre FROM genre WHERE genre_id = :id",
            new MapSqlParameterSource("id", id),
            (rs, numRow) -> buildBy(rs)
        );
    }

    @Override
    public void insert(Genre entity)
    {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("genre", entity.getGenre());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update("INSERT INTO genre (genre) VALUES (:genre)", namedParameters, keyHolder);
        entity.setId(Objects.requireNonNull(keyHolder.getKey()).longValue());
    }

    @Override
    public void update(Genre entity)
    {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("genre", entity.getGenre());
        namedParameters.addValue("genre_id", entity.getId());
        jdbc.update("UPDATE genre SET genre = :genre WHERE genre_id = :genre_id", namedParameters);
    }

    @Override
    public void delete(long id)
    {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("genre_id", id);
        jdbc.update("DELETE FROM genre WHERE genre_id = :genre_id", namedParameters);
    }
}
