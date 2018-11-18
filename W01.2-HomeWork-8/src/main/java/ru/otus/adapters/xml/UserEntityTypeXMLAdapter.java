package ru.otus.adapters.xml;

/*
 * Created by VSkurikhin at autumn 2018.
 */

import ru.otus.adapters.DataSetAdapter;
import ru.otus.adapters.UserEntryType;
import ru.otus.models.UserEntity;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class UserEntityTypeXMLAdapter
extends XmlAdapter<UserEntryType, UserEntity> implements DataSetAdapter<UserEntity>
{
    @Override
    public UserEntity unmarshal(UserEntryType v) throws Exception
    {
        if (v == null) return null;

        UserEntity entity = new UserEntity();
        entity.setId(v.id);
        entity.setLogin(v.login);

        return entity;
    }

    @Override
    public UserEntryType marshal(UserEntity v) throws Exception
    {
        if (v == null) return null;

        UserEntryType entryType = new UserEntryType();
        entryType.id = v.getId();
        entryType.login = v.getLogin();

        return entryType;
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
