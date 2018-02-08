package ru.otus.l091;

import javax.json.JsonObject;
import javax.json.JsonValue;
import java.lang.reflect.Field;

/*
    setFieldBoolean = -7 => java.sql.Types.BIT
    rs.get(f0) = true

    setFieldByte = 5     => java.sql.Types.SMALLINT
    rs.get(f1) = 1

    setFieldChar = 1     => java.sql.Types.CHAR
    rs.get(f2) = f

    setFieldShort = 5    => java.sql.Types.SMALLINT
    rs.get(f3) = 3

    setFieldInt = 4      => java.sql.Types.INTEGER
    rs.get(f4) = 4

    setFieldLong = -5    => java.sql.Types.BIGINT
    rs.get(f5) = 5

    setFieldFloat = 7    => java.sql.Types.REAL
    rs.get(f6) = 6.6

    setFieldDouble = 8   => java.sql.Types.DOUBLE
    rs.get(f7) = 7.7

    setFieldString = 12  => java.sql.Types.VARCHAR
    rs.get(f8) = f8
*/

public interface JsonFieldMethods {

    default JsonValue.ValueType getValueType(Field f, JsonObject jo) {
        return jo.get(f.getName()).getValueType();
    }

    default void checkFieldNumber(Field field, JsonObject jsonObject) {
        switch (getValueType(field, jsonObject)) {
            case ARRAY:
            case OBJECT:
            case STRING:
            case TRUE:
            case FALSE:
            case NULL:
                throw new RuntimeException(
                    "Eggog!! field( " + field.getType().toString() +
                        " " + field.getName() +
                        " ) but JSON value type: " +
                        getValueType(field, jsonObject).toString()
                );
        }
    }

    default <T> T setFieldBoolean(T object, Field field, JsonObject jsonObject)
        throws IllegalAccessException {

        switch (getValueType(field, jsonObject)) {
            case ARRAY:
            case OBJECT:
            case NUMBER:
            case STRING:
            case NULL:
                throw new RuntimeException(
                    "Eggog!! field( " + field.getType().toString() +
                        " " + field.getName() +
                        " ) but JSON value type: " +
                        getValueType(field, jsonObject).toString()
                );
        }
        field.setBoolean(object , jsonObject.getBoolean(field.getName()));

        return object;
    }

    default <T> T setFieldByte(T object, Field field, JsonObject jsonObject)
        throws IllegalAccessException {

        checkFieldNumber(field, jsonObject);
        field.setByte(object, (byte) jsonObject.getInt(field.getName()));

        return object;
    }

    default <T> T setFieldChar(T object, Field field, JsonObject jsonObject)
        throws IllegalAccessException {

        switch (getValueType(field, jsonObject)) {
            case ARRAY:
            case OBJECT:
            case NUMBER:
            case TRUE:
            case FALSE:
            case NULL:
                throw new RuntimeException(
                    "Eggog!! field( " + field.getType().toString() +
                        " " + field.getName() +
                        " ) but JSON value type: " +
                        getValueType(field, jsonObject).toString()
                );
        }
        field.setChar(object, jsonObject.getString(field.getName()).charAt(0));

        return object;
    }

    default <T> T setFieldShort(T object, Field field, JsonObject jsonObject)
        throws IllegalAccessException {

        checkFieldNumber(field, jsonObject);
        field.setShort(object, (short) jsonObject.getInt(field.getName()));

        return object;
    }

    default <T> T setFieldInt(T object, Field field, JsonObject jsonObject)
        throws IllegalAccessException {

        checkFieldNumber(field, jsonObject);
        field.setInt(object, jsonObject.getInt(field.getName()));

        return object;
    }

    default <T> T setFieldLong(T object, Field field, JsonObject jsonObject)
        throws IllegalAccessException {

        checkFieldNumber(field, jsonObject);
        field.setLong(
            object, jsonObject.getJsonNumber(field.getName()).longValue()
        );

        return object;
    }

    default <T> T setFieldFloat(T object, Field field, JsonObject jsonObject)
        throws IllegalAccessException {

        checkFieldNumber(field, jsonObject);
        field.setFloat(object,
            (float) jsonObject.getJsonNumber(field.getName()).doubleValue()
        );

        return object;
    }

    default <T> T setFieldDouble(T object, Field field, JsonObject jsonObject)
        throws IllegalAccessException {

        checkFieldNumber(field, jsonObject);
        field.setDouble(object,
            jsonObject.getJsonNumber(field.getName()).doubleValue()
        );

        return object;
    }

    default <T> T setFieldString(T object, Field field, JsonObject jsonObject)
        throws IllegalAccessException {

        switch (getValueType(field, jsonObject)) {
            case ARRAY:
            case OBJECT:
            case NUMBER:
            case TRUE:
            case FALSE:
                throw new RuntimeException(
                    "Eggog!! field( " + field.getType().toString() +
                        " " + field.getName() +
                        " ) but JSON value type: " +
                        getValueType(field, jsonObject).toString()
                );
            case NULL:
                field.set(object, null);
                break;
            default:
                field.set(object, (Object)jsonObject.getString(field.getName()));
        }

        return object;
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
