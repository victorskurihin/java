package ru.otus.l081;

import javax.json.JsonValue;
import java.lang.reflect.Type;
import java.util.Map;

public interface Adapter {
    public void setAdapters(Map<String, Adapter> map);
    public String getAdapteeOfType();
    public JsonValue jsonValue(Type aClass, Object o) throws IllegalAccessException;
}
