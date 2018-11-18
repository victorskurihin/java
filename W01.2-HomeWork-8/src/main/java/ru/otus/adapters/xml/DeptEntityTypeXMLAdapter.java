package ru.otus.adapters.xml;

/*
 * Created by VSkurikhin at autumn 2018.
 */

import ru.otus.adapters.DataSetAdapter;
import ru.otus.adapters.DeptEntryType;
import ru.otus.models.DeptEntity;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class DeptEntityTypeXMLAdapter
extends XmlAdapter<DeptEntryType, DeptEntity> implements DataSetAdapter<DeptEntity>
{
    @Override
    public DeptEntity unmarshal(DeptEntryType v) throws Exception
    {
        if (v == null) return null;

        DeptEntity entity = new DeptEntity();
        entity.setId(v.id);
        entity.setTitle(v.title);

        return entity;
    }

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
