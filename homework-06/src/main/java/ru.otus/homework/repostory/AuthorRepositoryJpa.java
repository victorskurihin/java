package ru.otus.homework.repostory;

import org.springframework.stereotype.Repository;
import ru.otus.homework.models.Author;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class AuthorRepositoryJpa implements AuthorDao
{
    @PersistenceContext
    private EntityManager em;

    public AuthorRepositoryJpa() {}

    public AuthorRepositoryJpa(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<Author> findByFirstName(String firstName)
    {
        return null;
    }

    @Override
    public List<Author> findByLastName(String lastName)
    {
        return null;
    }

    @Override
    public List<Author> findAll()
    {
        return null;
    }

    @Override
    public Author findById(long id)
    {
        return em.find(Author.class, id);
    }

    @Override
    public void insert(Author entity)
    {

    }

    @Override
    public void update(Author entity)
    {

    }

    @Override
    public void delete(long id)
    {

    }
}
