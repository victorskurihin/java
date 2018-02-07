package ru.otus.l091;

import com.google.common.reflect.TypeToken;

import java.sql.ResultSet;
import java.util.List;
import java.util.Map;

public interface Adapter {
    public void setAdapters(Map<String, Adapter> map);
    public String getAdapteeOfType();
    public List<String> create(Class<? extends DataSet> o);
    public <T extends DataSet> List<String> write(T o);
    public <T> T read(ResultSet resultSet, TypeToken<? extends DataSet> tt);
}
