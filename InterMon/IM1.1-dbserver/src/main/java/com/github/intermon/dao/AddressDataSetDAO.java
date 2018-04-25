package com.github.intermon.dao;

/*
 * Created by VSkurikhin at Sun Apr 25 13:22:49 MSK 2018.
 */

import org.hibernate.Session;
import com.github.intermon.dataset.*;

import java.util.List;

/**
 * The class for instances of the Data access object. An instance of this class
 * will serve instances of AddressDataSet for  the persistence layer of data in
 * them.
 */
public class AddressDataSetDAO extends DAO {
    private final String DAO_TYPE = AddressDataSet.class.getName();

    /**
     * The constructor remembers the DBService session.
     *
     * @param session the DBService session
     */
    public AddressDataSetDAO(Session session) {
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

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
