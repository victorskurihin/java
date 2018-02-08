package ru.otus.l091.adapters;

import com.google.common.reflect.TypeToken;
import ru.otus.l091.NoImplementationException;
import ru.otus.l091.DataSet;
import ru.otus.l091.db.Loader;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * This is helper class used to contained the SQL command structure and contains
 * additional information such as primary key of the object, table name for the
 * object.
 */
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

/**
 * The adapter as a design pattern.
 */
public class Adapters implements TypeNames, FieldMethods {
    private static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS ";
    public static final String CREATE_TABLE_RELATIONSHIP = "CREATE TABLE " +
        "IF NOT EXISTS relationship ( parent_class_name TEXT, parent_id "  +
        "BIGINT, parent_field_name TEXT, child_class_name TEXT, child_id " +
        "BIGINT )";

    private List<SQLCommand> result;
    private Connection connection;

    Adapters(Connection connection) {
        this.connection = connection;
    }

    /**
     * The helper method for prepare name of the table for storing the object
     * of class.
     *
     * @param c the subclass of the DataSet class
     * @return the name of the table
     */
    private String classGetNameToTableName(Class<? extends DataSet> c) {
        return c.getName().replace('.','_');
    }

    /**
     * Not implemented.
     * @param field the Field
     * @return not implemented
     */
    private String getArrayDescription(Field field) {
        return null;
    }

    /**
     * The method generates the column description for DDL operation
     * by the field type. This method recursively calls method
     * 'createTableForClass' for objects by subclasses of the DataSet
     * class.
     *
     * @param field the field
     * @return the String representation of the column description
     */
    private String getColumnDescription(Field field) {
        if (null == field) { return null; }

        switch (field.getType().getName()) {
            case BOOLEAN:
                return field.getName() + " BOOLEAN NOT NULL";
            case BYTE:
            case SHORT:
                return field.getName() + " SMALLINT NOT NULL";
            case CHAR:
                return field.getName() + " CHAR(1) NOT NULL";
            case INT:
                return field.getName() + " INTEGER NOT NULL";
            case LONG:
                return field.getName() + " BIGINT NOT NULL";
            case FLOAT:
                return field.getName() + " REAL NOT NULL";
            case DOUBLE:
                return field.getName() + " DOUBLE PRECISION NOT NULL";
            case JAVA_LANG_STRING:
                return field.getName() + " TEXT NOT NULL";
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

    /**
     * The method iterates by fields from the appropriate class and get for each
     * filed  the DDL  description. This  information  will be collected in the
     * SQLCommand container.
     *
     * @param sql the instantiated SQLCommand container
     * @param c the appropriate class
     * @return the SQLCommand container
     */
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
                e.printStackTrace();
                new RuntimeException(e);
            } finally {
                field.setAccessible(accessible);
            }
        }

