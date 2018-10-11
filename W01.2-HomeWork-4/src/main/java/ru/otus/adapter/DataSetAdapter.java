package ru.otus.adapter;

/*
 * Created by VSkurikhin at autumn 2018.
 */

import ru.otus.dataset.DataSet;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

interface DataSetAdapter<T extends DataSet>
{
    default Class<T> getTypeParameterClass()
    {
        Type[] types = getClass().getGenericInterfaces();
        for (Type type: types) {
            if (type.getTypeName().startsWith("ru.otus.adapter.DataSetAdapter")) {
                ParameterizedType paramType = (ParameterizedType) type;
                // some magic with reflection
                //noinspection unchecked
                return (Class<T>) paramType.getActualTypeArguments()[0];
            }
        }

        // Backup way for XML Adapter. Need more testing.
        Type type = getClass();
        ParameterizedType paramType = (ParameterizedType) type;

        //noinspection unchecked
        return (Class<T>) paramType.getActualTypeArguments()[1];
    }

    default T unmarshalAdapter(String name) throws Exception
    {
        return unmarshalAdapter(name, getTypeParameterClass());
    }

    default T unmarshalAdapter(String name, Class<T> c)
    throws IllegalAccessException, InstantiationException
    {
        T entity = c.newInstance();
        entity.setName(name);
        return entity;
    }

    default String marshalAdapter(T v)
    {
        return v == null ? null : v.getName();
    }

    default JsonObjectBuilder getJsonWithIdObjectBuilder(T v)
    {
        return Json.createObjectBuilder().add("id", v.getId());
    }

    default JsonObject marshalToJson(T v)
    {
        return Json.createObjectBuilder()
                .add("id", v.getId())
                .add("name", v.getName()).build();
    }

    default T unmarshalFromJson(JsonObject obj)
    throws IllegalAccessException, InstantiationException
    {
        T entity = getTypeParameterClass().newInstance();
        entity.setId(obj.getInt("id"));
        entity.setName(obj.getString("name"));
        return entity;
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
