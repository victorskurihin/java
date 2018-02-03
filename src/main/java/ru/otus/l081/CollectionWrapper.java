package ru.otus.l081;

import com.google.common.reflect.TypeToken;

import javax.json.*;
import java.math.BigInteger;
import java.util.Collection;

public class CollectionWrapper extends Adapters {
    Collection<Object> collection;
    private TypeToken<?> typeOfElementCollection;

    CollectionWrapper(
        Adapters adapters, TypeToken<?> type, Collection<Object> collection
    ) {
        this.collection = collection;
        typeOfElementCollection = type;
        setAdapters(adapters.adapters);
        setVisited(adapters.visited);
    }

    private void addString(String s) {
        if (typeOfElementCollection.getRawType() == Character.class) {
            collection.add(s.charAt(0));
        } else {
            collection.add(s);
        }
    }

    private void addNumber(String s) {
        Class<?> type = typeOfElementCollection.getRawType();
        if (type == BigInteger.class) {
            collection.add(new BigInteger(s));
        } else if (type == Double.class) {
            collection.add(new Double(s));
        } else if (type == Float.class) {
            collection.add(new Float(s));
        } else if (type == Long.class) {
            collection.add(new Long(s));
        } else if (type == Integer.class) {
            collection.add(new Integer(s));
        } else if (type == Short.class) {
            collection.add(new Short(s));
        } else if (type == Byte.class) {
            collection.add(new Byte(s));
        } else
            throw new NoImplementationException();
    }

    private boolean addCollection(JsonValue value) {
        if (typeOfElementCollection.isSubtypeOf(Collection.class)) {
            collection.add(
                adapters
                    .get(typeOfElementCollection.getRawType().getTypeName())
                    .read(value, typeOfElementCollection)
            );
            return true;
        }
        return false;
    }

    public void add(JsonValue value) {
        switch (value.getValueType()) {
            case ARRAY:
                if (addCollection(value)) {
                    break;
                }
                throw new NoImplementationException();
            case OBJECT:
                throw new NoImplementationException();
            case STRING:
                addString(((JsonString) value).getString());
                break;
            case NUMBER:
                addNumber(value.toString());
                break;
            case TRUE:
                collection.add(Boolean.TRUE);
                break;
            case FALSE:
                collection.add(Boolean.FALSE);
                break;
            case NULL:
                collection.add(null);
        }
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
