package ru.otus.l091;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;

public interface FieldMethods {

    default boolean isSubclassOfDataSet(Class<?> c) {
        return DataSet.class.isAssignableFrom(c);
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
     * @throws IllegalAccessException
     * @throws SQLException
     */
    default <T> T setFieldBoolean(T object, Field field, ResultSet rs)
        throws IllegalAccessException, SQLException {

        ResultSetMetaData rsmd = rs.getMetaData();
        int columnNumber = rs.findColumn(field.getName());

        System.err.println("setFieldBoolean = " +  + rsmd.getColumnType(columnNumber));

        switch (rsmd.getColumnType(columnNumber)) {
            case Types.BIT:
            case Types.BOOLEAN:
            System.err.println("rs.get(" + field.getName() + ") = " + rs.getBoolean(columnNumber));
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
     * @throws IllegalAccessException
     * @throws SQLException
     */
    default <T> T setFieldByte(T object, Field field, ResultSet rs)
        throws IllegalAccessException, SQLException {

        ResultSetMetaData rsmd = rs.getMetaData();
        int columnNumber = rs.findColumn(field.getName());
        System.err.println("setFieldByte = " +  + rsmd.getColumnType(columnNumber));

        switch (rsmd.getColumnType(columnNumber)) {
            case Types.TINYINT:
            case Types.SMALLINT:
                System.err.println("rs.get(" + field.getName() + ") = " + rs.getByte(columnNumber));
                field.setByte(object, rs.getByte(columnNumber));
                System.err.println("rs.set(" + field.getName() + ") = " + rs.getByte(columnNumber));
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
     * @throws IllegalAccessException
     * @throws SQLException
     */
    default <T> T setFieldChar(T object, Field field, ResultSet rs)
        throws IllegalAccessException, SQLException {

        ResultSetMetaData rsmd = rs.getMetaData();
        int columnNumber = rs.findColumn(field.getName());
        System.err.println("setFieldChar = " +  + rsmd.getColumnType(columnNumber));

        switch (rsmd.getColumnType(columnNumber)) {
            case Types.CHAR:
            case Types.NCHAR:
            case Types.NVARCHAR:
            case Types.VARCHAR:
                System.err.println("rs.get(" + field.getName() + ") = " + rs.getString(columnNumber));
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
     * @throws IllegalAccessException
     * @throws SQLException
     */
    default <T> T setFieldShort(T object, Field field, ResultSet rs)
        throws IllegalAccessException, SQLException {

        ResultSetMetaData rsmd = rs.getMetaData();
        int columnNumber = rs.findColumn(field.getName());
        System.err.println("setFieldShort = " +  + rsmd.getColumnType(columnNumber));

        switch (rsmd.getColumnType(columnNumber)) {
            case Types.NUMERIC:
            case Types.SMALLINT:
                System.err.println("rs.get(" + field.getName() + ") = " + rs.getShort(columnNumber));
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
     * @throws IllegalAccessException
     * @throws SQLException
     */
    default <T> T setFieldInt(T object, Field field, ResultSet rs)
        throws IllegalAccessException, SQLException {

        ResultSetMetaData rsmd = rs.getMetaData();
        int columnNumber = rs.findColumn(field.getName());
        System.err.println("setFieldInt = " +  + rsmd.getColumnType(columnNumber));

        switch (rsmd.getColumnType(columnNumber)) {
            case Types.INTEGER:
            case Types.NUMERIC:
                System.err.println("rs.get(" + field.getName() + ") = " + rs.getInt(columnNumber));
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
     * @throws IllegalAccessException
     * @throws SQLException
     */
    default <T> T setFieldLong(T object, Field field, ResultSet rs)
        throws IllegalAccessException, SQLException {

        ResultSetMetaData rsmd = rs.getMetaData();
        int columnNumber = rs.findColumn(field.getName());
        System.err.println("setFieldLong = " +  + rsmd.getColumnType(columnNumber));

        switch (rsmd.getColumnType(columnNumber)) {
            case Types.BIGINT:
            case Types.NUMERIC:
                System.err.println("rs.get(" + field.getName() + ") = " + rs.getLong(columnNumber));
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
     * @throws IllegalAccessException
     * @throws SQLException
     */
    default <T> T setFieldFloat(T object, Field field, ResultSet rs)
        throws IllegalAccessException, SQLException {

        ResultSetMetaData rsmd = rs.getMetaData();
        int columnNumber = rs.findColumn(field.getName());
        System.err.println("setFieldFloat = " +  + rsmd.getColumnType(columnNumber));

        switch (rsmd.getColumnType(columnNumber)) {
            case Types.FLOAT:
            case Types.REAL:
                System.err.println("rs.get(" + field.getName() + ") = " + rs.getFloat(columnNumber));
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
     * @throws IllegalAccessException
     * @throws SQLException
     */
    default <T> T setFieldDouble(T object, Field field, ResultSet rs)
        throws IllegalAccessException, SQLException {

        ResultSetMetaData rsmd = rs.getMetaData();
        int columnNumber = rs.findColumn(field.getName());
        System.err.println("setFieldDouble = " +  + rsmd.getColumnType(columnNumber));

        switch (rsmd.getColumnType(columnNumber)) {
            case Types.DOUBLE:
                System.err.println("rs.get(" + field.getName() + ") = " + rs.getDouble(columnNumber));
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
     * @throws IllegalAccessException
     * @throws SQLException
     */
    default <T> T setFieldString(T object, Field field, ResultSet rs)
        throws IllegalAccessException, SQLException {

        ResultSetMetaData rsmd = rs.getMetaData();
        int columnNumber = rs.findColumn(field.getName());
        System.err.println("setFieldString = " +  + rsmd.getColumnType(columnNumber));

        switch (rsmd.getColumnType(columnNumber)) {
            case Types.CHAR:
            case Types.NCHAR:
            case Types.NVARCHAR:
            case Types.VARCHAR:
                System.err.println("rs.get(" + field.getName() + ") = " + rs.getString(columnNumber));
                field.set(object, rs.getString(columnNumber));
                return object;
        }

        throw new NoImplementationException();
    }

    /**
     * The method get the value of the primary key (id) of object containing
     * in the field.
     *
     * @param field
     * @param rs the result set from sql query
     * @return the id of the object
     * @throws IllegalAccessException
     * @throws SQLException
     */
    default long getFK(Field field, ResultSet rs)
        throws IllegalAccessException, SQLException {

        ResultSetMetaData rsmd = rs.getMetaData();
        int columnNumber = rs.findColumn("fk " + field.getName());
        System.err.println("getFK = " +  + rsmd.getColumnType(columnNumber));

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
