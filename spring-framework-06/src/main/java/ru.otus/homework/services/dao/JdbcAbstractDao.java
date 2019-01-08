package ru.otus.homework.services.dao;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public abstract class JdbcAbstractDao<T>
{
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public JdbcAbstractDao(DataSource dataSource)
    {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    public abstract T fetchFrom(ResultSet resultSet) throws SQLException;

    List<T> findAll(String sql)
    {
        return namedParameterJdbcTemplate.query(sql, (rs, rowNum) -> fetchFrom(rs));
    }

    List<T> findAllWithFunction(String sql, Function<ResultSet, List<T>> f)
    {
        return namedParameterJdbcTemplate.query(sql, f::apply);
    }

    T findById(String sql, String sid, long id)
    {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put(sid, id);

        return namedParameterJdbcTemplate.queryForObject(sql, paramMap, (rs, rowNum) -> fetchFrom(rs));
    }

    T findBySKey(String sql, String sid, String sKey)
    {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put(sid, sKey);

        return namedParameterJdbcTemplate.queryForObject(sql, paramMap, (rs, rowNum) -> fetchFrom(rs));
    }

    String findStringFieldNameById(String tableName, String filedName, String fieldKey, String sid, long id)
    {
        String sql = "SELECT " + filedName + " FROM " + tableName + " WHERE " + fieldKey + " = :" + sid;

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put(sid, id);

        return namedParameterJdbcTemplate.queryForObject(sql, paramMap, String.class);
    }
}
