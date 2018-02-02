package ru.otus.l081;

import com.google.common.reflect.TypeToken;

import javax.json.JsonString;
import javax.json.JsonValue;
import java.math.BigInteger;
import java.util.Collection;

public class CollectionWraper extends Adapters {
    Collection<Object> collection;
    TypeToken<?> typeOfElementCollection;

    CollectionWraper(Adapters adapters, TypeToken<?> type, Collection<Object> collection) {
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
            throw new NoImplementedException();
    }

    public void add(JsonValue value) {
        switch (value.getValueType()) {
            case ARRAY:
                if (typeOfElementCollection.isArray()) {
                    throw new NoImplementedException();
                }
                throw new NoImplementedException();
            case OBJECT:
                throw new NoImplementedException();
            case STRING:
                addString(((JsonString) value).getString());
                break;
            case NUMBER:
                addNumber(value.toString());
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
