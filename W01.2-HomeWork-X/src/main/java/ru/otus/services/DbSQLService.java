/*
 * DbSQLService.java
 * This file was last modified at 2018.12.03 20:05 by Victor N. Skurikhin.
 * $Id$
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */

package ru.otus.services;

import ru.otus.db.DBConf;
import ru.otus.db.PostgreSQLService;
import ru.otus.db.dao.ControllersOfClass;
import ru.otus.db.dao.jpa.*;
import ru.otus.exeptions.ExceptionThrowable;
import ru.otus.models.*;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.servlet.ServletContext;
import java.sql.SQLException;
import java.util.*;
import java.util.function.Consumer;

public class DbSQLService extends PostgreSQLService implements DBConf, DbService
{
    private static final String SELECT_EMP_ENTITY = "SELECT e FROM EmpEntity e";
    private static final String SELECT_USER_ENTITY_BY_NAME = "SELECT e FROM UserEntity e WHERE e.login = :name";

    private static final String FIO_PREDICATE =
        "(e.firstName LIKE :name OR e.secondName LIKE :name OR e.surName LIKE :name)";
    private static final String JOB_PREDICATE = "e.job LIKE :job";
    private static final String CITY_PREDICATE = "e.city LIKE :city";
    private static final String AGE_PREDICATE = "e.age = :age";

    public DbSQLService()
    {
        super();
    }

    public DbSQLService(EntityManagerFactory entityManagerFactory)
    {
        super(entityManagerFactory);
    }

    @Override
    public void clearDb(ServletContext sc) throws ExceptionThrowable
    {
        super.clearDb(sc);
    }

    @Override
    public void importDb(ServletContext sc) throws ExceptionThrowable
    {
        super.importDb(sc);
    }

    @Override
    public void exportDb(ServletContext sc)
    {
        super.exportDb(sc);
    }

    public DeptController getDeptController()
    {
        return new DeptController(super.getEntityManagerFactory());
    }

    public UserController getUserController()
    {
        return new UserController(super.getEntityManagerFactory());
    }

    public GroupController getGroupController()
    {
        return new GroupController(super.getEntityManagerFactory());
    }

    public EmpController getEmpController()
    {
        return new EmpController(super.getEntityManagerFactory());
    }

    public StatisticController getStatisticController()
    {
        return new StatisticController(super.getEntityManagerFactory());
    }

    @SuppressWarnings("unchecked")
    @Override
    public <E extends DataSet> AbstractController<E, Long> getController(Class<E> c)
    {
        ControllersOfClass controller = ControllersOfClass.valueOf(c.getSimpleName());

        switch (controller) {
            case DeptEntity:
                return (AbstractController<E, Long>) getDeptController();
            case UserEntity:
                return (AbstractController<E, Long>) getUserController();
            case GroupEntity:
                return (AbstractController<E, Long>) getGroupController();
            case EmpEntity:
                return (AbstractController<E, Long>) getEmpController();
            case StatisticEntity:
                return (AbstractController<E, Long>) getStatisticController();
        }

        return null;
    }

    @Override
    public <T extends DataSet> void saveEntity(T entity)
    {
        mergeEntity(entity);
    }


    @Override
    public <T extends DataSet> T getEntityById(long id, Class<T> c) throws ExceptionThrowable
    {
        return getController(c).findById(id);
    }

    @Override
    public <T extends DataSet> List<T> getEntities(Class<T> c) throws ExceptionThrowable
    {
        return getController(c).getAll();
    }

    @Override
    public <T extends DataSet> boolean deleteEntityById(long id, Class<T> c) throws ExceptionThrowable
    {
        return getController(c).delete(id);
    }


    //// LEGACY ///

    @Override
    public List<EmpEntity> searchEmpEntity(Map<String, Object> attrs)
    {
        String sql = SELECT_EMP_ENTITY;
        String sqlTerm = " WHERE ";
        String sqlTermAnd = " AND ";

        if (isCorrectAttr("name", attrs)) {
            sql += sqlTerm;
            sql += FIO_PREDICATE;
            sqlTerm = sqlTermAnd;
        }
        if (isCorrectAttr("job", attrs)) {
            sql += sqlTerm;
            sql += JOB_PREDICATE;
            sqlTerm = sqlTermAnd;
        }
        if (isCorrectAttr("city", attrs)) {
            sql += sqlTerm;
            sql += CITY_PREDICATE;
            sqlTerm = sqlTermAnd;
        }
        if (isCorrectAttr("age", attrs)) {
            sql += sqlTerm;
            sql += AGE_PREDICATE;
        }

        return getEntities(sql, q -> attrs.forEach(q::setParameter));
    }

