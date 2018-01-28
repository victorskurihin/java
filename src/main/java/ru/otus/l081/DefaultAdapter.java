package ru.otus.l081;

import javax.json.Json;
import javax.json.JsonObjectBuilder;
import javax.json.JsonValue;
import java.lang.reflect.Type;

public class DefaultAdapter extends Adapters implements Adapter {
    private final String ADAPTEE_TYPE = "__DEFAULT__";

    @Override
    public String getAdapteeOfType() {
        return ADAPTEE_TYPE;
    }

    @Override
    public JsonValue jsonValue(Type aClass, Object o) throws IllegalAccessException {
        JsonObjectBuilder ob = Json.createObjectBuilder();
        return jsonObject(ob, o).build();
    }
}
