package ru.otus.l101.dao;

import ru.otus.l101.dataset.DataSet;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.util.List;

/**
 * By default this class save application classes to DB.
 */
public class CollectionAdapter extends MyDAO {
    private final String ADAPTEE_TYPE = "__DEFAULT__";
    private final long parentId;

    public CollectionAdapter(Connection connection, long id) {
        super(connection);
        parentId = id;
    }

    @Override
    public String getAdapteeOfType() {
        return ADAPTEE_TYPE;
    }

    SQLCommand primaryKeyDescription(SQLCommand sql) {
        return sql.concat(" id BIGSERIAL PRIMARY KEY, parent_id BIGINT");
    }

    private String getTableName(Field f, Class<?> classOfElements) {
        Class<?> classOfCollection = f.getType();
        return  '"' + classGetNameToTableName(classOfCollection)
              + ' ' + classGetNameToTableName(classOfElements) + '"';
    }

    public List<String> createByCollection(Field f) {
        @SuppressWarnings("unchecked")
        Class<?> classOfElements = (Class<?>) getFirstParameterType(f);
        SQLCommand sqlCommand = new SQLCommand(
            CREATE_TABLE, getTableName(f, classOfElements)
        );

        //noinspection unchecked
        return generateSQLs((Class<? extends DataSet>) classOfElements,
            sqlCommand,
            this::primaryKeyDescription,
            this::getColumnDescription,
            this::getTablesForCollection
        );
    }

    public <T extends DataSet> List<String> writeCollection(Field f, T o) {
        @SuppressWarnings("unchecked")
        Class<?> classOfElements = (Class<?>) getFirstParameterType(f);
        SQLCommand sqlCommand = new SQLCommand(
            INSERT_INTO, getTableName(f, classOfElements)
        );

        //noinspection unchecked
        return generateSQLs((Class<? extends DataSet>) classOfElements,
            sqlCommand,
            sql -> sql.concat(Long.toString(o.getId())),
            field -> getValue(field, o),
            field -> getCollectionValues(field, o)
        );
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
