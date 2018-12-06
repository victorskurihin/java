/*
 * AbstractController.java
 * This file was last modified at 2018.12.03 20:05 by Victor N. Skurikhin.
 * $Id$
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */

package ru.otus.db.dao.jpa;

import ru.otus.db.dao.JPAController;
import ru.otus.exeptions.ExceptionThrowable;
import ru.otus.models.DataSet;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

public abstract class AbstractController<E extends DataSet, K> implements JPAController<E, K>
{
    private EntityManagerFactory emf;

    private Class<E> classE;

    private Class<E> getTypeFirstParameterClass()
    {
        Type[] types = getClass().getGenericInterfaces();
        for (Type type : types) {
            if (type.getTypeName().startsWith("ru.otus.adapter.DataSetAdapter")) {
                ParameterizedType paramType = (ParameterizedType) type;
                // some magic with reflection
                //noinspection unchecked
                return (Class<E>) paramType.getActualTypeArguments()[0];
            }
        }
        return null;
    }

    public AbstractController()
    {
        classE = getTypeFirstParameterClass();
    }

    public AbstractController(EntityManagerFactory entityManagerFactory)
    {
        this();
        this.emf = entityManagerFactory;
    }

    public void setEntityManager(EntityManagerFactory entityManagerFactory)
    {
        this.emf = entityManagerFactory;
    }

    protected EntityManager getEntityManager()
    {
        return emf.createEntityManager();
    }

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
        finally {
            em.close();
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
        finally {
            em.close();
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
        finally {
            em.close();
        }
    }

    protected E mergeEntity(E entity) throws ExceptionThrowable
    {
        EntityManager em = getEntityManager();
        EntityTransaction transaction = em.getTransaction();

        if (!transaction.isActive()) {
            transaction.begin();
        }
        try {
            em.merge(entity);
            transaction.commit();

            return entity;
        }
        catch (RollbackException e) {
            throw new ExceptionThrowable(e);
        }
        catch (Exception e) {
            transaction.rollback();
            throw new ExceptionThrowable(e);
        }
        finally {
            em.close();
        }
    }

    protected boolean deleteEntityViaClassById(K id, Class<E> c) throws ExceptionThrowable
    {
        EntityManager em = getEntityManager();
        EntityTransaction transaction = em.getTransaction();

        if (!transaction.isActive()) {
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
        finally {
            em.close();
        }
    }

    protected boolean persistEntity(E entity) throws ExceptionThrowable
    {
        EntityManager em = getEntityManager();
        EntityTransaction transaction = em.getTransaction();

        if (!transaction.isActive()) {
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
        finally {
            em.close();
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
        finally {
            em.close();
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
        finally {
            em.close();
        }
    }

    @Override
    public E findById(EntityManager entityManager, long key) throws ExceptionThrowable
    {
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
        finally {
            em.close();
        }
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
