package ru.otus.homework.services;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import ru.otus.homework.models.Genre;
import ru.otus.homework.services.dao.JdbcGenreDao;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GenreServiceImpl implements GenreService
{
    public static String[] FIND_ALL_HEADER = {"genre_id", "genre"};

    private JdbcGenreDao genreDao;

    public GenreServiceImpl(JdbcGenreDao genreDao)
    {
        this.genreDao = genreDao;
    }

    static String[] unfoldGenre(Genre a)
    {
        if (null == a) {
            return new String[]{"NULL", "NULL"};
        }

        return new String[]{Long.toString(a.getId()), a.getGenre()};
    }

    private String[] unfold(Genre a)
    {
        return unfoldGenre(a);
    }

    @Override
    public List<String[]> findAll()
    {
        List<String[]> head = new ArrayList<>();
        head.add(FIND_ALL_HEADER);

        List<String[]> tail = genreDao.findAll().stream().map(this::unfold).collect(Collectors.toList());
        head.addAll(tail);

        return head;
    }

    @Override
    public List<String[]> findById(long id)
    {
        List<String[]> head = new ArrayList<>();
        head.add(FIND_ALL_HEADER);

        try {
            Genre genre;
            genre = genreDao.findById(id);
            head.add(unfold(genre));

            return head;
        }
        catch (EmptyResultDataAccessException e) {
            return head;

        }
    }

    @Override
    public List<String[]> findByGenre(String genre)
    {
        List<String[]> head = new ArrayList<>();
        head.add(FIND_ALL_HEADER);

        List<String[]> tail = genreDao.findByGenre(genre)
            .stream()
            .map(this::unfold)
            .collect(Collectors.toList());
        head.addAll(tail);

        return head;
    }

    @Override
    public long insert(String genre)
    {
        Genre g = new Genre();
        g.setGenre(genre);
        genreDao.insert(g);

        return g.getId();
    }

    @Override
    public long update(long id, String genre)
    {
        Genre g = new Genre();
        g.setId(id);
        g.setGenre(genre);
        genreDao.update(g);

        return g.getId();
    }

    @Override
    public void delete(long id)
    {
        genreDao.delete(id);
    }
}
