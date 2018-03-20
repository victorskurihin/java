package ru.otus.l101.dao;

/*
 * Created by VSkurikhin at winter 2018.
 */

import ru.otus.l101.dataset.TestDataSetClass;

import java.sql.Connection;

/**
 * By default this class save application classes to DB.
 */
public class TestDataSetClassMyDAO extends MyDAO {
    private final String ADAPTEE_TYPE = TestDataSetClass.class.getName();

    public TestDataSetClassMyDAO(Connection connection) {
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
