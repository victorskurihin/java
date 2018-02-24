package ru.otus.l121.dao;

import org.hibernate.Session;
import org.hibernate.query.Query;
import ru.otus.l121.dataset.DataSet;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

/**
 * This is conract for DAO classes with default the helper realization.
 */
public abstract class DAO {
    private final String DAO_TYPE = TypeNames.DEFAULT;

    protected Session session;
    private Map<String, DAO> adapters;

    DAO(Session session) {
        this.session = session;
    }

    public void setAdapters(Map<String, DAO> adapters) {
        this.adapters = adapters;
    }

    public abstract String getAdapteeOfType();
    public abstract <T extends DataSet> void save(T dataSet);
    public abstract <T extends DataSet> T read(long id);
    public abstract <T extends DataSet> T readByName(String name);
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
