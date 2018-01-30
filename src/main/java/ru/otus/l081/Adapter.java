package ru.otus.l081;

import com.google.common.reflect.TypeToken;

import javax.json.JsonValue;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.Map;

public interface Adapter {
    public void setAdapters(Map<String, Adapter> map);
    public String getAdapteeOfType();
    public JsonValue write(Type aClass, Object o) throws IllegalAccessException;
    public <T> T read(final InputStream body, TypeToken<T> tt);
}
