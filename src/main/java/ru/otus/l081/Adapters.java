package ru.otus.l081;

import com.google.common.reflect.TypeToken;

import javax.json.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Converts Java objects to JSON.
 * This abstract helper class  contains adaptee methods  for adapters  classes.
 * If default  JSON conversion isn't appropriate for a type, extend  this class
 * to customize the conversion.
 */
public class Adapters implements TypeNames, FieldMethods {

    protected Map<String, Adapter> adapters; // the map of adapters
    protected Set<Object> visited = new HashSet<>();

    public void setAdapters(Map<String, Adapter> map) {
        adapters = map;
    }

    public void setVisited(Set<Object> visited) {
        this.visited = visited;
    }

    private boolean loopDetected(Object o) {
        if (visited.contains(o)) { return true; }
        visited.add(o);
        return false;
    }

    /**
     * Adds the specified object to JSON array builder. If the specified object
     * is not standart boxing object this method  search  suitable  adapter and
     * delegate to this adapter control or delegate to the default adapter.
     *
     * @param ab JSON array builder
     * @param o the specified object
     * @return JSON array builder
     * @throws IllegalAccessException
     */
    JsonArrayBuilder addToJsonArray(JsonArrayBuilder ab, Object  o)
        throws IllegalAccessException {

        if (null == o) { return ab; }

        switch (o.getClass().getTypeName()) {
            case JAVA_LANG_BOOLEAN:
                return ab.add((Boolean) o);
            case JAVA_LANG_CHARACTER:
                return ab.add(((Character) o).toString());
            case JAVA_LANG_BYTE:
                return ab.add((Byte) o);
            case JAVA_LANG_SHORT:
                return ab.add((Short) o);
            case JAVA_LANG_INTEGER:
                return ab.add((Integer) o);
            case JAVA_LANG_LONG:
                return ab.add((Long) o);
            case JAVA_LANG_FLOAT:
                return ab.add((Float) o);
            case JAVA_LANG_DOUBLE:
                return ab.add((Float) o);
            case JAVA_LANG_STRING:
                return ab.add((String) o);
        }

        Type t = o.getClass();
        String key = adapters.containsKey(t.getTypeName())
                   ? t.getTypeName()
                   : ObjectOutputJson.DEFAULT;
        return ab.add(adapters.get(key).write(t, o));
    }

    /**
     * Adds a member, which is a key-value pair, to the JSON object builder.
     * The key must be a String.
     *
     * @param ob the JSON object builder
     * @param key the key
     * @param o the object of value
     * @return the JSON object builder
     * @throws IllegalAccessException
     */
    JsonObjectBuilder jsonKeyValue(JsonObjectBuilder ob, String key, Object o)
        throws IllegalAccessException {

        if (null == o) { return ob; } // Gson ??? { return ob.addNull(key); }

        Class<?> t = o.getClass();

        if (adapters.containsKey(o.getClass().getTypeName())) {
            return ob.add(
                key, adapters.get(t.getTypeName()).write(t, o)
            );
        } else switch (t.getTypeName()) {
            case JAVA_LANG_BOOLEAN:
                return ob.add(key, (Boolean) o);
            case JAVA_LANG_CHARACTER:
                return ob.add(key, (Character) o);
            case JAVA_LANG_BYTE:
                return ob.add(key, (Byte) o);
            case JAVA_LANG_SHORT:
                return ob.add(key, (Short) o);
            case JAVA_LANG_INTEGER:
                return ob.add(key, (Integer) o);
            case JAVA_LANG_LONG:
                return ob.add(key, (Long) o);
            case JAVA_LANG_FLOAT:
                return ob.add(key, (Float) o);
            case JAVA_LANG_DOUBLE:
                return ob.add(key, (Double) o);
            case JAVA_LANG_STRING:
                return ob.add(key, (String) o);
            default:
                String adapter = t.isArray()
                    ? ObjectOutputJson.BUILD_IN_ARRAY
                    : ObjectOutputJson.DEFAULT;
                return ob.add(key, adapters.get(adapter).write(t, o));
        }
    }

    /**
     * This method works with the any of the object fields are of buildin type,
     * just the object itself should not be of a buildin or Boxing type.
     * If the object is of generic type, use it.
     *
     * @param o the object
     * @param f the field in the object o
     * @return JSON object builder
     * @throws IllegalAccessException
     */
    JsonObjectBuilder addFiled(JsonObjectBuilder ob, Object o, Field f)
        throws IllegalAccessException {

        if (null == o) { return ob; }

        switch (f.getType().getName()) {
            case BOOLEAN:
                return ob.add(f.getName(), f.getBoolean(o));
            case BYTE:
                return ob.add(f.getName(), f.getByte(o));
            case CHAR:
                return ob.add(f.getName(), String.valueOf(f.getChar(o)));
            case SHORT:
                return ob.add(f.getName(), f.getShort(o));
            case INT:
                return ob.add(f.getName(), f.getInt(o));
            case LONG:
                return ob.add(f.getName(), f.getLong(o));
            case FLOAT:
                return ob.add(f.getName(), f.getFloat(o));
            case DOUBLE:
                return ob.add(f.getName(), f.getDouble(o));
        }

        if (f.getType().isArray()) {
            JsonArrayBuilder jab = Json.createArrayBuilder();
            return ob.add(f.getName(), jsonArray(jab, f.get(o)));
        }

        return jsonKeyValue(ob, f.getName(), f.get(o));
    }

