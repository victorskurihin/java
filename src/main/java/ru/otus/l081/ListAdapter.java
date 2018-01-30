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

    @Override
    public <T> T read(InputStream body, TypeToken<T> tt) {
        // Create JsonReader from Json.
        JsonReader reader = Json.createReader(body);
        // Prepare object.
//        List<Object> list = (List<Object>) newInstance();
        // Get the JsonObject structure from JsonReader.
//        JsonArray jsonArray = reader.readArray();
//        jsonArray.forEach(list::add);

        return null;
    }
}
