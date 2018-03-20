package ru.otus.l111.dao;

/*
 * Created by VSkurikhin at winter 2018.
 */

import ru.otus.l111.dataset.OtusIgnoreField;
import ru.otus.l111.exeption.NoImplementationException;
import ru.otus.l111.dataset.DataSet;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Arrays;
import java.util.Collection;

public interface FieldMethods {

    default boolean isSubclassOfDataSet(Class<?> c) {
        return DataSet.class.isAssignableFrom(c);
    }

    default boolean isImplementatorOfCollection(Class<?> c) {
        return Collection.class.isAssignableFrom(c);
    }

    /**
     * The helper method for prepare name of the table for storing the object
     * of class.
     *
     * @param c the subclass of the DataSet class
     * @return the name of the table
     */
    default String classGetTableName(Class<?> c) {
        return c.getName().replace('.','_');
    }

    default String tableNameToClassName(String name) {
        return name.replace('_','.');
    }


    default boolean isMyORMFieldIgnore(Field f) {
        return Arrays.stream(f.getAnnotations()).anyMatch(
            a -> a.annotationType().equals(OtusIgnoreField.class)
        );
    }

    default String getTableName(Field f) {
        Class<?> classOfElements = (Class<?>) getFirstParameterType(f);
        Class<?> classOfCollection = f.getType();
        return  "'" + classGetTableName(classOfCollection)
            + ' ' + classGetTableName(classOfElements) + "'";
    }

    default Type getFirstParameterType(Field field) {
        Type type = field.getGenericType();
        if (type instanceof ParameterizedType) {
            ParameterizedType pType = (ParameterizedType) type;
            if (pType.getActualTypeArguments().length > 0) {
                return pType.getActualTypeArguments()[0];
            }
        }
        return null;
    }

    /**
     * The method assigns to the object's field of the boolean from
     * the designated column SQL.
     *
     * @param object the object where located the appropriate field
     * @param field the appropriate field
     * @param rs the result set from sql query
     * @param <T> the generic type of the goal object
     * @return the charged object
     * @throws IllegalAccessException the access to the field
     * @throws SQLException ResultSet, ResultSetMetaData
     */
    default <T> T setFieldBoolean(T object, Field field, ResultSet rs)
        throws IllegalAccessException, SQLException {

        ResultSetMetaData rsmd = rs.getMetaData();
        int columnNumber = rs.findColumn(field.getName());

        switch (rsmd.getColumnType(columnNumber)) {
            case Types.BIT:
            case Types.BOOLEAN:
            field.setBoolean(object , rs.getBoolean(columnNumber));
            return object;
        }

        throw new NoImplementationException();
    }

    /**
     * The method assigns to the object's field of the byte type from
     * the designated column SQL.
     *
     * @param object the object where located the appropriate field
     * @param field the appropriate field
     * @param rs the result set from sql query
     * @param <T> the generic type of the goal object
     * @return the charged object
     * @throws IllegalAccessException the access to the field
     * @throws SQLException ResultSet, ResultSetMetaData
     */
    default <T> T setFieldByte(T object, Field field, ResultSet rs)
        throws IllegalAccessException, SQLException {

        ResultSetMetaData rsmd = rs.getMetaData();
        int columnNumber = rs.findColumn(field.getName());

        switch (rsmd.getColumnType(columnNumber)) {
            case Types.TINYINT:
            case Types.SMALLINT:
                field.setByte(object, rs.getByte(columnNumber));
                return object;
        }

        throw new NoImplementationException();
    }

    /**
     * The method assigns to the object's field of the char type from
     * the designated column SQL.
     *
     * @param object the object where located the appropriate field
     * @param field the appropriate field
     * @param rs the result set from sql query
     * @param <T> the generic type of the goal object
     * @return the charged object
     * @throws IllegalAccessException the access to the field
     * @throws SQLException ResultSet, ResultSetMetaData
     */
    default <T> T setFieldChar(T object, Field field, ResultSet rs)
        throws IllegalAccessException, SQLException {

        ResultSetMetaData rsmd = rs.getMetaData();
        int columnNumber = rs.findColumn(field.getName());

        switch (rsmd.getColumnType(columnNumber)) {
            case Types.CHAR:
            case Types.NCHAR:
            case Types.NVARCHAR:
            case Types.VARCHAR:
                field.setChar(object , rs.getString(columnNumber).charAt(0));
                return object;
        }

        throw new NoImplementationException();
    }

    /**
     * The method assigns to the object's field of the short type from
     * the designated column SQL.
     *
     * @param object the object where located the appropriate field
     * @param field the appropriate field
     * @param rs the result set from sql query
     * @param <T> the generic type of the goal object
     * @return the charged object
     * @throws IllegalAccessException the access to the field
     * @throws SQLException ResultSet, ResultSetMetaData
     */
    default <T> T setFieldShort(T object, Field field, ResultSet rs)
        throws IllegalAccessException, SQLException {

        ResultSetMetaData rsmd = rs.getMetaData();
        int columnNumber = rs.findColumn(field.getName());

        switch (rsmd.getColumnType(columnNumber)) {
            case Types.NUMERIC:
            case Types.SMALLINT:
                field.setShort(object, rs.getShort(columnNumber));
                return object;
        }

        throw new NoImplementationException();
    }

