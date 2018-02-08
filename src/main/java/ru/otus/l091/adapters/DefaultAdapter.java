package ru.otus.l091.adapters;

import com.google.common.reflect.TypeToken;
import ru.otus.l091.DataSet;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.List;

/**
 * By default this class save application classes to DB.
 */
public class DefaultAdapter extends Adapters implements Adapter {
    private final String ADAPTEE_TYPE = "__DEFAULT__";

    public DefaultAdapter(Connection connection) {
        super(connection);
    }

    @Override
    public String getAdapteeOfType() {
        return ADAPTEE_TYPE;
    }

    @Override
    public List<String> create(Class<? extends DataSet> c) {
        return createTablesForClass(c);
    }

    @Override
    public <T extends DataSet> List<String> write(T o) {
        return insertObjectsToTables(o);
    }

    @Override
    public <T> T read(ResultSet rs, TypeToken<? extends DataSet> tt, long id) {
        return createObject(rs, tt, id);
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
