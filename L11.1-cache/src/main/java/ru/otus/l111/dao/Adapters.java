package ru.otus.l111.dao;

/*
 * Created by VSkurikhin at winter 2018.
 */

import com.google.common.reflect.TypeToken;
import ru.otus.l111.db.CollectionLoader;
import ru.otus.l111.exeption.*;
import ru.otus.l111.dataset.DataSet;
import ru.otus.l111.db.Loader;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.function.Function;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

/**
 * The adapter as a design pattern.
 */
public class Adapters implements TypeNames, FieldMethods {
    public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS ";
    public static final String INSERT_INTO = "INSERT INTO ";
    private final String separator = ", ";

    private List<SQLCommand> result;
    private Connection connection;
    private Map<String, Adapter> adapters = new HashMap<>();

    private UnaryOperator indexUnaryOperator = null;
    private Function fieldFunction= null;
    private Function listFieldFunction = null;

    Adapters(Connection connection) {
        this.connection = connection;
    }

    public void setAdapters(Map<String, Adapter> adapters) {
        this.adapters = adapters;
    }

    /**
     * Not implemented.
     * @param f the Field
     * @return not implemented
     */
    private String getArrayDescription(Field f) {
        return null;
    }

    /**
     *
     * @param f field
     * @return the String
     */
    private String expressionByField(Field f) {
        boolean accessible = f.isAccessible();
        f.setAccessible(true);

        if (isMyORMFieldIgnore(f)) {
            return null;
        }

        try {
            if (isImplementatorOfCollection(f.getType())) {
                //noinspection unchecked
                return (String) listFieldFunction.apply(f);
            } else {
                //noinspection unchecked
                return (String) fieldFunction.apply(f);
            }
        } catch (Throwable e) {
            throw new FieldFunctionException(e);
        } finally {
            f.setAccessible(accessible);
        }
    }

    /**
     * The method iterates by fields from the appropriate class and get for each
     * filed  the DDL  description. This  information  will be collected in the
     * SQLCommand container.
     *
     * @param sql the instantiated SQLCommand container
     * @param c the appropriate class
     * @return the SQLCommand container
     */
    private SQLCommand constructSQL(SQLCommand sql, Class<? extends DataSet> c) {

        if (DataSet.class == c) {
            //noinspection unchecked
            return (SQLCommand) indexUnaryOperator.apply(sql);
        }

        if (isSubclassOfDataSet(c.getSuperclass())) {
            //noinspection unchecked
            sql = constructSQL(
                sql, (Class<? extends DataSet>) c.getSuperclass()
            );
        }

        for (Field field : c.getDeclaredFields()) {
            String sqlExpression = expressionByField(field);
            if (null != sqlExpression) {
                sql = sql.concat(separator).concat(sqlExpression);
            }
        }

        return sql;
    }

    /**
     * The method generate  the SQL query for a table meant for the appropriate
     * class and store this to the SQLCommand container.
     *
     * @param c the appropriate class
     * @param sql the initialized SQLCommand container
     * @return the SQLCommand contains the DDL create query
     */
    private
    SQLCommand createSQL(Class <? extends DataSet> c, SQLCommand sql) {

        sql.openParenthesis();
        sql = constructSQL(sql, c);
        sql.closeParenthesis();

        return sql;
    }

    /**
     * The  method  create  the list  and  collects  to this  list   SQLCommand
     * containers with SQL queries for the object of the DataSet subclass types
     * and all aggregated objects of DataSet subclasses.
     *
     * @param c the DataSet subclass
     * @param sql the initialized SQLCommand container
     * @param indexUnaryOperator
     * @param fieldFunction
     * @param listFieldFunction
     * @return the list of SQL queries
     */
    public List<String> generateSQLs(
        Class <? extends DataSet> c, SQLCommand sql,
        UnaryOperator<SQLCommand> indexUnaryOperator,
        Function<Field, String> fieldFunction,
        Function<Field, String> listFieldFunction)
    {
        this.indexUnaryOperator = indexUnaryOperator;
        this.fieldFunction      = fieldFunction;
        this.listFieldFunction  = listFieldFunction;
        result = new ArrayList<>();
        result.add(createSQL(c, sql));
        return result.stream()
            .map(SQLCommand::getSql)
            .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }

    SQLCommand primaryKeyDescription(SQLCommand sql) {
        return sql.concat(" id BIGSERIAL PRIMARY KEY");
    }

