package com.github.intermon.dao;

/*
 * Created by VSkurikhin at Wed Apr 25 13:23:58 MSK 2018.
 */

import org.hibernate.Session;
import com.github.intermon.dataset.*;

import java.util.List;

/**
 * The class for instances of the Data Access Object. An instance of this class
 * will  serve instances  of UserDataSet for  the persistence layer  of data in
 * them.
 */

public class UserDataSetDAO extends DAO {
    private final String DAO_TYPE = UserDataSet.class.getName();

    public UserDataSetDAO(Session session) {
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
