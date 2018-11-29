/*
 * AbstractController.java
 * This file was last modified at 30.11.18 0:23 by Victor N. Skurikhin.
 * $Id$
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */

/*
 * AbstractController.java
 * This file was last modified at 30.11.18 0:22 by Victor N. Skurikhin.
 * $Id$
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */

package ru.otus.db.dao.jpa;

import ru.otus.db.dao.DAOController;
import ru.otus.exeptions.ExceptionThrowable;
import ru.otus.models.DataSet;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.RollbackException;
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

    protected EntityManager getEntityManager()
    {
        return em;
    }

    protected List<E> getAll(Class<E> clazz) throws ExceptionThrowable
    {
        List<E> result = null;

        try {
            CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
            CriteriaQuery<E> criteriaQuery = criteriaBuilder.createQuery(clazz);
            Root<E> criteria = criteriaQuery.from(clazz);
            criteriaQuery.select(criteria);
            result = em.createQuery(criteriaQuery).getResultList();

            return result;
        }
        catch (Throwable e) {
            throw new ExceptionThrowable(e);
        }
    }

    protected E getEntityViaClassById(K id, Class<E> clazz) throws ExceptionThrowable
    {
        try {
            CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
            CriteriaQuery<E> query = criteriaBuilder.createQuery(clazz);
            Root<E> criteria = query.from(clazz);
            query = query.select(criteria)
                    .where(criteriaBuilder.equal(criteria.get("id"), id));
            E result = em.createQuery(query).getSingleResult();

            return result;
        }
        catch (NoResultException e) {
            return null;
        }
        catch (Exception e) {
            throw new ExceptionThrowable(e);
        }
    }

    protected E getEntityViaClassByName(String fieldName, String valueName, Class<E> clazz)
    throws ExceptionThrowable
    {
        try {
            CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
            CriteriaQuery<E> query = criteriaBuilder.createQuery(clazz);
            Root<E> criteria = query.from(clazz);
            query = query.select(criteria)
                    .where(criteriaBuilder.equal(criteria.get(fieldName), valueName));

            return em.createQuery(query).getSingleResult();
        }
        catch (NoResultException e) {
            return null;
        }
        catch (Exception e) {
            throw new ExceptionThrowable(e);
        }
    }

    protected E mergeEntity(E entity) throws ExceptionThrowable
    {
        EntityTransaction transaction = em.getTransaction();

        if ( ! transaction.isActive()) {
            transaction.begin();
        }
        try {
            em.merge(entity);
            em.flush();
            transaction.commit();

            return entity;
        }
        catch (RollbackException te) {
            throw new ExceptionThrowable(te);
        }
        catch (Exception e) {
            transaction.rollback();
            throw new ExceptionThrowable(e);
        }
    }

    protected boolean deleteEntityViaClassById(K id, Class<E> c) throws ExceptionThrowable
    {
        EntityTransaction transaction = em.getTransaction();

        if ( ! transaction.isActive()) {
            transaction.begin();
        }
        try {
            CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
            CriteriaDelete<E> delete = criteriaBuilder.createCriteriaDelete(c);
            Root<E> criteria = delete.from(c);
            delete.where(criteriaBuilder.equal(criteria.get("id"), id));
            int count = em.createQuery(delete).executeUpdate();
            em.getTransaction().commit();

            return count > 0;
        }
        catch (RollbackException te) {
                throw new ExceptionThrowable(te);
        }
        catch (Exception e) {
            transaction.rollback();
            throw new ExceptionThrowable(e);
        }
    }

    protected boolean persistEntity(E entity) throws ExceptionThrowable
    {
        EntityTransaction transaction = em.getTransaction();

        if ( ! transaction.isActive()) {
            transaction.begin();
        }
        try {
            em.persist(entity);
            em.getTransaction().commit();

            return true;
        }
        catch (RollbackException te) {
            throw new ExceptionThrowable(te);
        }
        catch (Exception e) {
            transaction.rollback();
            throw new ExceptionThrowable(e);
        }
    }

    protected long getMaxLong(String fieldName, Class<E> clazz) throws ExceptionThrowable
    {
        try {
            CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
            CriteriaQuery<Long> query = criteriaBuilder.createQuery(Long.class);
            Root<E> criteria = query.from(clazz);

            query.select(criteriaBuilder.max(criteria.get(fieldName)));

            long maxValue = 0L;

            try {
                maxValue = em.createQuery(query).getSingleResult();
            }
            catch (NoResultException nre) {
                maxValue = -1L;
            }

            return maxValue;
        }
        catch (Exception e) {
            throw new ExceptionThrowable(e);
        }
    }

    protected double getAvgDouble(String fieldName, Class<E> clazz) throws ExceptionThrowable
    {
        try {
            CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
            CriteriaQuery<Double> query = criteriaBuilder.createQuery(Double.class);
            Root<E> criteria = query.from(clazz);

            query.select(criteriaBuilder.avg(criteria.get(fieldName)));

            double avgValue = 0;

            try {
                avgValue = em.createQuery(query).getSingleResult();
            }
            catch (NoResultException nre) {
                avgValue = -1;
            }

            return avgValue;
        }
        catch (Exception e) {
            throw new ExceptionThrowable(e);
        }
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
