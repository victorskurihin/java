package ru.otus.l081;

import com.google.common.reflect.TypeToken;

import javax.json.*;
import java.lang.reflect.Type;

/**
 * By default this class converts application array to JSON using type adapters.
 */

public class BuildinArrayAdapter extends Adapters implements Adapter {
    private final String ADAPTEE_TYPE = "__BUILD_IN_ARRAY__";

    @Override
    public String getAdapteeOfType() {
        return ADAPTEE_TYPE;
    }

    @Override
    public JsonValue write(Type aClass, Object o) throws IllegalAccessException {
        JsonArrayBuilder jab = Json.createArrayBuilder();
        return jsonArray(jab, o).build();
    }

    @Override
    public <T> T read(final JsonValue value, TypeToken<?> tt) {
        // Get the JsonArray structure from JsonValue.
        JsonArray jsonArray = (JsonArray) value;
        jsonArray.size();

        switch (tt.getRawType().getTypeName()) {
            case Adapters.BOOLEAN_ARRAY:
                boolean[] booleans = new boolean[jsonArray.size()];
                for (int i = 0; i < jsonArray.size(); ++i) {
                    booleans[i] = jsonArray.getBoolean(i);
                }
                return (T) booleans;
            case Adapters.BYTE_ARRAY:
                byte[] bytes = new byte[jsonArray.size()];
                for (int i = 0; i < jsonArray.size(); ++i) {
                    bytes[i] = (byte) jsonArray.getInt(i);
                }
                return (T) bytes;
            case Adapters.CHAR_ARRAY:
                char[] chars = new char[jsonArray.size()];
                for (int i = 0; i < jsonArray.size(); ++i) {
                    chars[i] = jsonArray.getString(i).charAt(0);
                }
                return (T) chars;
            case Adapters.SHORT_ARRAY:
                short[] shorts = new short[jsonArray.size()];
                for (int i = 0; i < jsonArray.size(); ++i) {
                    shorts[i] = (short) jsonArray.getInt(i);
                }
                return (T) shorts;
            case Adapters.INT_ARRAY:
                int[] ints = new int[jsonArray.size()];
                for (int i = 0; i < jsonArray.size(); ++i) {
                    ints[i] = jsonArray.getInt(i);
                }
                return (T) ints;
            case Adapters.LONG_ARRAY:
                long[] longs = new long[jsonArray.size()];
                for (int i = 0; i < jsonArray.size(); ++i) {
                    longs[i] = jsonArray.getJsonNumber(i).longValue();
                }
                return (T) longs;
            case Adapters.FLOAT_ARRAY:
                float[] floats = new float[jsonArray.size()];
                for (int i = 0; i < jsonArray.size(); ++i) {
                    floats[i] = (float) jsonArray.getJsonNumber(i).doubleValue();
                }
                return (T) floats;
            case Adapters.DOUBLE_ARRAY:
                double[] doubles = new double[jsonArray.size()];
                for (int i = 0; i < jsonArray.size(); ++i) {
                    doubles[i] = jsonArray.getJsonNumber(i).doubleValue();
                }
                return (T) doubles;
        }

        throw new NoImplementationException();
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF