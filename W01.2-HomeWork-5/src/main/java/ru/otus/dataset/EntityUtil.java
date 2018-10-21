package ru.otus.dataset;

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
