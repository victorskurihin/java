/*
 * UserEntityJsonAdapter.java
 * This file was last modified at 2018.12.03 20:05 by Victor N. Skurikhin.
 * $Id$
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */

package ru.otus.adapters.json;

import ru.otus.adapters.DataSetAdapter;
import ru.otus.models.UserEntity;

import javax.json.bind.adapter.JsonbAdapter;

public class UserEntityJsonAdapter
implements JsonbAdapter<UserEntity, String>, DataSetAdapter<UserEntity>
{
    /**
     * The marshall method is convert the UserEntity object to JSON.
     *
     * @param user the UserEntity object to JSON.
     * @return JSON String.
     * @throws Exception
     */
    @Override
    public String adaptToJson(UserEntity user) throws Exception
    {
        return marshalAdapter(user);
    }

    /**
     * The unmarshall method is convert the JSON String to UserEntity object.
     *
     * @param s the JSON String.
     * @return UserEntity object.
     * @throws Exception
     */
    @Override
    public UserEntity adaptFromJson(String s) throws Exception
    {
        return unmarshalAdapter(s, UserEntity.class);
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
