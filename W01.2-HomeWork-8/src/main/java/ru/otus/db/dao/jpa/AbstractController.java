package ru.otus.db.dao.jpa;

/*
 * Created by VSkurikhin at winter 2018.
 */

import ru.otus.db.dao.DAOController;
import ru.otus.exeptions.ExceptionThrowable;
import ru.otus.models.DataSet;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public abstract class AbstractController<E extends DataSet, K> implements DAOController<E, K>
{
    private EntityManager em;

    public AbstractController() { /* None */ }

    public AbstractController(EntityManager entityManager)
    {
        this.em = entityManager;
    }

    public void setEntityManager(EntityManager entityManager)
    {
        this.em = entityManager;
    }

    protected List<E> getAll(Class<E> clazz) throws ExceptionThrowable
    {
        EntityTransaction transaction = em.getTransaction();
        List<E> result = null;

        try {
            transaction.begin();
            CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
            CriteriaQuery<E> criteriaQuery = criteriaBuilder.createQuery(clazz);
            Root<E> criteria = criteriaQuery.from(clazz);
            criteriaQuery.select(criteria);
            result = em.createQuery(criteriaQuery).getResultList();
            transaction.commit();

            return result;
        }
        catch (Throwable e) {
            transaction.rollback();
            throw new ExceptionThrowable(e);
        }

    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
