package ru.otus.l161.dao;

/*
 * Created by VSkurikhin at winter 2018.
 */

import org.hibernate.Session;
import org.hibernate.query.Query;
import ru.otus.l161.dataset.DataSet;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.List;

/**
 * This is contract for DAO classes with default the helper realization.
 *
 * A class is a superclass for implementing a Data Access Object interfaces
 * through Hibernate ORM.
 */
public abstract class DAO {
    @SuppressWarnings("WeakerAccess")
    protected Session session;

    DAO(Session session) {
        this.session = session;
    }

    /**
     * Returns the string description of type that can be serviced an adapter.
     *
     * @return the string description of type
     */
    public abstract String getAdapteeOfType();

    /**
     * Persists the given transient instance of subclass DataSet type, first
     * assigning a generated identifier.
     *
     * @param dataSet the transient instance of subclass DataSet type
     * @param <T> the subclass DataSet type
     */
    public abstract <T extends DataSet> void save(T dataSet);

    /**
     * Returns the persistent instance with given identifier of object, assuming
     * that the instance exists.
     *
     * @param id identifier
     * @param <T> the subclass DataSet type
     * @return the persistent instance of the T class with the given identifier
     */
    public abstract <T extends DataSet> T read(long id);

    /**
     * Returns the persistent instance with given the string value in object,
     * assuming that the instance exists.
     *
     * @param name the string value
     * @param <T> the subclass DataSet type
     * @return the persistent instance of the T class
     */
    public abstract <T extends DataSet> T readByName(String name);

    /**
     * Returns all persistent instances of the appropriate type of subclass
     * DataSet.
     *
     * @param <T> the subclass DataSet type
     * @return all persistent instances of the T class
     */
    public abstract <T extends DataSet> List<T> readAll();

    @Transactional
    <T extends DataSet> void saveDataSet(T dataSet) {
        session.save(dataSet);
    }

    protected <T extends DataSet> T read(Class<T> clazz, long id) {
        // return readByName(clazz, "id", id);
        return session.load(clazz, id); // ???
    }

    <T extends DataSet, S> T readByName(Class<T> clazz, String fldName, S name) {

        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<T> criteria = builder.createQuery(clazz);
        Root<T> from = criteria.from(clazz);
        criteria.where(builder.equal(from.get(fldName), name));
        Query<T> query = session.createQuery(criteria);

        return query.uniqueResult();
    }

    <T extends DataSet> List<T> readAll(Class<T> clazz) {

        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<T> criteria = builder.createQuery(clazz);
        criteria.from(clazz);

        return session.createQuery(criteria).list();
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
