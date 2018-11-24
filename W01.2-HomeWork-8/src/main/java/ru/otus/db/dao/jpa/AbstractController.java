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
import javax.persistence.criteria.CriteriaDelete;
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

    protected E getEntityViaClassById(K id, Class<E> clazz) throws ExceptionThrowable
    {
        try {
            em.getTransaction().begin();
            CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
            CriteriaQuery<E> query = criteriaBuilder.createQuery(clazz);
            Root<E> criteria = query.from(clazz);
            query = query.select(criteria)
                    .where(criteriaBuilder.equal(criteria.get("id"), id));
            E result = em.createQuery(query).getSingleResult();
            em.getTransaction().commit();

            return result;
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new ExceptionThrowable(e);
        }
    }

    protected E getEntityViaClassByName(String fieldName, String valueName, Class<E> clazz)
    throws ExceptionThrowable
    {
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();
            CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
            CriteriaQuery<E> query = criteriaBuilder.createQuery(clazz);
            Root<E> criteria = query.from(clazz);
            query = query.select(criteria)
                    .where(criteriaBuilder.equal(criteria.get(fieldName), valueName));
            E result = em.createQuery(query).getSingleResult();
            transaction.commit();

            return result;
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new ExceptionThrowable(e);
        }
    }

    protected E mergeEntity(E entity) throws ExceptionThrowable
    {
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();
            em.merge(entity);
            transaction.commit();

            return entity;
        }
        catch (Exception e) {
            transaction.rollback();
            throw new ExceptionThrowable(e);
        }
    }

    protected boolean deleteEntityViaClassById(K id, Class<E> c) throws ExceptionThrowable
    {
        try {
            em.getTransaction().begin();
            CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
            CriteriaDelete<E> delete = criteriaBuilder.createCriteriaDelete(c);
            Root<E> criteria = delete.from(c);
            delete.where(criteriaBuilder.equal(criteria.get("id"), id));
            int count = em.createQuery(delete).executeUpdate();
            em.getTransaction().commit();

            return count > 0;
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new ExceptionThrowable(e);
        }
    }

    protected boolean persistEntity(E entity) throws ExceptionThrowable
    {
        try {
            em.getTransaction().begin();
            em.persist(entity);
            em.getTransaction().commit();

            return true;
        }
        catch (Exception e) {
            em.getTransaction().rollback();
            throw new ExceptionThrowable(e);
        }
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
