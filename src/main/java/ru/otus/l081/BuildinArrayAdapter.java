package ru.otus.l081;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonValue;
import java.lang.reflect.Type;

public class BuildinArrayAdapter extends Adapters implements Adapter {
    private final String ADAPTEE_TYPE = "__BUILD_IN_ARRAY__";

    @Override
    public String getAdapteeOfType() {
        return ADAPTEE_TYPE;
    }

    @Override
    public JsonValue jsonValue(Type aClass, Object o) throws IllegalAccessException {
        JsonArrayBuilder jab = Json.createArrayBuilder();
        return jsonArray(jab, o).build();
    }
}
