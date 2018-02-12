package ru.otus.l101.adapters;

import com.google.common.reflect.TypeToken;
import ru.otus.l101.dataset.DataSet;

import java.sql.ResultSet;
import java.util.List;

public interface Adapter {
    public String getAdapteeOfType();
    public List<String> create(Class<? extends DataSet> o);
    public <T extends DataSet> List<String> write(T o);
    public <T> T read(ResultSet resultSet, TypeToken<? extends DataSet> tt, long id);
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
