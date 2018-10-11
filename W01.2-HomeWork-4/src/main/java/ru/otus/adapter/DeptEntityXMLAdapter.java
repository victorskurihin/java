package ru.otus.adapter;

/*
 * Created by VSkurikhin at autumn 2018.
 */

import ru.otus.dataset.DeptEntity;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class DeptEntityXMLAdapter
extends XmlAdapter<String, DeptEntity> implements DataSetAdapter<DeptEntity>
{
    @Override
    public DeptEntity unmarshal(String s) throws Exception
    {
        return unmarshalAdapter(s);
    }

    @Override
    public String marshal(DeptEntity deptEntity) throws Exception
    {
        return marshalAdapter(deptEntity);
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
