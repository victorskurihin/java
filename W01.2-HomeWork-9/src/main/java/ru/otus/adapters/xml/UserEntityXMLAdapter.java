/*
 * UserEntityXMLAdapter.java
 * This file was last modified at 29.11.18 10:35 by Victor N. Skurikhin.
 * $Id$
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */

package ru.otus.adapters.xml;

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