    /**
     * The method generates the column description for DDL operation
     * by the field type. This method recursively calls method
     * 'createTableForClass' for objects by subclasses of the DataSet
     * class.
     *
     * @param f the field
     * @return the String representation of the column description
     */
    String getColumnDescription(Field f) {
        if (null == f) { return null; }

        switch (f.getType().getName()) {
            case BOOLEAN:
                return f.getName() + " BOOLEAN NOT NULL";
            case BYTE:
            case SHORT:
                return f.getName() + " SMALLINT NOT NULL";
            case CHAR:
                return f.getName() + " CHAR(1) NOT NULL";
            case INT:
                return f.getName() + " INTEGER NOT NULL";
            case LONG:
                return f.getName() + " BIGINT NOT NULL";
            case FLOAT:
                return f.getName() + " REAL NOT NULL";
            case DOUBLE:
                return f.getName() + " DOUBLE PRECISION NOT NULL";
            case JAVA_LANG_STRING:
                return f.getName() + " TEXT NOT NULL";
        }

        if (f.getType().isArray()) {
            return getArrayDescription(f);
        }

        if (DataSet.class.isAssignableFrom(f.getType())) {
            Adapter adapter = adapters.getOrDefault(
                f.getType().getName(), adapters.get(DEFAULT)
            );

            //noinspection unchecked
            List<SQLCommand> sqlCommands = adapter
                .create((Class<? extends DataSet>) f.getType())
                .stream().map(SQLCommand::new)
                .collect(Collectors.toList());
            result.addAll(sqlCommands);

            return "\"fk " + f.getName() + '"' + " BIGINT";
        }

        throw new NoImplementationException();
    }

    String getTablesForCollection(Field f) {
        CollectionAdapter collectionAdapter = new CollectionAdapter(
            connection, 0
        );

        result.addAll(
            collectionAdapter.createByCollection(f).stream()
                .map(SQLCommand::new)
                .collect(Collectors.toList())
        );

        return "\"rt " + f.getName() + '"' + " TEXT";
    }

    /**
     * The method generates the column value for DML operation by the field
     * value.
     *
     * @param f the field
     * @param o the object contained the field
     * @param <T> the type of the object
     * @return the String representation of the column value
     * @throws IllegalAccessException for field
     */
    private <T extends DataSet> String getBuildInValue(Field f, T o)
        throws IllegalAccessException {

        if (null == f) { return null; }

        switch (f.getType().getName()) {
            case BOOLEAN:
                return f.getBoolean(o) ? "TRUE" : "FALSE";
            case BYTE:
                return Byte.toString(f.getByte(o));
            case SHORT:
                return Short.toString(f.getShort(o));
            case CHAR:
                return String.format("'%c'", f.getChar(o));
            case INT:
                return Integer.toString(f.getInt(o));
            case LONG:
                return Long.toString(f.getLong(o));
            case FLOAT:
                return Float.toString(f.getFloat(o));
            case DOUBLE:
                return Double.toString(f.getDouble(o));
            case JAVA_LANG_STRING:
                return String.format("'%s'", (String) f.get(o));
        }

        if (f.getType().isArray()) {
            throw new NoImplementationException();
        }

        throw new NoImplementationException();
    }

    /**
     * The method generates the column value for DML operation by the field
     * of the subclass by the DataSet class.
     *
     * @param f the field of the subclass by the DataSet class
     * @param o the object with type of the subclass by the DataSet class
     * @param <T> the type of the subclass by the DataSet class of the object
     * @return the string with DDL values from the object
     * @throws IllegalAccessException access to the field
     */
    private <T extends DataSet> String getDataSetValue(Field f, T o)
        throws IllegalAccessException {

        // DataSet.class.isAssignableFrom(field.getType())
        if (isSubclassOfDataSet(f.getType())) {
            //noinspection unchecked

            DataSet objectInField = (DataSet) f.get(o);

            Adapter adapter = adapters.getOrDefault(
                objectInField.getClass().getName(), adapters.get(DEFAULT)
            );

            List<SQLCommand> sqlCommands = adapter.write(objectInField)
                .stream().map(SQLCommand::new)
                .collect(Collectors.toList());
            result.addAll(sqlCommands);
            return Long.toString(objectInField.getId());
        }
        return null;
    }

    /**
     * The method iterates by fields from the appropriate object and get for
     * each  filed  the DML value. This information will be collected in the
     * SQLCommand container.
     *
     * @param f
     * @param o the appropriate object
     * @param <T> the type of the object
     * @return String
     */
    <T extends DataSet> String getValue(Field f, T o) {

        try {
            if (null == f.get(o)) {
                return "NULL";
            }

            String value = getDataSetValue(f, o);

            if (null == value) {
                value = getBuildInValue(f, o);
            }
            return value;
        } catch (IllegalAccessException e) {
            throw new AccessException(e);
        }
    }

    private <T extends DataSet>
    List<SQLCommand> iterateOverCollection(Collection c, Field f, T o) {
        List<SQLCommand> sqlCommandList = new ArrayList<>();
        CollectionAdapter cAdapter = new CollectionAdapter(
            connection, o.getId()
        );

        for (Object element : c) {
            if (isSubclassOfDataSet(element.getClass())) {
                DataSet dataSet = (DataSet) element;
                sqlCommandList.addAll(
                    cAdapter.writeCollection(f, dataSet)
                        .stream().map(SQLCommand::new)
                        .collect(Collectors.toList())
                );
            }
        }
        return sqlCommandList;
    }


