package ru.otus.l091.adapters;

import com.google.common.reflect.TypeToken;
import ru.otus.l091.DataSet;

import java.sql.ResultSet;
import java.util.List;
import java.util.Map;

public interface Adapter {
    public void setAdapters(Map<String, Adapter> map);
    public String getAdapteeOfType();
    public List<String> create(Class<? extends DataSet> o);
    public <T extends DataSet> List<String> write(T o);
    public <T> T read(ResultSet resultSet, TypeToken<? extends DataSet> tt, long id);
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
