package ru.otus.l081;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import java.io.OutputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.util.Arrays;

public class ObjectOutputJson {
    public static final String CLASS_NAME = "className";
    private PrintStream ps;
    protected JsonObjectBuilder ob;

    public ObjectOutputJson(OutputStream os) {
        ps = new PrintStream(os);
        ob = Json.createObjectBuilder();
    }

    public ObjectOutputJson() {
        this(System.out);
    }

    private JsonArrayBuilder addArrayOfBoolean(JsonArrayBuilder jab, boolean ... a) {
        for (boolean b : a) { jab.add(b); }
        return jab;
    }

    private JsonArrayBuilder addArrayOfByte(JsonArrayBuilder jab, byte ... a) {
        for (byte b : a) { jab.add(b); }
        return jab;
    }

    private JsonArrayBuilder addArrayOfChar(JsonArrayBuilder jab, char ... a) {
        for (char c : a) { jab.add(c); }
        return jab;
    }

    private JsonArrayBuilder addArrayOfShort(JsonArrayBuilder jab, short ... a) {
        for (short s : a) { jab.add(s); }
        return jab;
    }

    private JsonArrayBuilder addArrayOfInt(JsonArrayBuilder jab, int ... a) {
        Arrays.stream(a).forEach(jab::add);
        return jab;
    }

    private JsonArrayBuilder addArrayOfLong(JsonArrayBuilder jab, long ... a) {
        Arrays.stream(a).forEach(jab::add);
        return jab;
    }

    private JsonArrayBuilder addArrayOfFloat(JsonArrayBuilder jab, float ... a) {
        for (float s : a) { jab.add(s); }
        return jab;
    }

    private JsonArrayBuilder addArrayOfDouble(JsonArrayBuilder jab, double ... a) {
        Arrays.stream(a).forEach(jab::add);
        return jab;
    }

    JsonObjectBuilder addArray(JsonObjectBuilder job, Object o, Field f) throws IllegalAccessException {
        JsonArrayBuilder jab = Json.createArrayBuilder();
        switch (f.getType().getComponentType().getName()) {
            case "boolean":
                return job.add(
                    f.getName(), addArrayOfBoolean(jab, boolean[].class.cast(f.get(o)))
                );
            case "byte":
                return job.add(
                    f.getName(), addArrayOfByte(jab, byte[].class.cast(f.get(o)))
                );
            case "char":
                return job.add(
                    f.getName(), addArrayOfChar(jab, char[].class.cast(f.get(o)))
                );
            case "short":
                return job.add(
                    f.getName(), addArrayOfShort(jab, short[].class.cast(f.get(o)))
                );
            case "int":
                return job.add(
                    f.getName(), addArrayOfInt(jab, int[].class.cast(f.get(o)))
                );
            case "long":
                return job.add(
                    f.getName(), addArrayOfLong(jab, long[].class.cast(f.get(o)))
                );
            case "float":
                return job.add(
                    f.getName(), addArrayOfFloat(jab, float[].class.cast(f.get(o)))
                );
            case "double":
                return job.add(
                    f.getName(), addArrayOfDouble(jab, double[].class.cast(f.get(o)))
                );
        }
        return job;
    }

    JsonObjectBuilder addFiled(JsonObjectBuilder job, Object o, Field f) throws IllegalAccessException {
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
        if (f.getType().isArray()) {
            addArray(job, o, f);
        }
        if (String.class == f.getType()) {
            return job.add(f.getName(), (String) f.get(o));
        }
        return job;
    }

    void doClassFields(Class<?> c, Object o) throws IllegalAccessException {
        if (c.getDeclaredFields().length < 1) {
            return;
        }
        for (Field field : c.getDeclaredFields()) {
            ob = addFiled(ob, o, field);
        }
    }

    public String toJson(Object o) throws IllegalAccessException {
        doClassFields(o.getClass(), o);
        return ob.build().toString();
    }

}
