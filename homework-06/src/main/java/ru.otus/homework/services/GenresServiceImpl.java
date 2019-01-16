package ru.otus.homework.services;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import ru.otus.homework.models.Genre;
import ru.otus.homework.repository.GenreRepositoryJpa;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GenresServiceImpl implements GenresService
{
    private GenreRepositoryJpa genreRepository;

    public GenresServiceImpl(GenreRepositoryJpa genreRepository)
    {
        this.genreRepository = genreRepository;
    }

    static String[] unfoldGenre(Genre g)
    {
        if (null == g) {
            return new String[]{"NULL", "NULL"};
        }

        return new String[]{Long.toString(g.getId()), g.getGenre()};
    }

    private String[] unfold(Genre g)
    {
        return unfoldGenre(g);
    }

    @Override
    public List<String[]> findAll()
    {
        List<String[]> head = new ArrayList<>();
        head.add(GenreRepositoryJpa.FIND_ALL_HEADER);

        List<String[]> tail = genreRepository.findAll().stream().map(this::unfold).collect(Collectors.toList());
        head.addAll(tail);

        return head;
    }

    @Override
    public List<String[]> findById(long id)
    {
        List<String[]> head = new ArrayList<>();
        head.add(GenreRepositoryJpa.FIND_ALL_HEADER);

        try {
            Genre genre;
            genre = genreRepository.findById(id);
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
        head.add(GenreRepositoryJpa.FIND_ALL_HEADER);

        List<String[]> tail = genreRepository.findByGenre(genre)
            .stream()
            .map(this::unfold)
            .collect(Collectors.toList());
        head.addAll(tail);

        return head;
    }

    @Override
    public long insert(String value)
    {
        Genre genre = new Genre();
        genre.setGenre(value);
        genreRepository.save(genre);

        return genre.getId();
    }

    @Override
    public long update(long id, String value)
    {
        Genre genre = genreRepository.findById(id);
        genre.setGenre(value);
        genreRepository.save(genre);

        return genre.getId();
    }

    @Override
    public void delete(long id)
    {
        genreRepository.delete(id);
    }
}
