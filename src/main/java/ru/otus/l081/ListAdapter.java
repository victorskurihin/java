package ru.otus.l081;

import com.google.common.reflect.TypeToken;

import javax.json.*;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.math.BigInteger;
import java.util.Collection;
import java.util.List;

/**
 * Adapt a List collection of objects.
 */
public class ListAdapter extends Adapters implements Adapter {
    private final String ADAPTEE_TYPE = "java.util.List";

    @Override
    public String getAdapteeOfType() {
        return ADAPTEE_TYPE;
    }

    @Override
    public JsonValue write(Type aClass, Object o) throws IllegalAccessException {
        JsonArrayBuilder jab = Json.createArrayBuilder();

        //noinspection unchecked
        for (Object e : (List<Object>) o) {
            if (null == e) {
                jab.addNull();
            } else {
                jab = addToJsonArray(jab, e);
            }
        }

        return jab.build();
    }

    /**
     * TODO experimental
     */
    @SuppressWarnings("unchecked")
    @Override
    public <T> T read(final InputStream body, TypeToken<?> tt) throws NoSuchMethodException {

        // Create JsonReader from Json.
        JsonReader reader = Json.createReader(body);
        // Prepare object.
        TypeToken<?> elementOfContainetType = tt.resolveType(
            List.class.getMethod("get", int.class).getGenericReturnType()
        );
//        TypeToken<?> elementOfContainetType2 = tt.resolveType(
//            List.class.getMethod("add", int.class).getGenericParameterTypes()[0]
//        );
        List<Object> list = (List<Object>) newInstance(tt.getRawType());

        // Get the JsonObject structure from JsonReader.
        JsonArray jsonArray = reader.readArray();
        jsonArray.forEach(value -> addToCollection(list, elementOfContainetType, value));

        return (T) list;
    }
    /**
     * TODO experimental
     */
    private void addNumberToCollection(Collection<Object> c, Class<?> type, String s) {
        if (type == BigInteger.class) {
            c.add(new BigInteger(s));
        } else if (type == Double.class) {
            c.add(new Double(s));
        } else if (type == Float.class) {
            c.add(new Float(s));
        } else if (type == Long.class) {
            c.add(new Long(s));
        } else if (type == Integer.class) {
            c.add(new Integer(s));
        } else if (type == Short.class) {
            c.add(new Short(s));
        } else if (type == Byte.class) {
            c.add(new Byte(s));
        } else
            throw new NoImplementedException();
    }
    /**
     * TODO experimental
     */
    private void addStringToCollection(Collection<Object> c, Class<?> type, String s) {
        if (type == Character.class) {
            c.add(s.charAt(0));
        } else {
            c.add(s);
        }
    }
    /**
     * TODO experimental
     */
    private void addToCollection(Collection<Object> c, TypeToken<?> type, JsonValue value) {
        switch (value.getValueType()) {
            case ARRAY:
                if (type.isArray()) {

                }
            case OBJECT:
                throw new NoImplementedException();
            case STRING:
                addStringToCollection(c, type.getRawType(), value.toString());
                break;
            case NUMBER:
                addNumberToCollection(c, type.getRawType(), value.toString());
            case TRUE:
                c.add(Boolean.TRUE);
                break;
            case FALSE:
                c.add(Boolean.FALSE);
                break;
            case NULL:
                c.add(null);
        }
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF