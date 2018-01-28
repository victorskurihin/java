package ru.otus.l081;

import javax.json.Json;
import javax.json.JsonObjectBuilder;
import javax.json.JsonValue;
import java.lang.reflect.Type;
import java.util.Map;

public class MapAdapter extends Adapters implements Adapter {
    private final String ADAPTEE_TYPE = "java.util.Map";

    @Override
    public String getAdapteeOfType() {
        return ADAPTEE_TYPE;
    }

    JsonObjectBuilder jsonKeyValue(JsonObjectBuilder ob, String key, Object o)
        throws IllegalAccessException {

        if (null == o) {
            return ob;
        }

        Class<?> t = o.getClass();

        if (adapters.containsKey(o.getClass().getTypeName())) {
            return ob.add(
                key, adapters.get(t.getTypeName()).jsonValue(t, o)
            );
        } else switch (t.getTypeName()) {
                case "java.lang.Boolean":
                    return ob.add(key, (Boolean) o);
                case "java.lang.Character":
                    return ob.add(key, (Character) o);
                case "java.lang.Byte":
                    return ob.add(key, (Byte) o);
                case "java.lang.Short":
                    return ob.add(key, (Short) o);
                case "java.lang.Integer":
                    return ob.add(key, (Integer) o);
                case "java.lang.Long":
                    return ob.add(key, (Long) o);
                case "java.lang.Float":
                    return ob.add(key, (Float) o);
                case "java.lang.Double":
                    return ob.add(key, (Double) o);
                case "java.lang.String":
                    return ob.add(key, String.format("\"%s\"", (String) o));
                default:
                    String adapter = t.isArray()
                                   ? ObjectOutputJson.BUILD_IN_ARRAY
                                   : ObjectOutputJson.DEFAULT;
                    return ob.add(key, adapters.get(adapter).jsonValue(t, o));
            }
    }

    @Override
    public JsonValue jsonValue(Type aClass, Object o) throws IllegalAccessException {
        JsonObjectBuilder ob = Json.createObjectBuilder();

        //noinspection unchecked
        Map<Object, Object> m = (Map<Object, Object>) o;

        for (Object key : m.keySet()) {
            Object value = m.get(key);
            ob = jsonKeyValue(ob, key.toString(), value);
//            if (null == v) {
//                continue;
//            }
//            if (adapters.containsKey(v.getClass().getTypeName())) {
//                ob.add(
//                    e.toString(),
//                    adapters.get(aClass.getTypeName()).jsonValue(aClass, o)
//                );
//            } else switch (v.getClass().getTypeName()) {
//                case "java.lang.Boolean":
//                    ob.add(e.toString(), (Boolean) v);
//                    break;
//                case "java.lang.Character":
//                    ob.add(e.toString(), ((Character) v).toString());
//                    break;
//                case "java.lang.Byte":
//                    ob.add(e.toString(), (Byte) v);
//                    break;
//                case "java.lang.Short":
//                    ob.add(e.toString(), (Short) v);
//                    break;
//                case "java.lang.Integer":
//                    ob.add(e.toString(), (Integer) v);
//                    break;
//                case "java.lang.Long":
//                    ob.add(e.toString(), (Long) v);
//                    break;
//                case "java.lang.Float":
//                    ob.add(e.toString(), (Float) v);
//                    break;
//                case "java.lang.Double":
//                    ob.add(e.toString(), (Double) v);
//                    break;
//                case "java.lang.String":
//                    ob.add(e.toString(), String.format("\"%s\"", (String) o));
//                    break;
//                default:
//                    if (v.getClass().isArray()) {
//                        ob.add(
//                            e.toString(),
//                            adapters.get(ObjectOutputJson.BUILD_IN_ARRAY)
//                                    .jsonValue(v.getClass(), v)
//                        );
//                    } else {
//                        ob.add(
//                            e.toString(),
//                            adapters.get(ObjectOutputJson.DEFAULT)
//                                    .jsonValue(v.getClass(), v)
//                        );
//                    }
//            }
        }

        return ob.build();
    }
}
