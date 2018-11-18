package ru.otus.adapters;

/*
 * Created by VSkurikhin at autumn 2018.
 */

import ru.otus.models.DataSet;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;

@XmlAccessorType(XmlAccessType.FIELD)
public class DeptEntryType implements DataSet
{
    @XmlAttribute
    public Long id;

    @XmlValue
    public String title;

    @Override
    public long getId()
    {
        return id;
    }

    @Override
    public void setId(long id)
    {
        this.id = id;
    }

    @Override
    public String getName()
    {
        return title;
    }

    @Override
    public void setName(String name)
    {
        this.title = name;
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