    private <T extends DataSet> List<T> getEntities(String sql, Consumer<Query> c)
    {
        EntityTransaction transaction = getEntityManager().getTransaction();
        try {
            transaction.begin();
            Query q = getEntityManager().createQuery(sql);
            if (null != c) c.accept(q);
            //noinspection unchecked
            List<T> result = new ArrayList<>(q.getResultList());
            transaction.commit();
            return result;
        }
        catch (Exception e) {
            transaction.rollback();
            throw e;
        }
    }

    private <T extends DataSet> List<T> getEntitiesViaClass(Class<T> c)
    {
        EntityTransaction transaction = getEntityManager().getTransaction();

        try {
            transaction.begin();
            CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
            CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(c);
            Root<T> criteria = criteriaQuery.from(c);
            criteriaQuery.select(criteria);
            List<T> result = getEntityManager().createQuery(criteriaQuery).getResultList();
            transaction.commit();

            return result;
        }
        catch (Exception e) {
            transaction.rollback();
            throw e;
        }
    }

    private <T extends DataSet> T getEntity(String query, Consumer<Query> c)
    {
        EntityTransaction transaction = getEntityManager().getTransaction();

        try {
            transaction.begin();
            Query q = getEntityManager().createQuery(query);
            if (null != c) c.accept(q);
            //noinspection unchecked
            T result = (T) q.getSingleResult();
            transaction.commit();

            return result;
        }
        catch (Exception e) {
            transaction.rollback();
            throw e;
        }
    }

    /*
    TODO remove
    private <T extends DataSet> T getEntityById(String sql, long id)
    {
        return getEntity(sql, query -> query.setParameter("id", id));
    }
    */

    private <T extends DataSet> T getEntityByName(String sql, String name)
    {
        return getEntity(sql, query -> query.setParameter("name", name));
    }

    /*
    TODO remove
    private <T extends DataSet> T getEntityViaClassById(long id, Class<T> c)
    {
        EntityTransaction transaction = getEntityManager().getTransaction();

        try {
            transaction.begin();
            CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
            CriteriaQuery<T> query = criteriaBuilder.createQuery(c);
            Root<T> criteria = query.from(c);
            query = query.select(criteria)
                .where(criteriaBuilder.equal(criteria.get("id"), id));
            T result = getEntityManager().createQuery(query).getSingleResult();
            transaction.commit();

            return result;
        }
        catch (Exception e) {
            transaction.rollback();
            throw e;
        }
    }
    */

    @Override
    public List<StatisticEntity> getAllStatisticElements() throws SQLException
    {
        return getEntitiesViaClass(StatisticEntity.class);
    }

    @SuppressWarnings("Duplicates")
    private <T extends DataSet> void mergeEntity(T entity)
    {
        EntityTransaction transaction = getEntityManager().getTransaction();

        try {
            transaction.begin();
            getEntityManager().merge(entity);
            transaction.commit();
        }
        catch (Exception e) {
            transaction.rollback();
            throw new RuntimeException(e);
        }
    }

    /*
    TODO remove
    private void queryUpdate(String query, Consumer<Query> c)
    {
        EntityTransaction transaction = getEntityManager().getTransaction();

        try {
            transaction.begin();
            Query q = getEntityManager().createQuery(query);
            if (null != c) c.accept(q);
            q.executeUpdate();
            transaction.commit();
        }
        catch (Exception e) {
            transaction.rollback();
            throw new RuntimeException(e); // TODO
        }
    }

    private <T extends DataSet> void deleteEntityViaClassById(long id, Class<T> c)
    {
        EntityTransaction transaction = getEntityManager().getTransaction();

        try {
            transaction.begin();
            CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
            CriteriaDelete<T> delete = criteriaBuilder.createCriteriaDelete(c);
            Root<T> criteria = delete.from(c);
            delete.where(criteriaBuilder.equal(criteria.get("id"), id));
            getEntityManager().createQuery(delete).executeUpdate();
            transaction.commit();
        }
        catch (Exception e) {
            transaction.rollback();
            throw e;
        }
    }
    */

    private boolean isCorrectAttr(String key, Map<String, Object> attrs)
    {
        return attrs.containsKey(key) && attrs.get(key) != null;
    }

    @Override
    public List<EmpEntity> getEmpEntities()
    {
        return getEntities(SELECT_EMP_ENTITY, null);
    }

    @Override
    public UserEntity getUserEntityByName(String name)
    {
        try {
            return getEntityByName(SELECT_USER_ENTITY_BY_NAME, name);
        }
        catch (NoResultException e) {
            return null;
        }
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
