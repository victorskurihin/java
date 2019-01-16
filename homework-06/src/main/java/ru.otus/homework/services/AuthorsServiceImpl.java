package ru.otus.homework.services;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import ru.otus.homework.models.Author;
import ru.otus.homework.repository.AuthorRepositoryJpa;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthorsServiceImpl implements AuthorsService
{
    private AuthorRepositoryJpa authorRepository;

    public AuthorsServiceImpl(AuthorRepositoryJpa authorRepository)
    {
        this.authorRepository = authorRepository;
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
        head.add(AuthorRepositoryJpa.FIND_ALL_HEADER);

        List<String[]> tail = authorRepository.findAll().stream().map(this::unfold).collect(Collectors.toList());
        head.addAll(tail);

        return head;
    }

    @Override
    public List<String[]> findById(long id)
    {
        List<String[]> head = new ArrayList<>();
        head.add(AuthorRepositoryJpa.FIND_ALL_HEADER);

        try {
            Author author;
            author = authorRepository.findById(id);
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
        head.add(AuthorRepositoryJpa.FIND_ALL_HEADER);

        List<String[]> tail = authorRepository.findByFirstName(firstName)
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
        head.add(AuthorRepositoryJpa.FIND_ALL_HEADER);

        List<String[]> tail = authorRepository.findByLastName(lastName)
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
        authorRepository.save(author);

        return author.getId();
    }

    @Override
    public long update(long id, String firstName, String lastName)
    {
        Author author = authorRepository.findById(id);
        author.setFirstName(firstName);
        author.setLastName(lastName);
        authorRepository.save(author);

        return author.getId();
    }

    @Override
    public long updateFirstName(long id, String firstName)
    {
        Author author = authorRepository.findById(id);
        author.setFirstName(firstName);
        authorRepository.save(author);

        return author.getId();
    }

    @Override
    public long updateLastName(long id, String lastName)
    {
        Author author = authorRepository.findById(id);
        author.setLastName(lastName);
        authorRepository.save(author);

        return author.getId();
    }

    @Override
    public void delete(long id)
    {
        authorRepository.delete(id);
    }
}
