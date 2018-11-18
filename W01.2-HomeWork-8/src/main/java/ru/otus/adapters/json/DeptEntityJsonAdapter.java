package ru.otus.adapters.json;

/*
 * Created by VSkurikhin at autumn 2018.
 */

import ru.otus.adapters.DataSetAdapter;
import ru.otus.models.DeptEntity;

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
        return unmarshalAdapter(s, DeptEntity.class);
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
