/*
 * AbstractController.java
 * This file was last modified at 2018.12.03 20:05 by Victor N. Skurikhin.
 * $Id$
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */

package ru.otus.db.dao.jpa;

import ru.otus.db.dao.JPAController;
import ru.otus.exceptions.ExceptionThrowable;
import ru.otus.models.DataSet;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.function.Consumer;

public abstract class AbstractController<E extends DataSet, K> implements JPAController<E, K>
{
    private Class<E> classE;

    protected abstract EntityManager getEntityManager();

    protected abstract Class<E> getTypeFirstParameterClass();

    protected List<E> getAll(Class<E> clazz) throws ExceptionThrowable
    {
        List<E> result = null;
        EntityManager em = getEntityManager();

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
        EntityManager em = getEntityManager();

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
        EntityManager em = getEntityManager();

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
        EntityManager em = getEntityManager();

        try {
            return em.merge(entity);
        }
        catch (RollbackException e) {
            throw new ExceptionThrowable(e);
        }
        catch (Exception e) {
            throw new ExceptionThrowable(e);
        }
    }

    protected boolean deleteEntityViaClassById(K id, Class<E> c) throws ExceptionThrowable
    {
        EntityManager em = getEntityManager();

        try {
            CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
            CriteriaDelete<E> delete = criteriaBuilder.createCriteriaDelete(c);
            Root<E> criteria = delete.from(c);
            delete.where(criteriaBuilder.equal(criteria.get("id"), id));
            int count = em.createQuery(delete).executeUpdate();

            return count > 0;
        }
        catch (RollbackException te) {
            throw new ExceptionThrowable(te);
        }
        catch (Exception e) {
            throw new ExceptionThrowable(e);
        }
    }

    protected E updateEntity(K key, Consumer<E> consumer) throws ExceptionThrowable
    {
        if (null == classE) {
            classE = getTypeFirstParameterClass();
        }

        EntityManager em = getEntityManager();

        try {
            E entity = em.find(classE, key);
            consumer.accept(entity);

            return em.merge(entity);
        }
        catch (RollbackException e) {
            throw new ExceptionThrowable(e);
        }
        catch (Exception e) {
            throw new ExceptionThrowable(e);
        }
    }

    protected <R> R callStoredProcedureSingleResult(String name, Consumer<StoredProcedureQuery> c)
    throws ExceptionThrowable
    {
        EntityManager em = getEntityManager();

        try {
            StoredProcedureQuery proc = em.createNamedStoredProcedureQuery(name);
            c.accept(proc);
            //noinspection unchecked
            R result = (R) proc.getSingleResult();

            return result;
        }
        catch (RollbackException e) {
            throw new ExceptionThrowable(e);
        }
        catch (Exception e) {
            throw new ExceptionThrowable(e);
        }
    }

    protected boolean persistEntity(E entity) throws ExceptionThrowable
    {
        EntityManager em = getEntityManager();

        try {
            em.persist(entity);

            return true;
        }
        catch (RollbackException te) {
            throw new ExceptionThrowable(te);
        }
        catch (Exception e) {
            throw new ExceptionThrowable(e);
        }
    }

    protected long getMaxLong(String fieldName, Class<E> clazz) throws ExceptionThrowable
    {
        EntityManager em = getEntityManager();

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
        EntityManager em = getEntityManager();

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

    @Override
    public E findById(EntityManager entityManager, long key) throws ExceptionThrowable
    {
        if (null == classE) {
            classE = getTypeFirstParameterClass();
        }

        return entityManager.find(classE, key);
    }

    @Override
    public E findById(long key) throws ExceptionThrowable
    {
        EntityManager em = getEntityManager();

        try {
            return findById(em, key);
        }
        catch (NoResultException e) {
            return null;
        }
        catch (Exception e) {
            throw new ExceptionThrowable(e);
        }
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
