/*
 * UserEntityTypeXMLAdapter.java
 * This file was last modified at 2018.12.03 20:05 by Victor N. Skurikhin.
 * $Id$
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */

package ru.otus.adapters.xml;

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
