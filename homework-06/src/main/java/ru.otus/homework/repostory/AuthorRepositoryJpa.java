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
        //noinspection unchecked
        return em.createQuery(
            "SELECT a FROM Author a WHERE a.firstName LIKE :name")
            .setParameter("name", firstName)
            .getResultList();
    }

    @Override
    public List<Author> findByLastName(String lastName)
    {
        //noinspection unchecked
        return em.createQuery(
            "SELECT a FROM Author a WHERE a.lastName LIKE :name")
            .setParameter("name", lastName)
            .getResultList();
    }

    @Override
    public List<Author> findAll()
    {
        //noinspection unchecked
        return em.createQuery("SELECT a FROM Author a").getResultList();
    }

    @Override
    public Author findById(long id)
    {
        return em.find(Author.class, id);
    }

    @Override
    public void insert(Author entity)
    {
        Author author = new Author();
        author.setId(entity.getId());
        author.setFirstName(entity.getFirstName());
        author.setLastName(entity.getLastName());
        em.persist(author);
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
