package ru.otus.l091;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

class SQLCommand {
    private final String tableName;
    private long id;
    private String sql;

    SQLCommand(String sql, String tableName) {
        this.tableName = tableName;
        this.sql = sql + tableName;
    }

    public String getTableName() {
        return tableName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public SQLCommand closeParenthesis() {
        sql = sql.concat(" ) ");
        return this;
    }

    public SQLCommand openParenthesis() {
        sql = sql.concat(" ( ");
        return this;
    }

    public SQLCommand concat(String s) {
        sql = sql.concat(s);
        return this;
    }
}

public class Adapters implements TypeNames {
    public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS ";
    protected Map<String, Adapter> adapters; // the map of adapters
    private List<SQLCommand> result;

    public void setAdapters(Map<String, Adapter> map) {
        adapters = map;
    }

    String classGetNameToTableName(Class <? extends DataSet> c) {
        return c.getName().replace('.','_');
    }

    private String getArrayDescription(Field field) {
        return null;
    }

    private String getColumnDescription(Field field) {
        if (null == field) { return null; }

        switch (field.getType().getName()) {
            case BOOLEAN:
                return field.getName() + " BOOLEAN";
            case BYTE:
            case SHORT:
                return field.getName() + " SMALLINT";
            case CHAR:
                return field.getName() + " CHAR(1)";
            case INT:
                return field.getName() + " INTEGER";
            case LONG:
                return field.getName() + " BIGINT";
            case FLOAT:
                return field.getName() + " REAL";
            case DOUBLE:
                return field.getName() + " DOUBLE PRECISION";
            case JAVA_LANG_STRING:
                return field.getName() + " TEXT";
        }

        if (field.getType().isArray()) {
            return getArrayDescription(field);
        }

        if (DataSet.class.isAssignableFrom(field.getType())) {
            //noinspection unchecked
            this.result.add(
                createTableForClass( (Class<? extends DataSet>) field.getType())
            );
            // TODO create relationship
            return null;
        }

        throw new NoImplementationException();
    }

    private String separator = ", ";

    private SQLCommand getColumnsForClass(SQLCommand sql, Class <? extends DataSet> c) {

        if (DataSet.class == c) {
            sql =  sql.concat("id BIGSERIAL PRIMARY KEY");
            return sql;
        }

        if (DataSet.class.isAssignableFrom(c.getSuperclass())) {
            //noinspection unchecked
            sql = getColumnsForClass(
                sql, (Class<? extends DataSet>) c.getSuperclass()
            );
        }

        for (Field field : c.getDeclaredFields()) {
            boolean accessible = field.isAccessible();
            field.setAccessible(true);

            try {
                String columnDesc = getColumnDescription(field);

                if (null != columnDesc) {
                    //noinspection ResultOfMethodCallIgnored
                    sql = sql.concat(separator).concat(columnDesc);
                }
            } catch (Throwable e) {
                throw e;
            } finally {
                field.setAccessible(accessible);
            }
        }

        return sql;
    }

    public SQLCommand createTableForClass(Class <? extends DataSet> c) {

        String tableName = classGetNameToTableName(c);
        SQLCommand result = new SQLCommand(CREATE_TABLE, tableName);

        result.openParenthesis();
        result = getColumnsForClass(result, c);
        result.closeParenthesis();

        return result;
    }

    public List<String> createTablesForClass(Class <? extends DataSet> c) {
        result = new ArrayList<>();
        result.add(createTableForClass(c));

        return result.stream()
            .map(SQLCommand::getSql)
            .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }

    private <T extends DataSet> String getValue(Field field, Class<? super T> c, T o)
        throws IllegalAccessException {

        if (null == field) { return null; }

        switch (field.getType().getName()) {
            case BOOLEAN:
                return field.getBoolean(o) ? "TRUE" : "FALSE";
            case BYTE:
                return Byte.toString(field.getByte(o));
            case SHORT:
                return Short.toString(field.getShort(o));
            case CHAR:
                return String.format("'%c'", field.getChar(o));
            case INT:
                return Integer.toString(field.getInt(o));
            case LONG:
                return Long.toString(field.getLong(o));
            case FLOAT:
                return Float.toString(field.getFloat(o));
            case DOUBLE:
                return Double.toString(field.getDouble(o));
            case JAVA_LANG_STRING:
                return String.format("'%s'", (String) field.get(o));
        }

        if (field.getType().isArray()) {
            throw new NoImplementationException();
        }

        if (DataSet.class.isAssignableFrom(field.getType())) {
            //noinspection unchecked
            this.result.add(
                insertObjectToTable((T) field.get(o))
            );
            // TODO add relationship
            return null;
        }

        throw new NoImplementationException();
    }

    private <T extends DataSet> SQLCommand getValues(SQLCommand s, Class<? super T> c, T o)
        throws IllegalAccessException {

        if (DataSet.class == c) {
            s = s.concat(Long.toString(o.getId()));
            return s;
        }

        if (DataSet.class.isAssignableFrom(c.getSuperclass())) {
            //noinspection unchecked
            s = getValues(s, c.getSuperclass(), o);
        }

        for (Field field : c.getDeclaredFields()) {
            boolean accessible = field.isAccessible();
            field.setAccessible(true);

            try {
                String value = getValue(field, c, o);

                if (null != value) {
                    //noinspection ResultOfMethodCallIgnored
                    s = s.concat(separator).concat(value);
                }
            } catch (Throwable e) {
                throw e;
            } finally {
                field.setAccessible(accessible);
            }
        }

        return s;
    }

    private <T extends DataSet> SQLCommand getValuesObject(SQLCommand s, T o) {
        if (DataSet.class.isAssignableFrom(o.getClass().getSuperclass())) {
            //noinspection unchecked
            try {
                s = getValues(s, (Class<T>) o.getClass(), o);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        return s;
    }

    private <T extends DataSet> SQLCommand insertObjectToTable(T o) {

        String tableName = classGetNameToTableName(o.getClass());
        SQLCommand result = new SQLCommand("INSERT INTO ",  tableName);

        result.concat(" VALUES ").openParenthesis();
        result = getValuesObject(result, o);
        result.closeParenthesis();

        return result;
    }

    public <T extends DataSet> List<String> insertObjectsToTables(T o) {
        this.result = new ArrayList<>();
        this.result.add(insertObjectToTable(o));
//        return this.result;
        return null;
    }
}
