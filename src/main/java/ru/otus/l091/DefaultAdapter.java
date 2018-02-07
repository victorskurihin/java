package ru.otus.l091;

import com.google.common.reflect.TypeToken;

import java.sql.ResultSet;
import java.util.List;

public class DefaultAdapter extends Adapters implements Adapter {
    private final String ADAPTEE_TYPE = "__DEFAULT__";

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
    public <T> T read(ResultSet resultSet, TypeToken<? extends DataSet> tt) { return null; }
}
