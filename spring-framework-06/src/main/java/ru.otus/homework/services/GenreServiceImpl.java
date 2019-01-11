package ru.otus.homework.services;

import org.springframework.stereotype.Service;
import ru.otus.homework.models.Author;
import ru.otus.homework.models.Book;
import ru.otus.homework.models.Genre;
import ru.otus.homework.services.dao.JdbcGenreDao;

@Service
public class GenreServiceImpl extends TableBuilderService<Genre, JdbcGenreDao> implements GenreService
{
    private JdbcGenreDao publisherDao;

    public GenreServiceImpl(JdbcGenreDao dao)
    {
        this.publisherDao = dao;
    }

    private static String[] unfoldGenre(Genre genre)
    {
        return new String[]{Long.toString(genre.getId()), genre.getGenre()};
    }

    private static String[] unfoldWithDetail(Genre genre, Book book, Author author)
    {
        return new String[]{
            author.getFirstName(),
            author.getLastName(),
            Long.toString(book.getId()),
            book.getIsbn(),
            book.getTitle(),
            Integer.toString(book.getEditionNumber()),
            book.getCopyright(),
            genre.getGenre(),
        };
    }

    @Override
    public long insert(String publisherName)
    {
        Genre publisher = new Genre();
        publisher.setGenre(publisherName);

        publisherDao.insert(publisher);

        return publisher.getId();
    }

    @Override
    public long update(long id, String publisherName)
    {
        Genre publisher = new Genre();
        publisher.setId(id);
        publisher.setGenre(publisherName);

        publisherDao.update(publisher);

        return publisher.getId();
    }

    @Override
    public void delete(long id)
    {
        publisherDao.delete(id);
    }
}
