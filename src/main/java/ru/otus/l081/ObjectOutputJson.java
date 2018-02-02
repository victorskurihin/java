package ru.otus.l081;

import com.google.common.reflect.TypeToken;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.*;

/**
 * Converts Java objects to JSON.
 * By default this class converts application classes to JSON using its type
 * adapters.
 */
public class ObjectOutputJson {
    public static final String DEFAULT = "__DEFAULT__";
    public static final String BUILD_IN_ARRAY = "__BUILD_IN_ARRAY__";

    Map<String, Adapter> adapters = new HashMap<>();
    private final Adapter[] predefinedAdapters = {
        new DefaultAdapter(),
        new BuildinArrayAdapter(),
        new ListAdapter(),
        new LinkedListAdapter(),
        new ArrayListAdapter(),
        new SetAdapter(),
        new TreeSetAdapter(),
        new HashSetAdapter(),
        new MapAdapter(),
        new TreeMapAdapter(),
        new HashMapAdapter()
    };

    /**
     * Default constructor add predefined adapters.
     */
    public ObjectOutputJson() {
        for (Adapter a : predefinedAdapters) {
            adapters.put(a.getAdapteeOfType(), a);
            a.setAdapters(adapters);
        }
    }

    /**
     * This constructor call default constructor and put additional
     * atapters.
     *
     * @param adapters addional adapters
     */
    public ObjectOutputJson(Adapter ... adapters) {
        this();
        for (Adapter a : adapters) {
            if (! this.adapters.containsKey(a.getAdapteeOfType())) {
                this.adapters.put(a.getAdapteeOfType(), a);
                a.setAdapters(this.adapters);
            }
        }
    }

    /**
     * This method serializes the specified object, including those  of generic
     * types, into its equivalent representation as String. This method must be
     * used if the specified object is a generic type.
     *
     * @param aClass the specific genericized type of o
     * @param o the object for which Json representation is to be created
     * @return Json representation of o
     * @throws IllegalAccessException
     */
    public String toJson(Type aClass, Object o) throws IllegalAccessException {
        if (adapters.containsKey(aClass.getTypeName())) {
            return adapters.get(aClass.getTypeName())
                .write(aClass, o).toString();
        } else switch (aClass.getTypeName()) {
            case Adapters.JAVA_LANG_BOOLEAN:
                return ((Boolean) o).toString();
            case Adapters.JAVA_LANG_CHARACTER:
                return String.format("\"%c\"", (char) o);
            case Adapters.JAVA_LANG_BYTE:
                return Byte.toString((Byte) o);
            case Adapters.JAVA_LANG_SHORT:
                return Short.toString((Short) o);
            case Adapters.JAVA_LANG_INTEGER:
                return Integer.toString((Integer) o);
            case Adapters.JAVA_LANG_LONG:
                return Long.toString((Long) o);
            case Adapters.JAVA_LANG_FLOAT:
                return Float.toString((Float) o);
            case Adapters.JAVA_LANG_DOUBLE:
                return Double.toString((Double) o);
            case Adapters.JAVA_LANG_STRING:
                return String.format("\"%s\"", (String) o);
        }

        if (o.getClass().isArray()) {
            return adapters.get(BUILD_IN_ARRAY).write(aClass, o).toString();
        }

        return adapters.get(DEFAULT).write(aClass, o).toString();
    }

    /***
     * This method  serializes  the specified object  into its equivalent  Json
     * representation.
     *
     * @param o the object for which Json representation is to be created setting
     * @return Json representation of o.
     * @throws IllegalAccessException
     */
    public String toJson(Object o) throws IllegalAccessException {
        return o != null
               ? toJson(o.getClass(), o)
               : "null";
    }

    /**
     * TODO experimental
     */
    public <T> T fromJson(String src, TypeToken<?> tt)
        throws UnsupportedEncodingException, NoSuchMethodException {

        byte[] bytes = src.getBytes("UTF-8");
        ByteArrayInputStream is = new ByteArrayInputStream(bytes);

        if (adapters.containsKey(tt.getRawType().getName())) {
            return adapters.get(tt.getRawType().getTypeName()).read(is, tt);
        }

        return adapters.get(DEFAULT).read(is, tt);
    }

}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF