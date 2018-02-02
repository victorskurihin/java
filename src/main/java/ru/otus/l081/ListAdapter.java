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
        List<Object> list = (List<Object>) newInstance(tt.getRawType());
        CollectionWraper collection = new CollectionWraper(this, elementOfContainetType, list);

        // Get the JsonObject structure from JsonReader.
        JsonArray jsonArray = reader.readArray();
        jsonArray.forEach(collection::add);

        return (T) list;
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF