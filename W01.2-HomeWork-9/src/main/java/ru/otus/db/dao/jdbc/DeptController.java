/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package ru.otus.db.dao.jdbc;

/*
 * Created by VSkurikhin at winter 2018.
 */

import ru.otus.db.Executor;
import ru.otus.db.ResultHandler;
import ru.otus.exeptions.ExceptionSQL;
import ru.otus.exeptions.ExceptionThrowable;
import ru.otus.models.DeptEntity;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

public class DeptController extends AbstractController <DeptEntity, Long>
{
    public static final String SELECT_ALL = "SELECT id, pid, title FROM dep_directory";
    public static final String SELECT_BY_ID = "SELECT id, pid, title FROM dep_directory WHERE id = ?";
    public static final String INSERT = "INSERT INTO dep_directory (id, pid, title) VALUES (?, ?, ?)";
    public static final String UPDATE = "UPDATE dep_directory SET pid = ?, title = ? WHERE id = ?";
    public static final String DELETE = "DELETE FROM dep_directory WHERE id = ?";

    DeptController(DataSource dataSource)
    {
        super(dataSource);
    }

    private DeptEntity setDeptEntity(DeptEntity entity, ResultSet resultSet) throws ExceptionSQL
    {
        try {
            entity.setId(resultSet.getLong("id"));
            entity.setParentId(resultSet.getLong("pid"));
            entity.setTitle(resultSet.getString("title"));
        } catch (SQLException e) {
            throw new ExceptionSQL(e);
        }

        return entity;
    }

    private DeptEntity getDeptEntity(ResultSet resultSet) throws ExceptionSQL
    {
        DeptEntity entity = new DeptEntity();
        setDeptEntity(entity, resultSet);

        return entity;
    }

    @Override
    public List<DeptEntity> getAll() throws ExceptionThrowable
    {
        try {
            return getArrayListAll(SELECT_ALL, this::getDeptEntity);
        } catch (SQLException | ExceptionSQL e) {
            throw new ExceptionThrowable(e);
        }
    }

    @Override
    public DeptEntity getEntityById(Long id) throws ExceptionThrowable
    {
        AtomicBoolean exists = new AtomicBoolean(false);
        final DeptEntity result = new DeptEntity();

        ResultHandler handler = resultSet -> {
            if (resultSet.next()) {
                DeptController.this.setDeptEntity(result, resultSet);
                exists.set(true);
            }
        };

        execQueryEntityById(SELECT_BY_ID, handler, getConsumerLongId(id));

        return exists.get() ? result : null;
    }

    public Consumer<PreparedStatement> getConsumerInsertDeptEntity(DeptEntity entity)
    {
        return preparedStatement -> {
            try {
                preparedStatement.setLong(1, entity.getId());
                preparedStatement.setLong(2, entity.getParentId());
                preparedStatement.setString(3, entity.getTitle());
            } catch (SQLException e) {
                throw new ExceptionSQL(e);
            }
        };
    }

    public Consumer<PreparedStatement> getConsumerUpdateDeptEntity(DeptEntity entity)
    {
        return preparedStatement -> {
            try {
                preparedStatement.setLong(1, entity.getParentId());
                preparedStatement.setString(2, entity.getTitle());
                preparedStatement.setLong(3, entity.getId());
            } catch (SQLException e) {
                throw new ExceptionSQL(e);
            }
        };
    }

    @Override
    public DeptEntity update(DeptEntity entity) throws ExceptionThrowable
    {
        try {
            Executor executor = new Executor(getDataSource().getConnection());
            if (executor.execUpdate(UPDATE, getConsumerUpdateDeptEntity(entity)) < 1) {
                throw new SQLException("Error SQL update!");
            }

            return entity;
        } catch (SQLException | ExceptionSQL e) {
            throw new ExceptionThrowable(e);
        }
    }

    @Override
    public boolean delete(Long id) throws ExceptionThrowable
    {
        try {
            Executor executor = new Executor(getDataSource().getConnection());
            int count = executor.execUpdate(DELETE, getConsumerLongId(id));

            return count > 0;
        } catch (SQLException | ExceptionSQL e) {
            throw new ExceptionThrowable(e);
        }
    }

    @Override
    public boolean create(DeptEntity entity) throws ExceptionThrowable
    {
        try {
            Executor executor = new Executor(getDataSource().getConnection());
            int count = executor.execUpdate(INSERT, getConsumerInsertDeptEntity(entity));

            return count > 0;
        } catch (SQLException | ExceptionSQL e) {
            throw new ExceptionThrowable(e);
        }
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
