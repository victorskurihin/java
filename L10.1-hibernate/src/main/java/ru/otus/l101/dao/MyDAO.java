package ru.otus.l101.dao;

/*
 * Created by VSkurikhin at winter 2018.
 */

import com.google.common.reflect.TypeToken;
import ru.otus.l101.dataset.DataSet;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.List;

/**
 * By default this class save application classes to DB.
 */
public class MyDAO extends Adapters implements Adapter {
    private final String ADAPTEE_TYPE = DEFAULT;

    public MyDAO(Connection connection) {
        super(connection);
    }

    @Override
    public String getAdapteeOfType() {
        return ADAPTEE_TYPE;
    }

    @Override
    public List<String> create(Class<? extends DataSet> c) {
        String tableName = classGetTableName(c);
        SQLCommand sqlCommand = new SQLCommand(CREATE_TABLE, tableName);

        return generateSQLs(c, sqlCommand,
            this::primaryKeyDescription,
            this::getColumnDescription,
            this::getTablesForCollection
        );
    }

    @Override
    public <T extends DataSet> List<String> write(T o) {
        String tableName = classGetTableName(o.getClass());
        SQLCommand sqlCommand = new SQLCommand(INSERT_INTO, tableName);
        sqlCommand.valuesWord();
        // sqlCommand.concat(" VALUES");

        return generateSQLs(o.getClass(), sqlCommand,
            sql -> sql.concat(Long.toString(o.getId())),
            field -> getValue(field, o),
            field -> getCollectionValues(field, o)
        );
    }

    @Override
    public <T extends DataSet> T read(ResultSet rs, TypeToken<? extends DataSet> tt, long id) {
        return createObject(rs, tt, id);
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
