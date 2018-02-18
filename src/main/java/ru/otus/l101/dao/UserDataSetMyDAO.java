package ru.otus.l101.dao;

import com.google.common.reflect.TypeToken;
import ru.otus.l101.dataset.DataSet;
import ru.otus.l101.dataset.UserDataSet;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.List;

/**
 * By default this class save application classes to DB.
 */
public class UserDataSetMyDAO extends MyDAO {
    private final String ADAPTEE_TYPE = UserDataSet.class.getName();

    public UserDataSetMyDAO(Connection connection) {
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
