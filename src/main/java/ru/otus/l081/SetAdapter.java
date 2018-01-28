package ru.otus.l081;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonValue;
import java.lang.reflect.Type;
import java.util.Set;

public class SetAdapter extends Adapters implements Adapter {
    private final String ADAPTEE_TYPE = "java.util.Set";

    @Override
    public String getAdapteeOfType() {
        return ADAPTEE_TYPE;
    }

    @Override
    public JsonValue jsonValue(Type aClass, Object o) throws IllegalAccessException {
        JsonArrayBuilder jab = Json.createArrayBuilder();
        //noinspection unchecked
        for (Object e : (Set<Object>) o) {
            jab = addToJsonArray(jab, e);
        }
        return jab.build();
    }
}