    /**
     * The method assigns to the object's field of the int type from
     * the designated column SQL.
     *
     * @param object the object where located the appropriate field
     * @param field the appropriate field
     * @param rs the result set from sql query
     * @param <T> the generic type of the goal object
     * @return the charged object
     * @throws IllegalAccessException the access to the field
     * @throws SQLException ResultSet, ResultSetMetaData
     */
    default <T> T setFieldInt(T object, Field field, ResultSet rs)
        throws IllegalAccessException, SQLException {

        ResultSetMetaData rsmd = rs.getMetaData();
        int columnNumber = rs.findColumn(field.getName());

        switch (rsmd.getColumnType(columnNumber)) {
            case Types.INTEGER:
            case Types.NUMERIC:
                field.setInt(object, rs.getInt(columnNumber));
                return object;
        }

        throw new NoImplementationException();
    }

    /**
     * The method assigns to the object's field of the long type from
     * the designated column SQL.
     *
     * @param object the object where located the appropriate field
     * @param field the appropriate field
     * @param rs the result set from sql query
     * @param <T> the generic type of the goal object
     * @return the charged object
     * @throws IllegalAccessException the access to the field
     * @throws SQLException ResultSet, ResultSetMetaData
     */
    default <T> T setFieldLong(T object, Field field, ResultSet rs)
        throws IllegalAccessException, SQLException {

        ResultSetMetaData rsmd = rs.getMetaData();
        int columnNumber = rs.findColumn(field.getName());

        switch (rsmd.getColumnType(columnNumber)) {
            case Types.BIGINT:
            case Types.NUMERIC:
                field.setLong(object, rs.getLong(columnNumber));
                return object;
        }

        throw new NoImplementationException();
    }

    /**
     * The method assigns to the object's field of the float type from
     * the designated column SQL.
     *
     * @param object the object where located the appropriate field
     * @param field the appropriate field
     * @param rs the result set from sql query
     * @param <T> the generic type of the goal object
     * @return the charged object
     * @throws IllegalAccessException the access to the field
     * @throws SQLException ResultSet, ResultSetMetaData
     */
    default <T> T setFieldFloat(T object, Field field, ResultSet rs)
        throws IllegalAccessException, SQLException {

        ResultSetMetaData rsmd = rs.getMetaData();
        int columnNumber = rs.findColumn(field.getName());

        switch (rsmd.getColumnType(columnNumber)) {
            case Types.FLOAT:
            case Types.REAL:
                field.setFloat(object, rs.getFloat(columnNumber));
                return object;
        }

        throw new NoImplementationException();
    }

    /**
     * The method assigns to the object's field of the double type from
     * the designated column SQL.
     *
     * @param object the object where located the appropriate field
     * @param field the appropriate field
     * @param rs the result set from sql query
     * @param <T> the generic type of the goal object
     * @return the charged object
     * @throws IllegalAccessException the access to the field
     * @throws SQLException ResultSet, ResultSetMetaData
     */
    default <T> T setFieldDouble(T object, Field field, ResultSet rs)
        throws IllegalAccessException, SQLException {

        ResultSetMetaData rsmd = rs.getMetaData();
        int columnNumber = rs.findColumn(field.getName());

        switch (rsmd.getColumnType(columnNumber)) {
            case Types.DOUBLE:
                field.setDouble(object, rs.getDouble(columnNumber));
                return object;
        }

        throw new NoImplementationException();
    }

    /**
     * The method assigns to the object's field of the String type from
     * the designated column SQL.
     *
     * @param object the object where located the appropriate field
     * @param field the appropriate field
     * @param rs the result set from sql query
     * @param <T> the generic type of the goal object
     * @return the charged object
     * @throws IllegalAccessException the access to the field
     * @throws SQLException ResultSet, ResultSetMetaData
     */
    default <T> T setFieldString(T object, Field field, ResultSet rs)
        throws IllegalAccessException, SQLException {

        ResultSetMetaData rsmd = rs.getMetaData();
        int columnNumber = rs.findColumn(field.getName());

        switch (rsmd.getColumnType(columnNumber)) {
            case Types.CHAR:
            case Types.NCHAR:
            case Types.NVARCHAR:
            case Types.VARCHAR:
                field.set(object, rs.getString(columnNumber));
                return object;
        }

        throw new NoImplementationException();
    }

    /**
     * The method get the value of the primary key (id) of object containing
     * in the field.
     *
     * @param field the filed
     * @param rs the result set from sql query
     * @return the id of the object
     * @throws IllegalAccessException the access to the field
     * @throws SQLException ResultSet, ResultSetMetaData
     */
    default long getFK(Field field, ResultSet rs)
        throws IllegalAccessException, SQLException {

        ResultSetMetaData rsmd = rs.getMetaData();
        int columnNumber = rs.findColumn("fk " + field.getName());

        switch (rsmd.getColumnType(columnNumber)) {
            case Types.BIGINT:
            case Types.NUMERIC:
                return rs.getLong(columnNumber);
        }

        throw new NoImplementationException();
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
