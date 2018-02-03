package ru.otus.l081;

import com.google.common.reflect.TypeToken;

import javax.json.*;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Adapt a List collection of objects.
 */
public class ListAdapter extends Adapters implements Adapter {
    private final String ADAPTEE_TYPE = "java.util.List";

    public String getAdapteeOfType() {
        return ADAPTEE_TYPE;
    }

    /**
     * @param aClass
     * @param o
     * @return
     * @throws IllegalAccessException
     */
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
     * @param value
     * @param tt
     * @param <T>
     * @return
     * @throws NoSuchMethodException
     */
    @Override
    public <T> T read(final JsonValue value, TypeToken<?> tt) {
        // Prepare object.
        TypeToken<?> elementOfContainetType = null;
        try {
            elementOfContainetType = tt.resolveType(
                List.class.getMethod("get", int.class).getGenericReturnType()
            );
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }

        //noinspection unchecked
        List<Object> list = (List<Object>) newInstance(tt.getRawType());
        CollectionWrapper collection = new CollectionWrapper(
            this, elementOfContainetType, list
        );

        // Get the JsonArray structure from JsonValue.
        JsonArray jsonArray = (JsonArray) value;
        jsonArray.forEach(collection::add);

        //noinspection unchecked
        return (T) list;
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF