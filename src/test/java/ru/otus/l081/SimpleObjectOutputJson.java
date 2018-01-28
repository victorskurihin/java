package ru.otus.l081;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class SimpleObjectOutputJson {
    Set<Object> visited = new HashSet<>();

    JsonArrayBuilder jsonArray(JsonArrayBuilder jab, Object o)
    throws IllegalAccessException {
        if (null == o || visited.contains(o)) {
            return jab;
        }
        visited.add(o);

        switch (o.getClass().getTypeName()) {
            case "boolean[]":
                for (boolean b : boolean[].class.cast(o)) { jab.add(b); }
                return jab;
            case "char[]":
                for (char c : char[].class.cast(o)) { jab.add(c); }
                return jab;
            case "byte[]":
                for (byte b : byte[].class.cast(o)) { jab.add(b); }
                return jab;
            case "short[]":
                for (short s : short[].class.cast(o)) { jab.add(s); }
                return jab;
            case "int[]":
                Arrays.stream(int[].class.cast(o)).forEach(jab::add);
                return jab;
            case "long[]":
                Arrays.stream(long[].class.cast(o)).forEach(jab::add);
                return jab;
            case "float[]":
                for (float f : float[].class.cast(o)) { jab.add(f); }
                return jab;
            case "double[]":
                Arrays.stream(double[].class.cast(o)).forEach(jab::add);
                return jab;
        }

        for (Object e : Object[].class.cast(o)) {
            if (null == e) continue;
            if (e.getClass().isArray()) {
                JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
                jab.add(jsonArray(jsonArrayBuilder, e));
            } else {
                JsonObjectBuilder ob = Json.createObjectBuilder();
                jab.add(jsonObject(ob, e));
            }
        }

        return jab;
    }

    JsonObjectBuilder addFiled(JsonObjectBuilder job, Object o, Field f)
    throws IllegalAccessException {

        switch (f.getType().getName()) {
            case "boolean":
                return job.add(f.getName(), f.getBoolean(o));
            case "byte":
                return job.add(f.getName(), f.getByte(o));
            case "char":
                return job.add(f.getName(), f.getChar(o));
            case "short":
                return job.add(f.getName(), f.getShort(o));
            case "int":
                return job.add(f.getName(), f.getInt(o));
            case "long":
                return job.add(f.getName(), f.getLong(o));
            case "float":
                return job.add(f.getName(), f.getFloat(o));
            case "double":
                return job.add(f.getName(), f.getDouble(o));
        }

        if (null == f.get(o)) {
            return job.add(f.getName(), "null");
        }

        if (f.getType().isArray()) {
            JsonArrayBuilder jab = Json.createArrayBuilder();
            return job.add(f.getName(), jsonArray(jab, f.get(o)));
        }

        if (String.class == f.getType()) {
            return job.add(f.getName(), (String) f.get(o));
        }

        JsonObjectBuilder ob = Json.createObjectBuilder();

        return job.add(f.getName(), jsonObject(ob, f.get(o)));
    }

    JsonObjectBuilder jsonObject(JsonObjectBuilder ob, Object o)
    throws IllegalAccessException {
        if (null == o || visited.contains(o)) {
            return ob;
        }
        visited.add(o);

        if (o.getClass().getDeclaredFields().length < 1) {
            return ob;
        }

        for (Field field : o.getClass().getDeclaredFields()) {
            boolean accessible = field.isAccessible();
            field.setAccessible(true);

            try {
                ob = addFiled(ob, o, field);
            } catch (Throwable e) {
                throw e;
            } finally {
                field.setAccessible(accessible);
            }
        }

        return ob;
    }

    public String toJson(Type aClass, Object o) throws IllegalAccessException {
        switch (aClass.getTypeName()) {
            case "java.lang.Boolean":
                return ((Boolean) o).toString();
            case "java.lang.Character":
                return String.format("\"%c\"", (char) o);
            case "java.lang.Byte":
                return Byte.toString((Byte) o);
            case "java.lang.Short":
                return Short.toString((Short) o);
            case "java.lang.Integer":
                return Integer.toString((Integer) o);
            case "java.lang.Long":
                return Long.toString((Long) o);
            case "java.lang.Float":
                return Float.toString((Float) o);
            case "java.lang.Double":
                return Double.toString((Double) o);
            case "java.lang.String":
                return String.format("\"%s\"", (String) o);
        }

        try {
            if (o.getClass().isArray()) {
                JsonArrayBuilder jab = Json.createArrayBuilder();
                return jsonArray(jab, o).build().toString();
            }
            JsonObjectBuilder ob = Json.createObjectBuilder();

            return jsonObject(ob, o).build().toString();
        } catch (Exception e) {
            throw e;
        }
    }

    public String toJson(Object o) throws IllegalAccessException {
        return o != null
               ? toJson(o.getClass(), o)
               : "null";
    }
}
