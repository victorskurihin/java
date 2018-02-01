package ru.otus.l081;

import com.google.common.reflect.TypeToken;

import javax.json.*;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.Set;

/**
 * Adapt a Set collection of objects.
 */
public class SetAdapter extends Adapters implements Adapter {
    private final String ADAPTEE_TYPE = "java.util.Set";

    @Override
    public String getAdapteeOfType() {
        return ADAPTEE_TYPE;
    }

    @Override
    public JsonValue write(Type aClass, Object o) throws IllegalAccessException {
        JsonArrayBuilder jab = Json.createArrayBuilder();
        //noinspection unchecked
        for (Object e : (Set<Object>) o) {
            jab = addToJsonArray(jab, e);
        }
        return jab.build();
    }

    @Override
    public <T> T read(final InputStream body, TypeToken<?> tt) {
        throw new NoImplementedException();
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF