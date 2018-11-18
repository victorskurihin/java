package ru.otus.models;

/*
 * Created by VSkurikhin at autumn 2018.
 */

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;

public class EntityUtil
{
    public static <T> String convertToJson(T entity) {
        Jsonb jsonb = JsonbBuilder.create();
        return jsonb.toJson(entity);
    }

    public static <T> T convertFromJson(String json, Class<T> clazz)
    {
        Jsonb jsonb = JsonbBuilder.create();
        return jsonb.fromJson(json, clazz);
    }

    public static boolean isOdd(EmpEntity e) {
        return ! (e.getId() % 2 == 0);
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
