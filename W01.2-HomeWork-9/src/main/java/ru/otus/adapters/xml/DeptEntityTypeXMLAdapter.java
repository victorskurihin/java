/*
 * DeptEntityTypeXMLAdapter.java
 * This file was last modified at 29.11.18 10:37 by Victor N. Skurikhin.
 * $Id$
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */

/*
 * DeptEntityTypeXMLAdapter.java
 * This file was last modified at 29.11.18 10:37 by Victor N. Skurikhin.
 * $Id$
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */

package ru.otus.adapters.xml;

import ru.otus.adapters.DataSetAdapter;
import ru.otus.adapters.DeptEntryType;
import ru.otus.models.DeptEntity;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class DeptEntityTypeXMLAdapter
extends XmlAdapter<DeptEntryType, DeptEntity> implements DataSetAdapter<DeptEntity>
{
    /**
     * The marshall method is convert the DeptEntity object to XML.
     *
     * @param v the DeptEntity object.
     * @return JSON String.
     * @throws Exception
     */
    @Override
    public DeptEntity unmarshal(DeptEntryType v) throws Exception
    {
        if (v == null) return null;

        DeptEntity entity = new DeptEntity();
        entity.setId(v.id);
        entity.setTitle(v.title);

        return entity;
    }

    /**
     * The unmarshall method is convert the XML String to DeptEntity object.
     *
     * @param v the JSON String.
     * @return DeptEntity object.
     * @throws Exception
     */
    @Override
    public DeptEntryType marshal(DeptEntity v) throws Exception
    {
        if (v == null) return null;

        DeptEntryType entryType = new DeptEntryType();
        entryType.id = v.getId();
        entryType.title = v.getTitle();

        return entryType;
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
