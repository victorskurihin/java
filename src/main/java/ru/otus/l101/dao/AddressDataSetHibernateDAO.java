package ru.otus.l101.dao;

import org.hibernate.Session;
import ru.otus.l101.dataset.*;

import java.util.List;

public class AddressDataSetHibernateDAO extends HibernateDAO {
    private final String DAO_TYPE = AddressDataSet.class.getName();

    public AddressDataSetHibernateDAO(Session session) {
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
    public AddressDataSet readByName(String street) {
        return readByName(AddressDataSet.class, "street", street);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<AddressDataSet> readAll() {
        return readAll(AddressDataSet.class);
    }
}
