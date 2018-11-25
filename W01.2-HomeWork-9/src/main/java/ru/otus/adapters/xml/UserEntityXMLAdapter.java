package ru.otus.adapters.xml;

/*
 * Created by VSkurikhin at autumn 2018.
 */

import ru.otus.adapters.DataSetAdapter;
import ru.otus.models.UserEntity;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class UserEntityXMLAdapter
extends XmlAdapter<String, UserEntity> implements DataSetAdapter<UserEntity>
{
    @Override
    public UserEntity unmarshal(String s) throws Exception
    {
        return unmarshalAdapter(s);
    }

    @Override
    public String marshal(UserEntity userEntity) throws Exception
    {
        return marshalAdapter(userEntity);
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