    /**
     * This method explores the object and all its fields.
     *
     * @param ob JSON object builder
     * @param o the object
     * @return JSON object builder
     * @throws IllegalAccessException
     */
    JsonObjectBuilder jsonObject(JsonObjectBuilder ob, Object o)
        throws IllegalAccessException {

        if (null == o || loopDetected(o) ||
            o.getClass().getDeclaredFields().length < 1) {
            return ob;
        }

        for (Field field : o.getClass().getDeclaredFields()) {
            boolean accessible = field.isAccessible();
            field.setAccessible(true);

            try {
                ob = addFiled(ob, o, field);
            } catch (Throwable e) {
                throw e;
            } finally {
                field.setAccessible(accessible);
            }
        }
        visited.remove(o);

        return ob;
    }

    /**
     * Adapt an array of objects.
     * This method explores the array and all its objects.
     *
     * @param ab the JSON array builder
     * @param o the array
     * @return the JSON array builder
     * @throws IllegalAccessException
     */
    JsonArrayBuilder jsonArray(JsonArrayBuilder ab, Object o)
        throws IllegalAccessException {

        if (null == o || loopDetected(o)) { return ab; }

        switch (o.getClass().getTypeName()) {
            case BOOLEAN_ARRAY:
                for (boolean b : boolean[].class.cast(o)) { ab.add(b); }
                break;
            case CHAR_ARRAY:
                for (char c : char[].class.cast(o)) { ab.add(c); }
                break;
            case BYTE_ARRAY:
                for (byte b : byte[].class.cast(o)) { ab.add(b); }
                break;
            case SHORT_ARRAY:
                for (short s : short[].class.cast(o)) { ab.add(s); }
                break;
            case INT_ARRAY:
                Arrays.stream(int[].class.cast(o)).forEach(ab::add);
                break;
            case LONG_ARRAY:
                Arrays.stream(long[].class.cast(o)).forEach(ab::add);
                break;
            case FLOAT_ARRAY:
                for (float f : float[].class.cast(o)) { ab.add(f); }
                break;
            case DOUBLE_ARRAY:
                Arrays.stream(double[].class.cast(o)).forEach(ab::add);
                break;
            default: for (Object e : Object[].class.cast(o)) {
                if (null == e) {
                    ab.addNull();
                } else if (e.getClass().isArray()) {
                    JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
                    ab.add(jsonArray(jsonArrayBuilder, e));
                } else {
                    JsonObjectBuilder ob = Json.createObjectBuilder();
                    ab.add(jsonObject(ob, e));
                }
            }
        }
        visited.remove(o);

        return ab;
    }

    /**
     * The method create instance of test class.
     *
     * @param runClass - the class
     * @return - the object of test class
     */
    static <T> Object newInstance(Class<T> runClass) {
        //noinspection TryWithIdenticalCatches
        try {
            return runClass.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    private <T> T setField(T object, Field field, JsonValue value)
        throws IllegalAccessException {

        //noinspection unchecked
        JsonObject jsonObject = (JsonObject) value;

        switch (field.getType().getTypeName()) {
            case BOOLEAN:
                return setFieldBoolean(object, field, jsonObject);
            case BYTE:
                return setFieldByte(object, field, jsonObject);
            case CHAR:
                return setFieldChar(object, field, jsonObject);
            case SHORT:
                return setFieldShort(object, field, jsonObject);
            case INT:
                return setFieldInt(object, field, jsonObject);
            case LONG:
                return setFieldLong(object, field, jsonObject);
            case FLOAT:
                return setFieldFloat(object, field, jsonObject);
            case DOUBLE:
                return setFieldDouble(object, field, jsonObject);
            case JAVA_LANG_STRING:
                return setFieldString(object, field, jsonObject);
        }

        TypeToken<?> tt = TypeToken.of(field.getGenericType());

        String key = adapters.containsKey(field.getType().getTypeName())
                   ? field.getType().getTypeName()
                   : ( tt.isArray()
                       ? ObjectOutputJson.BUILD_IN_ARRAY
                       : ObjectOutputJson.DEFAULT
                   );

        field.set(
            object, adapters.get(key).read(jsonObject.get(field.getName()), tt)
        );

        return object;
    }

    <T> T createObject(JsonValue value, TypeToken<?> tt) {
        //noinspection unchecked
        T result = (T) newInstance(tt.getRawType());

        for (Field field : tt.getRawType().getDeclaredFields()) {
            boolean accessible = field.isAccessible();
            field.setAccessible(true);

            try {
                result = setField(result, field, value);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
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