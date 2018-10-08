package ru.otus.adapter;

/*
 * Created by VSkurikhin at autumn 2018.
 */

import ru.otus.dataset.DataSet;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class DataSetAdapter<T extends DataSet> extends XmlAdapter<String, T>
{
    private Class<T> getTypeParameterClass()
    {
        Type type = getClass().getGenericSuperclass();
        ParameterizedType paramType = (ParameterizedType) type;

        //noinspection unchecked
        return (Class<T>) paramType.getActualTypeArguments()[0];
    }

    @Override
    public T unmarshal(String name) throws Exception
    {
        T entity = getTypeParameterClass().newInstance();
        entity.setName(name);
        return entity;
    }

    @Override
    public String marshal(T v) throws Exception
    {
        return v == null ? null : v.getName();
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
