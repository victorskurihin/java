package ru.otus.homework.services;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import ru.otus.homework.models.Author;
import ru.otus.homework.services.dao.JdbcAuthorDao;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthorsServiceImpl implements AuthorsService
{
    // TODO LOG

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

    private String[] unfold(Author a)
    {
        return unfoldAuthor(a);
    }

    @Override
    public List<String[]> findAll()
    {
        List<String[]> head = new ArrayList<>();
        head.add(JdbcAuthorDao.FIND_ALL_HEADER);

        List<String[]> tail = authorDao.findAll().stream().map(this::unfold).collect(Collectors.toList());
        head.addAll(tail);

        return head;
    }

    @Override
    public List<String[]> findById(long id)
    {
        List<String[]> head = new ArrayList<>();
        head.add(JdbcAuthorDao.FIND_ALL_HEADER);

        try {
            Author author;
            author = authorDao.findById(id);
            head.add(unfold(author));

            return head;
        }
        catch (EmptyResultDataAccessException e) {
            return head;

        }
    }

    @Override
    public List<String[]> findByFirstName(String firstName)
    {
        List<String[]> head = new ArrayList<>();
        head.add(JdbcAuthorDao.FIND_ALL_HEADER);

        List<String[]> tail = authorDao.findByFirstName(firstName)
            .stream()
            .map(this::unfold)
            .collect(Collectors.toList());
        head.addAll(tail);

        return head;
    }

    @Override
    public List<String[]> findByLastName(String lastName)
    {
        List<String[]> head = new ArrayList<>();
        head.add(JdbcAuthorDao.FIND_ALL_HEADER);

        List<String[]> tail = authorDao.findByLastName(lastName)
            .stream()
            .map(this::unfold)
            .collect(Collectors.toList());
        head.addAll(tail);

        return head;
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
