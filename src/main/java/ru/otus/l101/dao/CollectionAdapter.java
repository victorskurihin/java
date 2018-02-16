package ru.otus.l101.dao;

import com.google.common.reflect.TypeToken;
import ru.otus.l101.NoImplementationException;
import ru.otus.l101.dataset.DataSet;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * By default this class save application classes to DB.
 */
public class CollectionAdapter extends Adapters implements Adapter {
    private final String ADAPTEE_TYPE = "__DEFAULT__";
    private final long parentId;

    public CollectionAdapter(Connection connection, long id) {
        super(connection);
        parentId = id;
    }

    /**
     * TODO experimental
     * @param tt the type token of the collection
     * @return the type token the element from the collection
     */
    private TypeToken<?> getTTOfFirstParameterAddMethod(TypeToken<?> tt) {
        Method[] methods = Collection.class.getMethods();
        Optional<Method> optionalMethod = Optional.empty();

        for (Method m : methods) {
            if (m.getName().equals("add")) {
                if (1 == m.getParameterCount()) {
                    optionalMethod = Optional.of(m);
                    break;
                }
            }
        }

        if (! optionalMethod.isPresent()) {
            throw new NoImplementationException();
        }

        return tt.resolveType(
            optionalMethod.get().getGenericParameterTypes()[0]
        );
    }

    @Override
    public String getAdapteeOfType() {
        return ADAPTEE_TYPE;
    }

    @Override
    public List<String> create(Class<? extends DataSet> c) {
        return createTablesForClass(c);
    }

    public List<String> createByList(Class<? extends Collection<? extends DataSet>> c) {
        return null;
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
