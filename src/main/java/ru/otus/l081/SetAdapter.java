package ru.otus.l081;

import com.google.common.reflect.TypeToken;

import javax.json.*;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
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
     * @param tt the type token of the collection
     * @return the type token the element from the collection
     */
    private TypeToken<?> getTypeOfFirstParameterAddMethod(TypeToken<?> tt) {
        Method[] methods = Set.class.getMethods();
        Optional<Method> optionalMethod = Optional.empty();

        for (Method m : methods) {
            if (m.getName().equals("add")) {
                if (1 == m.getParameterCount()) {
                    optionalMethod = Optional.of(m);
                    break;
                }
            }
        }

        if (! optionalMethod.isPresent()) {
            throw new NoImplementationException();
        }

        return tt.resolveType(
            optionalMethod.get().getGenericParameterTypes()[0]
        );

    }

    /**
     * TODO experimental
     */
    @Override
    public <T> T read(final JsonValue value, TypeToken<?> tt) {
        // Prepare object.
        TypeToken<?> elementOfContainetType = getTypeOfFirstParameterAddMethod(tt);
        //noinspection unchecked
        Set<Object> set = (Set<Object>) newInstance(
            tt.getRawType()
        );
        CollectionWrapper collection = new CollectionWrapper(
            this, elementOfContainetType, set
        );

        // Get the JsonArray structure from JsonValue.
        JsonArray jsonArray = (JsonArray) value;
        jsonArray.forEach(collection::add);

        //noinspection unchecked
        return (T) set;
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF