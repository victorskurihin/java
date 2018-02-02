package ru.otus.l081;

import com.google.common.reflect.TypeToken;

import javax.json.*;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Optional;
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

    /**
     * TODO experimental
     */
    /**
     * @param tt the type token of the collection
     * @return the type token the element from the collection
     */
    private TypeToken<?> getGenericTypeOfFirstParameter(TypeToken<?> tt) {
        Method[] methods = Set.class.getMethods();
        Optional<Method> optionalMethod= Arrays.stream(methods)
            .filter(m -> m.getName().equals("add"))
            .filter(m -> 1 == m.getParameterCount()).findFirst();
        if (! optionalMethod.isPresent()) {
            throw new NoImplementedException();
        }
        return tt.resolveType(
            optionalMethod.get().getGenericParameterTypes()[0]
        );

    }

    /**
     * TODO experimental
     */
    @Override
    public <T> T read(final InputStream body, TypeToken<?> tt) throws NoSuchMethodException {
        // Create JsonReader from Json.
        JsonReader reader = Json.createReader(body);
        // Prepare object.
        TypeToken<?> elementOfContainetType = getGenericTypeOfFirstParameter(tt);

        @SuppressWarnings("unchecked") Set<Object> set = (Set<Object>) newInstance(
            tt.getRawType()
        );
        CollectionWraper collection = new CollectionWraper(this, elementOfContainetType, set);

        // Get the JsonObject structure from JsonReader.
        JsonArray jsonArray = reader.readArray();
        jsonArray.forEach(collection::add);

        return (T) set;
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF