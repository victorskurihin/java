package ru.otus.l081;

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
        new TreeMapAdapter()
        // new HashMapAdapter()
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
            return adapters.get(aClass.getTypeName()).jsonValue(aClass, o).toString();
        } else switch (aClass.getTypeName()) {
            case "java.lang.Boolean":
                return ((Boolean) o).toString();
            case "java.lang.Character":
                return String.format("\"%c\"", (char) o);
            case "java.lang.Byte":
                return Byte.toString((Byte) o);
            case "java.lang.Short":
                return Short.toString((Short) o);
            case "java.lang.Integer":
                return Integer.toString((Integer) o);
            case "java.lang.Long":
                return Long.toString((Long) o);
            case "java.lang.Float":
                return Float.toString((Float) o);
            case "java.lang.Double":
                return Double.toString((Double) o);
            case "java.lang.String":
                return String.format("\"%s\"", (String) o);
        }

        if (o.getClass().isArray()) {
            return adapters.get(BUILD_IN_ARRAY).jsonValue(aClass, o).toString();
        }

        return adapters.get(DEFAULT).jsonValue(aClass, o).toString();
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
}
