package ru.otus.l121.dao;

import org.hibernate.Session;
import ru.otus.l121.dataset.*;

import java.util.List;
import java.util.Set;

public class UserDataSetHibernateDAO extends HibernateDAO {
    private final String DAO_TYPE = UserDataSet.class.getName();

    public UserDataSetHibernateDAO(Session session) {
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
        UserDataSet userDataSet = read(UserDataSet.class, id);
        return userDataSet;
    }

    @SuppressWarnings("unchecked")
    @Override
    public UserDataSet readByName(String name) {
        return readByName(UserDataSet.class, "name", name);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<UserDataSet> readAll() {
        return readAll(UserDataSet.class);
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
