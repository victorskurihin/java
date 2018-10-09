package ru.otus.adapter;

/*
 * Created by VSkurikhin at autumn 2018.
 */

import ru.otus.dataset.DeptEntity;

import javax.json.JsonObject;
import javax.json.bind.adapter.JsonbAdapter;

public class DeptEntityJsonAdapter
implements JsonbAdapter<DeptEntity, String>, DataSetAdapter<DeptEntity>
{
    @Override
    public String adaptToJson(DeptEntity dept) throws Exception
    {
        return marshalAdapter(dept);
    }

    @Override
    public DeptEntity adaptFromJson(String s) throws Exception
    {
        System.out.println("s = " + s);
        return unmarshalAdapter(s, DeptEntity.class);
    }

    public JsonObject convertToJson(DeptEntity dept) throws Exception
    {
        return getJsonWithIdObjectBuilder(dept).add("title", dept.getTitle()).build();
    }

    public DeptEntity createFromJson(JsonObject obj) throws Exception
    {
        return unmarshalFromJson(obj);
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
