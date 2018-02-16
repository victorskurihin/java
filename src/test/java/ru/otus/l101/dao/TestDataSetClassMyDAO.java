package ru.otus.l101.dao;

import com.google.common.reflect.TypeToken;
import ru.otus.l101.dataset.DataSet;
import ru.otus.l101.dataset.TestDataSetClass;
import ru.otus.l101.dataset.UserDataSet;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.List;
import java.util.function.UnaryOperator;

/**
 * By default this class save application classes to DB.
 */
public class TestDataSetClassMyDAO extends Adapters implements Adapter {
    private final String ADAPTEE_TYPE = TestDataSetClass.class.getName();

    public TestDataSetClassMyDAO(Connection connection) {
        super(connection);
    }

    @Override
    public String getAdapteeOfType() {
        return ADAPTEE_TYPE;
    }

    @Override
    public List<String> create(Class<? extends DataSet> c) {
        return generateSQLs(c,
            sql -> sql.concat(" id BIGSERIAL PRIMARY KEY"),
            this::getColumnDescription,
            f -> {
                // TODO
                return "\"rt name\" TEXT";
            }
        );
    }

    @Override
    public <T extends DataSet> List<String> write(T o) {
        return generateSQLs(o.getClass(),
            sql -> { return sql.concat(Long.toString(o.getId())); },
            field -> { return getValue(field, o); },
            field -> { return null; }
        );
        // return insertObjectsToTables(o);
    }

    @Override
    public <T> T read(ResultSet rs, TypeToken<? extends DataSet> tt, long id) {
        return createObject(rs, tt, id);
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
