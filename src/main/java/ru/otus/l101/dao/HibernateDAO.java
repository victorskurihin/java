package ru.otus.l101.dao;

import org.hibernate.Session;
import org.hibernate.query.Query;
import ru.otus.l101.dataset.DataSet;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

public abstract class HibernateDAO {
    private final String DAO_TYPE = TypeNames.DEFAULT;

    private Session session;
    private Map<String, HibernateDAO> adapters;

    HibernateDAO(Session session) {
        this.session = session;
    }

    public void setAdapters(Map<String, HibernateDAO> adapters) {
        this.adapters = adapters;
    }

    public abstract String getAdapteeOfType();
    public abstract <T extends DataSet> void save(T dataSet);
    public abstract <T extends DataSet> T read(long id);
    public abstract <T extends DataSet> T readByName(String name);
    public abstract <T extends DataSet> List<T> readAll();

    private <T extends DataSet> void saveField(Field field, T o) {
        boolean accessible = field.isAccessible();
        field.setAccessible(true);

        try {
            String key = field.getType().getName();
            HibernateDAO dao = adapters.getOrDefault(key, null);
            if (null != dao) {
                //noinspection unchecked
                dao.save((T) field.get(o));
            }
        } catch (Throwable e) {
            e.printStackTrace();
            //noinspection ThrowableNotThrown
            new RuntimeException(e);
        } finally {
            field.setAccessible(accessible);
        }
    }

    <T extends DataSet> void saveDataSet(T dataSet) {
        for (Field field : dataSet.getClass().getDeclaredFields()) {
            if (DataSet.class.isAssignableFrom(field.getType())) {
                saveField(field, dataSet);
            }
        }
        session.save(dataSet);
    }

    protected <T extends DataSet> T read(Class<T> clazz, long id) {
        return readByName(clazz, "id", id);
        // return session.load(clazz, id); ???
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
