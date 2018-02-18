package ru.otus.l101.dao;

import org.hibernate.Session;
import ru.otus.l101.dataset.*;

import java.util.List;

public class PhoneDataSetHibernateDAO extends HibernateDAO {
    private final String DAO_TYPE = PhoneDataSet.class.getName();

    public PhoneDataSetHibernateDAO(Session session) {
        super(session);
    }

    @Override
    public String getAdapteeOfType() {
        return DAO_TYPE;
    }

    @Override
    public <T extends DataSet> void save(T dataSet) {
        saveDataSet(dataSet);
    }

    @SuppressWarnings("unchecked")
    @Override
    public UserDataSet read(long id) {
        return read(UserDataSet.class, id);
    }

    @SuppressWarnings("unchecked")
    @Override
    public PhoneDataSet readByName(String number) {
        return readByName(PhoneDataSet.class, "number", number);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<UserDataSet> readAll() {
        return readAll(UserDataSet.class);
    }
}
