package ru.otus.l101.dao;

import org.hibernate.Session;
import org.hibernate.query.Query;
import ru.otus.l101.NoImplementationException;
import ru.otus.l101.dataset.DataSet;
import ru.otus.l101.dataset.EmptyDataSet;
import ru.otus.l101.dataset.PhoneDataSet;
import ru.otus.l101.dataset.UserDataSet;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class EmptyDataSetHibernateDAO extends HibernateDAO {
    private final String DAO_TYPE = EmptyDataSet.class.getName();

    public EmptyDataSetHibernateDAO(Session session) {
        super(session);
    }

    @Override
    public String getAdapteeOfType() {
        return DAO_TYPE;
    }

    @Override
    public <T extends DataSet> void save(T dataSet) {
        throw new NoImplementationException();
    }

    @Override
    public <T extends DataSet> T read(long id) {
        throw new NoImplementationException();
    }

    @Override
    public <T extends DataSet> T readByName(String name) {
        throw new NoImplementationException();
    }

    @Override
    public <T extends DataSet> List<T> readAll() {
        throw new NoImplementationException();
    }
}