        return sql;
    }

    /**
     * The method generate  the DDL create query  for a table meant for
     * the appropriate class and store this to the SQLCommand container.
     *
     * @param c the appropriate class
     * @return the SQLCommand contains the DDL create query
     */
    private SQLCommand createTableForClass(Class <? extends DataSet> c) {

        String tableName = classGetNameToTableName(c);
        SQLCommand result = new SQLCommand(CREATE_TABLE, tableName);

        result.openParenthesis();
        result = getColumns(result, c);
        result.closeParenthesis();

        return result;
    }

    /**
     * The method create the list and collects to this list SQLCommand
     * containers with DDL create queries for all the DataSet subclass
     * and all aggregated subclasses of the DataSet class.
     *
     * @param c the DataSet subclass
     * @return the list of DDL queries
     */
    public List<String> createTablesForClass(Class <? extends DataSet> c) {
        result = new ArrayList<>();
        result.add(createTableForClass(c));

        return result.stream()
            .map(SQLCommand::getSql)
            .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }

    /**
     * The method generates the column value for DML operation by the field
     * value.
     *
     * @param field the field
     * @param o the object contained the field
     * @param <T> the type of the object
     * @return the String representation of the column value
     * @throws IllegalAccessException for field
     */
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

    /**
     * The method generates the column value for DML operation by the field
     * of the subclass by the DataSet class.
     *
     * @param field the field of the subclass by the DataSet class
     * @param o the object with type of the subclass by the DataSet class
     * @param s the instantiated SQLCommand container
     * @param <T> the type of the subclass by the DataSet class of the object
     * @return the string with DDL values from the object
     * @throws IllegalAccessException access to the field
     */
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

    /**
     * The method iterates by fields from the appropriate object and get for
     * each  filed  the DML value. This information will be collected in the
     * SQLCommand container.
     *
     * @param s the instantiated SQLCommand container
     * @param c the class of the appropriate object
     * @param o the appropriate object
     * @param <T> the type of the object
     * @return the SQLCommand container
     */
    private
    <T extends DataSet> SQLCommand getValues(SQLCommand s, Class<? super T> c, T o) {

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
            } catch (IllegalAccessException e) {
                //noinspection ThrowableNotThrown
                new RuntimeException(e);
            } catch (Throwable e) {
                e.printStackTrace();
                //noinspection ThrowableNotThrown
                new RuntimeException(e);
            } finally {
                field.setAccessible(accessible);
            }
        }

        return s;
    }

    /**
     * The method generate the DML insert query for a table meant for the
     * appropriate object and store this query to the SQLCommand container.
     *
     * @param o the appropriate object
     * @param <T> the type of the appropriate object
     * @return the appropriate object
     */
    private <T extends DataSet> SQLCommand insertObjectToTable(T o) {

        String tableName = classGetNameToTableName(o.getClass());
        SQLCommand result = new SQLCommand("INSERT INTO ",  tableName);

        result.concat(" VALUES").openParenthesis();
        //noinspection unchecked
        result = getValues(result, (Class<T>) o.getClass(), o);
        result.closeParenthesis();

        return result;
    }

    /**
     * The method create the list and collects to this list  SQLCommand
     * containers with DML insert queries for the object of the DataSet
     * subclass types and all aggregated objects of DataSet subclasses.
     *
     * @param o the object of the DataSet subclass
     * @param <T> the type of the object of the DataSet subclass
     * @return the list of DML insert commands
     */
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
    @SuppressWarnings("WeakerAccess")
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

    /**
     * The method fills the object. The stored column filling the correspond
     * field in the object.
     *
     * @param object the object
     * @param field the correspond field
     * @param rs the ResultSet from the select query
     * @param <T> the type of the object
     * @return the filled object
     * @throws IllegalAccessException the access to the field
     * @throws SQLException in the loader
     */
    private <T> T setField(T object, Field field, ResultSet rs)
        throws IllegalAccessException, SQLException {

        switch (field.getType().getTypeName()) {
            case BOOLEAN:
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

            //noinspection unchecked
            Class<? extends DataSet> c = (Class<? extends DataSet>) field.getType();
            Loader loader = new Loader(connection);
            Object value = loader.load(
                id, c, resultSet -> {
                    if (resultSet.next()) {
                        return createObject(resultSet, TypeToken.of(c), id);
                    } else
                        throw new RuntimeException("SQL Error!!!");
                }
            );
            field.set(object, value);

            return object;
        }

        throw new NoImplementationException();
    }

    /**
     * The method create the object. The method iterates by fields from the
     * type contained in TypeToken class and get for each filed  the value.
     *
     * @param rs the ResultSet from the select query
     * @param tt the TypeToken object
     * @param id the id of the stored object
     * @param <T> the type of the object
     * @return the completely created object
     */
    public  <T> T createObject(ResultSet rs, TypeToken<?> tt, long id) {
        //noinspection unchecked
        T result = (T) newInstance(tt.getRawType(), id);

        for (Field field : tt.getRawType().getDeclaredFields()) {
            boolean accessible = field.isAccessible();
            field.setAccessible(true);

            try {
                result = setField(result, field, rs);
            } catch (IllegalAccessException | SQLException e) {
                //noinspection ThrowableNotThrown
                new RuntimeException(e);
            } catch (Throwable e) {
                e.printStackTrace();
                //noinspection ThrowableNotThrown
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
