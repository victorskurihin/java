package ru.otus.homework.repository;

import org.springframework.stereotype.Repository;
import ru.otus.homework.models.Author;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import javax.persistence.RollbackException;
import java.util.List;

@Repository
public class AuthorRepositoryJpa implements AuthorDao
{
    public static String[] FIND_ALL_HEADER = {"author_id", "first_name", "last_name"};

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
        EntityTransaction transaction = em.getTransaction();

        if (!transaction.isActive()) {
            transaction.begin();
        }
        try {
            if (entity.getId() == 0) {
                em.persist(entity);
                em.flush();
            }
            else {
                em.merge(entity);
            }
            transaction.commit();
        }
        catch (RollbackException e) {
            // TOTO LOG
            throw new RuntimeException(e);
        }
        catch (RuntimeException e) {
            // TOTO LOG
            transaction.rollback();
        }
   }

    @Override
    public void update(Author entity)
    {
        em.getTransaction().begin();
        try {
            em.merge(entity);
            em.getTransaction().commit();
        }
        catch (RuntimeException e) {
            // TOTO LOG
            em.getTransaction().rollback();
        }
    }

    @Override
    public void delete(long id)
    {
        try {
            em.getTransaction().begin();
            Author mergedAuthor = em.merge(findById(id));
            em.remove(mergedAuthor);
            em.getTransaction().commit();
        }
        catch (RuntimeException e) {
            // TOTO LOG
            em.getTransaction().rollback();
        }
    }
}
