package ru.otus.l091;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;

public interface FieldMethods {

    default <T> T setFieldBoolean(T object, Field field, ResultSet rs)
        throws IllegalAccessException, SQLException {

        ResultSetMetaData rsmd = rs.getMetaData();
        int columnNumber = rs.findColumn(field.getName());
        System.out.println("rs.get(" + field.getName() + " = " + rs.getBoolean(columnNumber));
        System.out.println("rsmd = " + rsmd);

        if (Types.BOOLEAN == rsmd.getColumnType(columnNumber)) {
            field.setBoolean(object , rs.getBoolean(columnNumber));
            return object;
        }

        throw new NoImplementationException();
    }

    default <T> T setFieldByte(T object, Field field, ResultSet rs)
        throws IllegalAccessException, SQLException {

        ResultSetMetaData rsmd = rs.getMetaData();
        int columnNumber = rs.findColumn(field.getName());
        System.out.println("rs.get(" + field.getName() + " = " + rs.getByte(columnNumber));
        System.out.println("rsmd = " + rsmd);

        switch (rsmd.getColumnType(columnNumber)) {
            case Types.TINYINT:
            case Types.SMALLINT:
                field.setByte(object, rs.getByte(columnNumber));
                return object;
        }

        throw new NoImplementationException();
    }

    default <T> T setFieldChar(T object, Field field, ResultSet rs)
        throws IllegalAccessException, SQLException {

        ResultSetMetaData rsmd = rs.getMetaData();
        int columnNumber = rs.findColumn(field.getName());
        System.out.println("rs.get(" + field.getName() + " = " + rs.getString(columnNumber));
        System.out.println("rsmd = " + rsmd);

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

    default <T> T setFieldShort(T object, Field field, ResultSet rs)
        throws IllegalAccessException {

        return object;
    }

    default <T> T setFieldInt(T object, Field field, ResultSet rs)
        throws IllegalAccessException {

        return object;
    }

    default <T> T setFieldLong(T object, Field field, ResultSet rs)
        throws IllegalAccessException {

        return object;
    }

    default <T> T setFieldFloat(T object, Field field, ResultSet rs)
        throws IllegalAccessException {

        return object;
    }

    default <T> T setFieldDouble(T object, Field field, ResultSet rs)
        throws IllegalAccessException {

        return object;
    }

    default <T> T setFieldString(T object, Field field, ResultSet rs)
        throws IllegalAccessException {

        return object;
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
