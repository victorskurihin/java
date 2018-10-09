package ru.otus.adapter;

/*
 * Created by VSkurikhin at autumn 2018.
 */

import ru.otus.dataset.UserEntity;

import javax.json.bind.adapter.JsonbAdapter;

public class UserEntityJsonAdapter
implements JsonbAdapter<UserEntity, String>, DataSetAdapter<UserEntity>
{
    @Override
    public String adaptToJson(UserEntity user) throws Exception
    {
        return marshalAdapter(user);
    }

    @Override
    public UserEntity adaptFromJson(String s) throws Exception
    {
        return unmarshalAdapter(s);
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
