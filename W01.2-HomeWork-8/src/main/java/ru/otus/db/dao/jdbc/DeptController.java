package ru.otus.db.dao.jdbc;

/*
 * Created by VSkurikhin at winter 2018.
 */

import ru.otus.exeptions.ExceptionSQL;
import ru.otus.exeptions.ExceptionThrowable;
import ru.otus.models.DeptEntity;

import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

public class DeptController extends AbstractController <DeptEntity, Long>
{
    public static final String SELECT_ALL = "SELECT * FROM dep_directory";

    DeptController(DataSource dataSource)
    {
        super(dataSource);
    }

    @Override
    public List<DeptEntity> getAll() throws ExceptionThrowable
    {
        try {
            return getArrayListAll(SELECT_ALL, resultSet -> {
                DeptEntity deptEntity = new DeptEntity();
                try {
                    deptEntity.setId(resultSet.getLong("id"));
                    deptEntity.setParentId(resultSet.getLong("pid"));
                    deptEntity.setTitle(resultSet.getString("title"));
                } catch (SQLException e) {
                    throw new ExceptionSQL(e);
                }
                return deptEntity;
            });
        } catch (SQLException e) {
            throw new ExceptionThrowable(e);
        }
    }

    @Override
    public DeptEntity getEntityById(Long id)
    {
        return null;
    }

    @Override
    public DeptEntity update(DeptEntity entity)
    {
        return null;
    }

    @Override
    public boolean delete(Long id)
    {
        return false;
    }

    @Override
    public boolean create(DeptEntity entity)
    {
        return false;
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