    <T extends DataSet> String getCollectionValues(Field f, T o) {
        if (isImplementatorOfCollection(f.getType())) {
            try {
                Collection<?> collection = (Collection<?>) f.get(o);
                result.addAll(iterateOverCollection(collection, f, o));
                return getTableName(f);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        throw new NotCollectionExeption("The field " + f + " isn't collection.");
    }

    /**
     * The method create instance of test class.
     *
     * @param c - the class
     * @return - the object of test class
     */
    private static <T> Object newInstance(Class<T> c, long id) {
        //noinspection TryWithIdenticalCatches
        try {
            return c.getDeclaredConstructor(long.class).newInstance(id);
        } catch (InstantiationException | IllegalAccessException e) {
            throw new NewInstanceException(e);
        } catch (InvocationTargetException | NoSuchMethodException e) {
            throw new NewInstanceException(e);
        }
    }

    private <T extends DataSet> Class<T> getFirstParameterClass(Field f) {
        //noinspection unchecked
        return (Class<T>) getFirstParameterType(f);
    }

    private <T extends DataSet>
    T loadCollection(T o, Field f, ResultSet rs, int column)
        throws SQLException {

        String tableName = rs.getString(column);
        String[] array = tableName.split(" ");

        if (array.length < 2) {
            return o;
        }

        try {
            //noinspection unchecked
            Collection<DataSet> collection = (Collection<DataSet>) f.get(o);
            CollectionLoader loader = new CollectionLoader(connection);
            Class<? extends DataSet> c = getFirstParameterClass(f);

            collection.addAll(loader.load(
                o.getId(), tableName, resultSet -> {
                    List<DataSet> r = new ArrayList<>();
                    while (resultSet.next()) {
                        long id = resultSet.getLong(1);
                        r.add(createObject(resultSet, TypeToken.of(c), id));
                    }
                    return r;
                }
            ));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return o;
    }

    /**
     * The method fills the object. The stored column filling the correspond
     * field in the object.
     *
     * @param o the object
     * @param f the correspond field
     * @param rs the ResultSet from the select query
     * @param <T> the type of the object
     * @return the filled object
     * @throws IllegalAccessException the access to the field
     * @throws SQLException in the loader
     */
    private <T extends DataSet> T setField(T o, Field f, ResultSet rs)
        throws IllegalAccessException, SQLException {

        if (isMyORMFieldIgnore(f)) {
            return o;
        }

        switch (f.getType().getTypeName()) {
            case BOOLEAN:
                return setFieldBoolean(o, f, rs);
            case BYTE:
                return setFieldByte(o, f, rs);
            case CHAR:
                return setFieldChar(o, f, rs);
            case SHORT:
                return setFieldShort(o, f, rs);
            case INT:
                return setFieldInt(o, f, rs);
            case LONG:
                return setFieldLong(o, f, rs);
            case FLOAT:
                return setFieldFloat(o, f, rs);
            case DOUBLE:
                return setFieldDouble(o, f, rs);
            case JAVA_LANG_STRING:
                return setFieldString(o, f, rs);
        }

        if (isImplementatorOfCollection(f.getType())) {
            int column = rs.findColumn("rt " + f.getName());
            if (column > 0) {
                return loadCollection(o, f, rs, column);
            }
            return o;
        }

        int column = rs.findColumn("fk " + f.getName());
        if (isSubclassOfDataSet(f.getType()) && column > 0) {
            long id = getFK(f, rs);

            //noinspection unchecked
            Class<? extends DataSet> c = (Class<? extends DataSet>) f.getType();
            Adapter adapter = adapters.getOrDefault(
                c.getName(), adapters.get(DEFAULT)
            );

            Loader loader = new Loader(connection);
            Object value = loader.load(
                id, c, resultSet -> {
                    if (resultSet.next()) {
                        return adapter.read(resultSet, TypeToken.of(c), id);
                    } else
                        throw new SQLException("SQL Error!!!");
                }
            );
            f.set(o, value);

            return o;
        }

        throw new NoImplementationException();
    }

    /**
     * The method  create  the object. The method  iterates by  fields from the
     * type  contained in  TypeToken class  and get  for each filed  the value.
     * If  type  of Class  contains  filed's type is  a particular  subclass of
     * DataSet, the loader with createObject call recursively.
     *
     * @param rs the ResultSet from the select query
     * @param tt the TypeToken object
     * @param id the id of the stored object
     * @param <T> the type of the object
     * @return the completely created object
     */
    public <T extends DataSet> T createObject(ResultSet rs, TypeToken<?> tt, long id) {
        //noinspection unchecked
        T result = (T) newInstance(tt.getRawType(), id);

        for (Field field : tt.getRawType().getDeclaredFields()) {
            boolean accessible = field.isAccessible();
            field.setAccessible(true);

            try {
                result = setField(result, field, rs);
            } catch (Throwable e) {
                //noinspection ThrowableNotThrown
                throw new CreateObjectException(e);
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
