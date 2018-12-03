/*
 * DeptEntityXMLAdapter.java
 * This file was last modified at 2018.12.01 15:09 by Victor N. Skurikhin.
 * $Id$
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */

package ru.otus.adapters.xml;

import ru.otus.adapters.DataSetAdapter;
import ru.otus.models.DeptEntity;

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
