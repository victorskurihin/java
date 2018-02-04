package ru.otus.l091;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Adapters implements TypeNames {
    protected Map<String, Adapter> adapters; // the map of adapters
    private List<String> result;

    public void setAdapters(Map<String, Adapter> map) {
        adapters = map;
    }

    String classGetNameToTableName(Class <? extends DataSet> c) {
        return c.getName().replace('.','_');
    }

    private String getArrayDescription(Field field) {
        return null;
    }

    private String getColumnDescription(Field field) {
        if (null == field) { return null; }

        switch (field.getType().getName()) {
            case BOOLEAN:
                return field.getName() + " BOOLEAN";
            case BYTE:
            case SHORT:
                return field.getName() + " SMALLINT";
            case CHAR:
                return field.getName() + " CHAR(1)";
            case INT:
                return field.getName() + " INTEGER";
            case LONG:
                return field.getName() + " BIGINT";
            case FLOAT:
                return field.getName() + " REAL";
            case DOUBLE:
                return field.getName() + " DOUBLE PRECISION";
            case JAVA_LANG_STRING:
                return field.getName() + " TEXT";
        }

        if (field.getType().isArray()) {
            return getArrayDescription(field);
        }

        if (DataSet.class.isAssignableFrom(field.getType())) {
            //noinspection unchecked
            this.result.add(
                createTableForClass( (Class<? extends DataSet>) field.getType())
            );
            return null;
        }

        throw new NoImplementationException();
    }

    private String separator = ", ";

    private String getColumnsForClass(String s, Class <? extends DataSet> c) {

        if (DataSet.class == c) {
            s = s.concat("id BIGSERIAL PRIMARY KEY");
            return s;
        }

        if (DataSet.class.isAssignableFrom(c.getSuperclass())) {
            //noinspection unchecked
            s = getColumnsForClass(
                s, (Class<? extends DataSet>) c.getSuperclass()
            );
        }

        for (Field field : c.getDeclaredFields()) {
            boolean accessible = field.isAccessible();
            field.setAccessible(true);

            try {
                String columnDesc = getColumnDescription(field);

                if (null != columnDesc) {
                    //noinspection ResultOfMethodCallIgnored
                    s = s.concat(separator).concat(columnDesc);
                }
            } catch (Throwable e) {
                throw e;
            } finally {
                field.setAccessible(accessible);
            }
        }

        return s;
    }

    public String createTableForClass(Class <? extends DataSet> c) {
        System.out.println("c.getTypeName() = " + c.getTypeName());

        String tableName = classGetNameToTableName(c);
        String result = "CREATE TABLE IF NOT EXISTS " + tableName + " ( ";

        result = getColumnsForClass(result, c);
        result += " )";

        return result;
    }

    public List<String> createTablesForClass(Class <? extends DataSet> c) {
        this.result = new ArrayList<>();
        this.result.add(createTableForClass(c));
        return this.result;
    }

    private <T extends DataSet> String getValue(Field field, Class<? super T> c, T o)
        throws IllegalAccessException {

        if (null == field) { return null; }

        switch (field.getType().getName()) {
            case BOOLEAN:
                return field.getBoolean(o) ? "TRUE" : "FALSE";
            case BYTE:
                return Byte.toString(field.getByte(o));
            case SHORT:
                return Short.toString(field.getShort(o));
            case CHAR:
                return String.format("'%c'", field.getChar(o));
            case INT:
                return Integer.toString(field.getInt(o));
            case LONG:
                return Long.toString(field.getLong(o));
            case FLOAT:
                return Float.toString(field.getFloat(o));
            case DOUBLE:
                return Double.toString(field.getDouble(o));
            case JAVA_LANG_STRING:
                return String.format("'%s'", (String) field.get(o));
        }

        if (field.getType().isArray()) {
            throw new NoImplementationException();
        }

        if (DataSet.class.isAssignableFrom(field.getType())) {
            //noinspection unchecked
            this.result.add(
                insertObjectToTable((T) field.get(o))
            );
            return null;
        }

        throw new NoImplementationException();
    }

    private <T extends DataSet> String getValues(String s, Class<? super T> c, T o)
        throws IllegalAccessException {

        if (DataSet.class == c) {
            s = s.concat(Long.toString(o.getId()));
            return s;
        }

        if (DataSet.class.isAssignableFrom(c.getSuperclass())) {
            //noinspection unchecked
            s = getValues(s, c.getSuperclass(), o);
        }

        for (Field field : c.getDeclaredFields()) {
            boolean accessible = field.isAccessible();
            field.setAccessible(true);

            try {
                String value = getValue(field, c, o);

                if (null != value) {
                    //noinspection ResultOfMethodCallIgnored
                    s = s.concat(separator).concat(value);
                }
            } catch (Throwable e) {
                throw e;
            } finally {
                field.setAccessible(accessible);
            }
        }

        return s;
    }

    private <T extends DataSet> String getValuesObject(String s, T o) {
        if (DataSet.class.isAssignableFrom(o.getClass().getSuperclass())) {
            //noinspection unchecked
            try {
                s = getValues(s, (Class<T>) o.getClass(), o);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        return s;
    }

    private <T extends DataSet> String insertObjectToTable(T o) {

        String tableName = classGetNameToTableName(o.getClass());
        String result = "INSERT INTO " + tableName + " VALUES ( ";

        result = getValuesObject(result, o);
        result += " )";

        return result;
    }

    public <T extends DataSet> List<String> insertObjectsToTables(T o) {
        this.result = new ArrayList<>();
        this.result.add(insertObjectToTable(o));
        return this.result;
    }
}
