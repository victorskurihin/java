package ru.otus.l081;

import com.google.common.reflect.TypeToken;

import javax.json.Json;
import javax.json.JsonObjectBuilder;
import javax.json.JsonValue;
import java.io.InputStream;
import java.lang.reflect.Type;

/**
 * By default this class converts application classes to JSON using type adapters.
 */
public class DefaultAdapter extends Adapters implements Adapter {
    private final String ADAPTEE_TYPE = "__DEFAULT__";

    @Override
    public String getAdapteeOfType() {
        return ADAPTEE_TYPE;
    }

    @Override
    public JsonValue write(Type aClass, Object o) throws IllegalAccessException {
        JsonObjectBuilder ob = Json.createObjectBuilder();
        return jsonObject(ob, o).build();
    }

    @Override
    public <T> T read(InputStream body, TypeToken<?> tt) {
        return null;
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF