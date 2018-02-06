package ru.otus.l091;

import javax.json.JsonObject;
import javax.json.JsonValue;
import java.lang.reflect.Field;

public interface FieldMethods {

    default <T> T setFieldBoolean(T object, Field field)
        throws IllegalAccessException {

        return object;
    }

    default <T> T setFieldByte(T object, Field field)
        throws IllegalAccessException {

        return object;
    }

    default <T> T setFieldChar(T object, Field field)
        throws IllegalAccessException {

        return object;
    }

    default <T> T setFieldShort(T object, Field field)
        throws IllegalAccessException {

        return object;
    }

    default <T> T setFieldInt(T object, Field field)
        throws IllegalAccessException {

        return object;
    }

    default <T> T setFieldLong(T object, Field field)
        throws IllegalAccessException {

        return object;
    }

    default <T> T setFieldFloat(T object, Field field)
        throws IllegalAccessException {

        return object;
    }

    default <T> T setFieldDouble(T object, Field field)
        throws IllegalAccessException {

        return object;
    }

    default <T> T setFieldString(T object, Field field)
        throws IllegalAccessException {

        return object;
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
