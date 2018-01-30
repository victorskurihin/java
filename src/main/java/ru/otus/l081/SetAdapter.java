package ru.otus.l081;

import com.google.common.reflect.TypeToken;

import javax.json.*;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
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
    public <T> T read(InputStream body, TypeToken<T> tt) {
        // Create JsonReader from Json.
//        JsonReader reader = Json.createReader(body);
        // Prepare object.
//        Set<Object> set = (Set<Object>) newInstance(tClass);
        // Get the JsonObject structure from JsonReader.
//        JsonArray jsonArray = reader.readArray();

//        for (Method method : tClass.getMethods()) {
//            Class returnClass = method.getReturnType();
//            if (Collection.class.isAssignableFrom(returnClass)) {
//                Type returnType = method.getGenericReturnType();
//                if (returnType instanceof ParameterizedType) {
//                    ParameterizedType paramType = (ParameterizedType) returnType;
//                    Type[] argTypes = paramType.getActualTypeArguments();
//                    if (argTypes.length > 0) {
//                        System.out.println("Generic type is " + argTypes[0]);
//                    }
//                }
//            }
//        }
//        jsonArray.forEach(
//            value -> {
//                switch (value.getValueType()) {
//                    case ARRAY:
//                        throw new NoImplementedException();
//                    case OBJECT:
//                        throw new NoImplementedException();
//                    case STRING:
//                        set.add(value.toString());
//                        break;
//                    case NUMBER:
//                        set.add(new Long(value.toString()));
//                        break;
//                    case TRUE:
//                        System.out.println("value.toString() = " + value.toString());
////                        set.add(true);
//                        break;
//                    case FALSE:
//                        System.out.println("value.toString() = " + value.toString());
////                        set.add(false);
//                        break;
//                    case NULL:
//                        System.out.println("value.toString() = " + value.toString());
////                        set.add(null);
//                        break;
//                }
//            }
//        );
        return null;
    }
}
