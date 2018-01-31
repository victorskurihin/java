package ru.otus.l081;

import com.google.common.reflect.TypeToken;

import javax.json.*;
import java.io.InputStream;
import java.lang.reflect.Type;
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
    public <T> T read(InputStream body, TypeToken<?> tt) throws NoSuchMethodException {

        // Create JsonReader from Json.
        JsonReader reader = Json.createReader(body);
        // Prepare object.
        TypeToken<?> ct = tt.resolveType(
            List.class.getMethod("get", int.class).getGenericReturnType()
        );
        List<Object> list = (List<Object>) newInstance(tt.getRawType());

        // Get the JsonObject structure from JsonReader.
        JsonArray jsonArray = reader.readArray();
        jsonArray.forEach(value -> listAdd(list, ct, value));

        return (T) list;
    }
    /**
     * TODO experimental
     */
    private void listAdd(List<Object> list, TypeToken<?> ct, JsonValue value) {
        switch (value.getValueType()) {
            case ARRAY:
                throw new NoImplementedException();
            case OBJECT:
                throw new NoImplementedException();
            case STRING:
                if (String.class ==  ct.getRawType()) {
                    list.add(((JsonString) value).getString());
                } else if (Character.class == ct.getRawType()) {
                    list.add(((JsonString) value).getChars().charAt(0));
                } else {
                    throw new NoImplementedException();
                }
                break;
            case NUMBER:
                if (Integer.class ==  ct.getRawType()) {
                    list.add(new Integer(value.toString()));
                } else if (Long.class == ct.getRawType()) {
                    list.add(new Long(value.toString()));
                } else {
                    throw new NoImplementedException();
                }
                break;
            case TRUE:
                list.add(Boolean.TRUE);
                break;
            case FALSE:
                list.add(Boolean.FALSE);
                break;
            case NULL:
                list.add(null);
                break;
        }
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF