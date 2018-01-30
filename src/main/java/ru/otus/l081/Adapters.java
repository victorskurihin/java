package ru.otus.l081;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
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
public abstract class Adapters implements Adapter {
    public static final String JAVA_LANG_BOOLEAN = "java.lang.Boolean";
    public static final String JAVA_LANG_CHARACTER = "java.lang.Character";
    public static final String JAVA_LANG_BYTE = "java.lang.Byte";
    public static final String JAVA_LANG_SHORT = "java.lang.Short";
    public static final String JAVA_LANG_INTEGER = "java.lang.Integer";
    public static final String JAVA_LANG_LONG = "java.lang.Long";
    public static final String JAVA_LANG_FLOAT = "java.lang.Float";
    public static final String JAVA_LANG_DOUBLE = "java.lang.Double";
    public static final String JAVA_LANG_STRING = "java.lang.String";
    public static final String BOOLEAN = "boolean";
    public static final String BYTE = "byte";
    public static final String CHAR = "char";
    public static final String SHORT = "short";
    public static final String INT = "int";
    public static final String LONG = "long";
    public static final String FLOAT = "float";
    public static final String DOUBLE = "double";
    public static final String BOOLEAN_ARRAY = "boolean[]";
    public static final String CHAR_ARRAY = "char[]";
    public static final String BYTE_ARRAY = "byte[]";
    public static final String SHORT_ARRAY = "short[]";
    public static final String INT_ARRAY = "int[]";
    public static final String LONG_ARRAY = "long[]";
    public static final String FLOAT_ARRAY = "float[]";
    public static final String DOUBLE_ARRAY = "double[]";

    protected Map<String, Adapter> adapters; // the map of adapters
    private Set<Object> visited = new HashSet<>();

    @Override
    public void setAdapters(Map<String, Adapter> map) {
        adapters = map;
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

        if (null == o) { return ob.addNull(key); }

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
                return ob.add(f.getName(), f.getChar(o));
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
     * @param runClass - the class
     * @return - the object of test class
     */
    static <T> Object newInstance(Class<T> runClass) {
        try {
            return runClass.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
