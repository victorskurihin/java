package ru.otus.l081;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Map;

/**
 * Converts Java objects to JSON.
 * This abstract helper class contains adaptee methods for adapters classes.
 */
public abstract class Adapters implements Adapter {
    protected Map<String, Adapter> adapters; // the map of adapters

    @Override
    public void setAdapters(Map<String, Adapter> map) {
        adapters = map;
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
            case "java.lang.Boolean":
                return ab.add((Boolean) o);
            case "java.lang.Character":
                return ab.add(((Character) o).toString());
            case "java.lang.Byte":
                return ab.add((Byte) o);
            case "java.lang.Short":
                return ab.add((Short) o);
            case "java.lang.Integer":
                return ab.add((Integer) o);
            case "java.lang.Long":
                return ab.add((Long) o);
            case "java.lang.Float":
                return ab.add((Float) o);
            case "java.lang.Double":
                return ab.add((Float) o);
            case "java.lang.String":
                return ab.add((String) o);
        }

        Type t = o.getClass();
        String key = adapters.containsKey(t.getTypeName())
                   ? t.getTypeName()
                   : ObjectOutputJson.DEFAULT;
        return ab.add(adapters.get(key).jsonValue(t, o));
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

        if (null == o) { return ob.addNull(key); }

        Class<?> t = o.getClass();

        if (adapters.containsKey(o.getClass().getTypeName())) {
            return ob.add(
                key, adapters.get(t.getTypeName()).jsonValue(t, o)
            );
        } else switch (t.getTypeName()) {
            case "java.lang.Boolean":
                return ob.add(key, (Boolean) o);
            case "java.lang.Character":
                return ob.add(key, (Character) o);
            case "java.lang.Byte":
                return ob.add(key, (Byte) o);
            case "java.lang.Short":
                return ob.add(key, (Short) o);
            case "java.lang.Integer":
                return ob.add(key, (Integer) o);
            case "java.lang.Long":
                return ob.add(key, (Long) o);
            case "java.lang.Float":
                return ob.add(key, (Float) o);
            case "java.lang.Double":
                return ob.add(key, (Double) o);
            case "java.lang.String":
                return ob.add(key, (String) o);
            default:
                String adapter = t.isArray()
                    ? ObjectOutputJson.BUILD_IN_ARRAY
                    : ObjectOutputJson.DEFAULT;
                return ob.add(key, adapters.get(adapter).jsonValue(t, o));
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
            case "boolean":
                return ob.add(f.getName(), f.getBoolean(o));
            case "byte":
                return ob.add(f.getName(), f.getByte(o));
            case "char":
                return ob.add(f.getName(), f.getChar(o));
            case "short":
                return ob.add(f.getName(), f.getShort(o));
            case "int":
                return ob.add(f.getName(), f.getInt(o));
            case "long":
                return ob.add(f.getName(), f.getLong(o));
            case "float":
                return ob.add(f.getName(), f.getFloat(o));
            case "double":
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

        if (null == o || o.getClass().getDeclaredFields().length < 1) {
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

        if (null == o) { return ab; }

        switch (o.getClass().getTypeName()) {
            case "boolean[]":
                for (boolean b : boolean[].class.cast(o)) { ab.add(b); }
                return ab;
            case "char[]":
                for (char c : char[].class.cast(o)) { ab.add(c); }
                return ab;
            case "byte[]":
                for (byte b : byte[].class.cast(o)) { ab.add(b); }
                return ab;
            case "short[]":
                for (short s : short[].class.cast(o)) { ab.add(s); }
                return ab;
            case "int[]":
                Arrays.stream(int[].class.cast(o)).forEach(ab::add);
                return ab;
            case "long[]":
                Arrays.stream(long[].class.cast(o)).forEach(ab::add);
                return ab;
            case "float[]":
                for (float f : float[].class.cast(o)) { ab.add(f); }
                return ab;
            case "double[]":
                Arrays.stream(double[].class.cast(o)).forEach(ab::add);
                return ab;
        }

        for (Object e : Object[].class.cast(o)) {
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

        return ab;
    }
}
