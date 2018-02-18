package ru.otus.l101.dao;

import java.sql.Connection;

/**
 * By default this class save application classes to DB.
 */
public class TestComplexDataSetClassMyDAO extends MyDAO {
    private final String ADAPTEE_TYPE = TestComplexDataSetClassMyDAO.class.getName();

    public TestComplexDataSetClassMyDAO(Connection connection) {
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
