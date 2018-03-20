package ru.otus.l101.dao;

/*
 * Created by VSkurikhin at winter 2018.
 */

import com.google.common.reflect.TypeToken;
import ru.otus.l101.dataset.DataSet;
import ru.otus.l101.dataset.PhoneDataSet;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.List;

/**
 * By default this class save application classes to DB.
 */
public class PhoneDataSetMyDAO extends MyDAO {
    private final String ADAPTEE_TYPE = PhoneDataSet.class.getName();

    public PhoneDataSetMyDAO(Connection connection) {
        super(connection);
    }

    @Override
    public String getAdapteeOfType() {
        return ADAPTEE_TYPE;
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
