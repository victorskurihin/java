/*
 * EntityUtil.java
 * This file was last modified at 2018.12.01 15:30 by Victor N. Skurikhin.
 * $Id$
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */

package ru.otus.models;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;

public class EntityUtil
{
    public static <T> String convertToJson(T entity)
    {
        Jsonb jsonb = JsonbBuilder.create();
        return jsonb.toJson(entity);
    }

    public static <T> T convertFromJson(String json, Class<T> clazz)
    {
        Jsonb jsonb = JsonbBuilder.create();
        return jsonb.fromJson(json, clazz);
    }

    public static boolean isOdd(EmpEntity e)
    {
        return ! (e.getId() % 2 == 0);
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
