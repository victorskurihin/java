/*
 * EmpEntity.java
 * This file was last modified at 2018.12.03 17:05 by Victor N. Skurikhin.
 * $Id$
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */

package ru.otus.db.dao.jdbc;

import ru.otus.db.Executor;
import ru.otus.db.ResultHandler;
import ru.otus.exeptions.ExceptionSQL;
import ru.otus.exeptions.ExceptionThrowable;
import ru.otus.models.EmpEntity;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

public class EmpController extends AbstractController<EmpEntity, Long>
{
    public static final String SELECT_ALL = "SELECT" +
        " id, first_name, second_name, sur_name, department, city, job, salary, user_id, age" +
        " FROM emp_registry";
    public static final String SELECT_BY_ID = SELECT_ALL + " WHERE id = ?";
    public static final String INSERT = "INSERT INTO emp_registry" +
        " (id, first_name, second_name, sur_name, department, city, job, salary, user_id, age)" +
        " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    public static final String UPDATE = "UPDATE emp_registry SET" +
        " first_name = ?, second_name = ?, sur_name = ?, department = ?," +
        " city = ?, job = ?, salary = ?, user_id = ?, age = ?" +
        " WHERE id = ?";
    public static final String DELETE = "DELETE FROM dept_directory WHERE id = ?";

    EmpController(DataSource dataSource)
    {
        super(dataSource);
    }

    private EmpEntity setEmpEntity(EmpEntity entity, ResultSet resultSet) throws ExceptionSQL
    {
        try {
            entity.setId(resultSet.getLong("id"));
            entity.setFirstName(resultSet.getString("first_name"));
            entity.setSecondName(resultSet.getString("second_name"));
            entity.setSurName(resultSet.getString("sur_name"));
            entity.setDepartment(null); // TODO
            entity.setCity(resultSet.getString("city"));
            entity.setCity(resultSet.getString("city"));
        }
        catch (SQLException e) {
            throw new ExceptionSQL(e);
        }

        return entity;
    }

    private EmpEntity getEmpEntity(ResultSet resultSet) throws ExceptionSQL
    {
        EmpEntity entity = new EmpEntity();
        setEmpEntity(entity, resultSet);

        return entity;
    }

    @Override
    public List<EmpEntity> getAll() throws ExceptionThrowable
    {
        try {
            return getArrayListAll(SELECT_ALL, this::getEmpEntity);
        }
        catch (SQLException | ExceptionSQL e) {
            throw new ExceptionThrowable(e);
        }
    }

    @Override
    public EmpEntity getEntityById(Long id) throws ExceptionThrowable
    {
        AtomicBoolean exists = new AtomicBoolean(false);
        final EmpEntity result = new EmpEntity();

        ResultHandler handler = resultSet -> {
            if (resultSet.next()) {
                EmpController.this.setEmpEntity(result, resultSet);
                exists.set(true);
            }
        };

        execQueryEntityById(SELECT_BY_ID, handler, getConsumerLongId(id));

        return exists.get() ? result : null;
    }

    public Consumer<PreparedStatement> getConsumerInsertEmpEntity(EmpEntity entity)
    {
        return preparedStatement -> {
            try {
                preparedStatement.setLong(1, entity.getId());
                preparedStatement.setLong(2, entity.getParentId());
                preparedStatement.setString(3, entity.getTitle());
            }
            catch (SQLException e) {
                throw new ExceptionSQL(e);
            }
        };
    }

    public Consumer<PreparedStatement> getConsumerUpdateEmpEntity(EmpEntity entity)
    {
        return preparedStatement -> {
            try {
                preparedStatement.setLong(1, entity.getParentId());
                preparedStatement.setString(2, entity.getTitle());
                preparedStatement.setLong(3, entity.getId());
            }
            catch (SQLException e) {
                throw new ExceptionSQL(e);
            }
        };
    }

    @Override
    public EmpEntity update(EmpEntity entity) throws ExceptionThrowable
    {
        try {
            Executor executor = new Executor(getDataSource().getConnection());
            if (executor.execUpdate(UPDATE, getConsumerUpdateEmpEntity(entity)) < 1) {
                throw new SQLException("Error SQL update!");
            }

            return entity;
        }
        catch (SQLException | ExceptionSQL e) {
            throw new ExceptionThrowable(e);
        }
    }

    @Override
    public boolean delete(Long id) throws ExceptionThrowable
    {
        return delete(DELETE, id);
    }

    @Override
    public boolean create(EmpEntity entity) throws ExceptionThrowable
    {
        try {
            Executor executor = new Executor(getDataSource().getConnection());
            int count = executor.execUpdate(INSERT, getConsumerInsertEmpEntity(entity));

            return count > 0;
        }
        catch (SQLException | ExceptionSQL e) {
            throw new ExceptionThrowable(e);
        }
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
