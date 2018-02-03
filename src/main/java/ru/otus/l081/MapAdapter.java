package ru.otus.l081;

import com.google.common.reflect.TypeToken;

import javax.json.*;
import java.lang.reflect.Type;
import java.util.Map;

/**
 * Adapt a Map collection of objects.
 */
public class MapAdapter extends Adapters implements Adapter {
    private final String ADAPTEE_TYPE = "java.util.Map";

    @Override
    public String getAdapteeOfType() {
        return ADAPTEE_TYPE;
    }

    @Override
    public JsonValue write(Type aClass, Object o) throws IllegalAccessException {
        JsonObjectBuilder ob = Json.createObjectBuilder();

        //noinspection unchecked
        Map<Object, Object> m = (Map<Object, Object>) o;

        for (Object key : m.keySet()) {
            Object value = m.get(key);
            if (null == value) { continue; }
            ob = jsonKeyValue(ob, key.toString(), value);
        }

        return ob.build();
    }

    @Override
    public <T> T read(final JsonValue v, TypeToken<?> tt) {
        throw new NoImplementationException();
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF