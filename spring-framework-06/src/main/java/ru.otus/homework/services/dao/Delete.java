package ru.otus.homework.services.dao;

import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.SqlUpdate;

import javax.sql.DataSource;
import java.sql.Types;

public class Delete extends SqlUpdate
{
    public Delete(DataSource dataSource, String tableName, String fieldIdName, String sid)
    {
        super(dataSource, "DELETE FROM " + tableName + " WHERE " + fieldIdName + " = :" + sid);
        super.declareParameter(new SqlParameter(sid, Types.BIGINT));
    }
}
