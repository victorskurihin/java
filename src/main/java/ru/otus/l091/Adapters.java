package ru.otus.l091;

import com.google.common.reflect.TypeToken;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

class SQLCommand {
    private final String tableName;
    private long id;
    private String sql;

    SQLCommand(String sql) {
        tableName = "";
        this.sql = sql;
    }

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
        sql = sql.concat(" )");
        return this;
    }

    public SQLCommand openParenthesis() {
        sql = sql.concat(" (");
        return this;
    }

    public SQLCommand concat(String s) {
        sql = sql.concat(s);
        return this;
    }
}

public class Adapters implements TypeNames, FieldMethods {
    public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS ";
    public static final String CREATE_TABLE_RELATIONSHIP = "CREATE TABLE " +
        "IF NOT EXISTS relationship ( parent_class_name TEXT, parent_id "  +
        "BIGINT, parent_field_name TEXT, child_class_name TEXT, child_id " +
        "BIGINT )";

    protected Map<String, Adapter> adapters; // the map of adapters
    private List<SQLCommand> result;
    private Connection connection;

    Adapters(Connection connection) {
        this.connection = connection;
    }

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
            result.add(
                createTableForClass( (Class<? extends DataSet>) field.getType())
            );
            result.add(new SQLCommand(CREATE_TABLE_RELATIONSHIP));
            return "\"fk " + field.getName() + '"' + " BIGINT";
        }

        throw new NoImplementationException();
    }

    private String separator = ", ";

    private SQLCommand getColumns(SQLCommand sql, Class <? extends DataSet> c) {

        if (DataSet.class == c) {
            sql =  sql.concat(" id BIGSERIAL PRIMARY KEY");
            return sql;
        }

        if (DataSet.class.isAssignableFrom(c.getSuperclass())) {
            //noinspection unchecked
            sql = getColumns(
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

    private SQLCommand createTableForClass(Class <? extends DataSet> c) {

        String tableName = classGetNameToTableName(c);
        SQLCommand result = new SQLCommand(CREATE_TABLE, tableName);

        result.openParenthesis();
        result = getColumns(result, c);
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

    private <T extends DataSet> String getValue(Field field, T o)
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

        throw new NoImplementationException();
    }

    private
    <T extends DataSet> String getDataSetValue(Field field, T o, SQLCommand s)
        throws IllegalAccessException {

        // DataSet.class.isAssignableFrom(field.getType())
        if (isSubclassOfDataSet(field.getType())) {
            //noinspection unchecked
            SQLCommand sql = insertObjectToTable((T) field.get(o));
            result.add(sql);
            // add relationship
            SQLCommand sqlRelation = new SQLCommand(String.format(
                "INSERT INTO relationship VALUES ('%s', %d, '%s', '%s', %d)",
                s.getTableName(), s.getId(), field.getName(),
                sql.getTableName(), sql.getId()
            ));
            result.add(sqlRelation);
            return Long.toString(sql.getId());
        }
        return null;
    }

    private
    <T extends DataSet> SQLCommand getValues(SQLCommand s, Class<? super T> c, T o)
        throws IllegalAccessException {

        if (DataSet.class == c) {
            s.setId(o.getId());
            s = s.concat(Long.toString(o.getId()));
            return s;
        }

        // DataSet.class.isAssignableFrom(c.getSuperclass())
        if (isSubclassOfDataSet(c.getSuperclass())) {
            //noinspection unchecked
            s = getValues(s, c.getSuperclass(), o);
        }

        for (Field field : c.getDeclaredFields()) {
            boolean accessible = field.isAccessible();
            field.setAccessible(true);

            try {
                String value = getDataSetValue(field, o, s);

                if (null == value) {
                    value = getValue(field, o);
                }

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
            try {
                //noinspection unchecked
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

        result.concat(" VALUES").openParenthesis();
        result = getValuesObject(result, o);
        result.closeParenthesis();

        return result;
    }

    public <T extends DataSet> List<String> insertObjectsToTables(T o) {
        result = new ArrayList<>();
        result.add(insertObjectToTable(o));

        return result.stream()
            .map(SQLCommand::getSql)
            .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }

    /**
     * The method create instance of test class.
     *
     * @param runClass - the class
     * @return - the object of test class
     */
    static <T> Object newInstance(Class<T> runClass, long id) {
        //noinspection TryWithIdenticalCatches
        try {
            return runClass.getDeclaredConstructor(long.class).newInstance(id);
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    private <T> T setField(T object, Field field, ResultSet rs)
        throws IllegalAccessException, SQLException {

        switch (field.getType().getTypeName()) {
            case BOOLEAN:
                System.out.println("boolean");
                return setFieldBoolean(object, field, rs);
            case BYTE:
                return setFieldByte(object, field, rs);
            case CHAR:
                return setFieldChar(object, field, rs);
            case SHORT:
                return setFieldShort(object, field, rs);
            case INT:
                return setFieldInt(object, field, rs);
            case LONG:
                return setFieldLong(object, field, rs);
            case FLOAT:
                return setFieldFloat(object, field, rs);
            case DOUBLE:
                return setFieldDouble(object, field, rs);
            case JAVA_LANG_STRING:
                return setFieldString(object, field, rs);
        }

        int column = rs.findColumn("fk " + field.getName());
        if (isSubclassOfDataSet(field.getType()) && column > 0) {
            long id = getFK(field, rs);
            System.out.println("Ok " + id);
            //noinspection unchecked
            Class<? extends DataSet> c = (Class<? extends DataSet>) field.getType();
            Loader loader = new Loader(connection);
            Object value = (Object) loader.load(
                id, c, resultSet -> {
                    if (resultSet.next()) {
                        return createObject(resultSet, TypeToken.of(c), id);
                    } else
                        throw new RuntimeException("SQL Error!!!");
                }
            );
            field.set(object, value);
        }

        throw new NoImplementationException();
    }

    <T> T createObject(ResultSet rs, TypeToken<?> tt, long id) {
        //noinspection unchecked
        T result = (T) newInstance(tt.getRawType(), id);

        for (Field field : tt.getRawType().getDeclaredFields()) {
            System.out.println("field.getName() = " + field.getName());
            boolean accessible = field.isAccessible();
            try {
                result = setField(result, field, rs);
            } catch (Exception e) {
                new RuntimeException(e);
            } finally {
                field.setAccessible(accessible);
            }
        }

        return result;
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
