package ru.otus.l121.dao;

import org.hibernate.Session;
import ru.otus.l121.dataset.*;

import java.util.List;

public class PhoneDataSetDAO extends DAO {
    private final String DAO_TYPE = PhoneDataSet.class.getName();

    public PhoneDataSetDAO(Session session) {
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
    public List<PhoneDataSet> readAll() {
        return readAll(PhoneDataSet.class);
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
