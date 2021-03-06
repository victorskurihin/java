package ru.otus.l101.dao;

/*
 * Created by VSkurikhin at winter 2018.
 */

import com.google.common.reflect.TypeToken;
import ru.otus.l101.dataset.DataSet;

import java.sql.ResultSet;
import java.util.List;
import java.util.Map;

public interface Adapter {
    String getAdapteeOfType();
    List<String> create(Class<? extends DataSet> o);
    <T extends DataSet> List<String> write(T o);
    <T extends DataSet> T read(ResultSet resultSet, TypeToken<? extends DataSet> tt, long id);
    void setAdapters(Map<String, Adapter> adapters);
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
