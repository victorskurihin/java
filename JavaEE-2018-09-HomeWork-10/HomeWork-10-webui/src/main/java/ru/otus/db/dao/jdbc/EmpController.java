/*
 * EmpController.java
 * This file was last modified at 2018.12.03 20:57 by Victor N. Skurikhin.
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
import java.sql.Types;
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
            // TODO NULL work
            DeptController deptController = new DeptController(getDataSource());
            entity.setDepartment(deptController.getEntityById(resultSet.getLong("department")));
            entity.setCity(resultSet.getString("city"));
            entity.setJob(resultSet.getString("job"));
            entity.setSalary(resultSet.getLong("salary"));
            // TODO NULL work
            UserController userController = new UserController(getDataSource());
            entity.setUser(userController.getEntityById(resultSet.getLong("user_id")));
            entity.setAge(resultSet.getLong("age"));
        }
        catch (SQLException e) {
            throw new ExceptionSQL(e);
        }
        catch (ExceptionThrowable exceptionThrowable) {
            throw new RuntimeException(exceptionThrowable); // TODO castom exception
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
                preparedStatement.setString(2, entity.getFirstName());
                preparedStatement.setString(3, entity.getSecondName());
                preparedStatement.setString(4, entity.getSurName());

                if (null != entity.getDepartment()) {
                    preparedStatement.setLong(5, entity.getDepartment().getId());
                }
                else {
                    preparedStatement.setNull(5, Types.BIGINT);
                }

                preparedStatement.setString(6, entity.getCity());
                preparedStatement.setString(7, entity.getJob());
                preparedStatement.setLong(8, entity.getSalary());

                if (null != entity.getUser()) {
                    preparedStatement.setLong(9, entity.getUser().getId());
                }
                else {
                    preparedStatement.setNull(9, Types.BIGINT);
                }

                preparedStatement.setLong(10, entity.getAge());
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
                preparedStatement.setString(1, entity.getFirstName());
                preparedStatement.setString(2, entity.getSecondName());
                preparedStatement.setString(3, entity.getSurName());

                if (null != entity.getDepartment()) {
                    preparedStatement.setLong(4, entity.getDepartment().getId());
                }
                else {
                    preparedStatement.setNull(4, Types.BIGINT);
                }

                preparedStatement.setString(5, entity.getCity());
                preparedStatement.setString(6, entity.getJob());
                preparedStatement.setLong(7, entity.getSalary());
                preparedStatement.setLong(8, entity.getUser().getId());

                if (null != entity.getUser()) {
                    preparedStatement.setLong(8, entity.getUser().getId());
                }
                else {
                    preparedStatement.setNull(8, Types.BIGINT);
                }

                preparedStatement.setLong(9, entity.getAge());
                preparedStatement.setLong(10, entity.getId());
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
