package ru.otus.l101.dao;

import com.google.common.reflect.TypeToken;
import ru.otus.l101.exeption.NoImplementationException;
import ru.otus.l101.dataset.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.List;

/**
 * By default this class save application classes to DB.
 */
public class TestClassMyDAO extends Adapters implements Adapter {
    private final String DAO_TYPE = TestClass.class.getName();

    public TestClassMyDAO(Connection connection) {
        super(connection);
        // throw new NoImplementationException();
    }

    @Override
    public String getAdapteeOfType() {
        throw new NoImplementationException();
    }

    @Override
    public List<String> create(Class<? extends DataSet> o) {
        throw new NoImplementationException();
    }

    @Override
    public <T extends DataSet> List<String> write(T o) {
        throw new NoImplementationException();
    }

    @Override
    public <T extends DataSet> T read(ResultSet resultSet, TypeToken<? extends DataSet> tt, long id) {
        throw new NoImplementationException();
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
