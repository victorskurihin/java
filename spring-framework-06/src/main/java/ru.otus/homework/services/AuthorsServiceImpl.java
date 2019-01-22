package ru.otus.homework.services;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import ru.otus.homework.models.Author;
import ru.otus.homework.services.dao.JdbcAuthorDao;

import java.util.List;

@Service
public class AuthorsServiceImpl implements AuthorsService
{
    public static String[] FIND_ALL_HEADER = {"author_id", "first_name", "last_name"};

    private JdbcAuthorDao authorDao;

    public AuthorsServiceImpl(JdbcAuthorDao authorDao)
    {
        this.authorDao = authorDao;
    }

    static String[] unfoldAuthor(Author a)
    {
        if (null == a) {
            return new String[]{"NULL", "NULL", "NULL"};
        }

        return new String[]{Long.toString(a.getId()), a.getFirstName(), a.getLastName()};
    }

    public String[] unfold(Author a)
    {
        return unfoldAuthor(a);
    }

    @Override
    public List<Author> findAll()
    {
        return authorDao.findAll();
    }

    @Override
    public String[] getHeader()
    {
        return FIND_ALL_HEADER;
    }

    @Override
    public Author findById(long id)
    {
        try {
            return authorDao.findById(id);
        }
        catch (EmptyResultDataAccessException e) {
            // TODO LOG
            return null;
        }
    }

    @Override
    public List<Author> findByFirstName(String firstName)
    {
        List<Author> authors = authorDao.findByFirstName(firstName);
        // TODO LOG

        return authors;
    }

    @Override
    public List<Author> findByLastName(String lastName)
    {
        List<Author> authors = authorDao.findByLastName(lastName);
        // TODO LOG

        return authors;
    }

    @Override
    public long insert(String firstName, String lastName)
    {
        Author author = new Author();
        author.setFirstName(firstName);
        author.setLastName(lastName);

        authorDao.insert(author);

        return author.getId();
    }

    @Override
    public long update(long id, String firstName, String lastName)
    {
        Author author = new Author();
        author.setId(id);
        author.setFirstName(firstName);
        author.setLastName(lastName);

        authorDao.update(author);

        return author.getId();
    }

    @Override
    public void delete(long id)
    {
        authorDao.delete(id);
    }
}
