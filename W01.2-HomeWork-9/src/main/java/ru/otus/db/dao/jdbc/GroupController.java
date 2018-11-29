/*
 * GroupController.java
 * This file was last modified at 29.11.18 10:42 by Victor N. Skurikhin.
 * $Id$
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */

package ru.otus.db.dao.jdbc;

import ru.otus.db.Executor;
import ru.otus.db.ResultHandler;
import ru.otus.exeptions.ExceptionSQL;
import ru.otus.exeptions.ExceptionThrowable;
import ru.otus.models.GroupEntity;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

public class GroupController extends AbstractController <GroupEntity, Long>
{
    public static final String SELECT_ALL = "SELECT id, groupname, login FROM user_groups";
    public static final String SELECT_BY_ID = SELECT_ALL + " WHERE id = ?";
    public static final String INSERT = "INSERT INTO user_groups (id, groupname, login) VALUES (?, ?, ?)";
    public static final String UPDATE = "UPDATE user_groups SET groupname = ?, login = ? WHERE id = ?";
    public static final String DELETE = "DELETE FROM user_groups WHERE id = ?";

    GroupController(DataSource dataSource)
    {
        super(dataSource);
    }

    private GroupEntity setGroupEntity(GroupEntity entity, ResultSet resultSet) throws ExceptionSQL
    {
        try {
            entity.setId(resultSet.getLong("id"));
            entity.setGroupname(resultSet.getString("groupname"));
            entity.setLogin(resultSet.getString("login"));
        } catch (SQLException e) {
            throw new ExceptionSQL(e);
        }

        return entity;
    }

    private GroupEntity getGroupEntity(ResultSet resultSet) throws ExceptionSQL
    {
        GroupEntity entity = new GroupEntity();
        setGroupEntity(entity, resultSet);

        return entity;
    }

    @Override
    public List<GroupEntity> getAll() throws ExceptionThrowable
    {
        try {
            return getArrayListAll(SELECT_ALL, this::getGroupEntity);
        } catch (SQLException | ExceptionSQL e) {
            throw new ExceptionThrowable(e);
        }
    }

    @Override
    public GroupEntity getEntityById(Long id) throws ExceptionThrowable
    {
        AtomicBoolean exists = new AtomicBoolean(false);
        final GroupEntity result = new GroupEntity();

        ResultHandler handler = resultSet -> {
            if (resultSet.next()) {
                GroupController.this.setGroupEntity(result, resultSet);
                exists.set(true);
            }
        };

        execQueryEntityById(SELECT_BY_ID, handler, getConsumerLongId(id));

        return exists.get() ? result : null;
    }

    public Consumer<PreparedStatement> getConsumerInsertGroupEntity(GroupEntity entity)
    {
        return preparedStatement -> {
            try {
                preparedStatement.setLong(1, entity.getId());
                preparedStatement.setString(2, entity.getGroupname());
                preparedStatement.setString(3, entity.getLogin());
            } catch (SQLException e) {
                throw new ExceptionSQL(e);
            }
        };
    }

    public Consumer<PreparedStatement> getConsumerUpdateGroupEntity(GroupEntity entity)
    {
        return preparedStatement -> {
            try {
                preparedStatement.setString(1, entity.getGroupname());
                preparedStatement.setString(2, entity.getLogin());
                preparedStatement.setLong(3, entity.getId());
            } catch (SQLException e) {
                throw new ExceptionSQL(e);
            }
        };
    }

    @Override
    public GroupEntity update(GroupEntity entity) throws ExceptionThrowable
    {
        try {
            Executor executor = new Executor(getDataSource().getConnection());
            if (executor.execUpdate(UPDATE, getConsumerUpdateGroupEntity(entity)) < 1) {
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
        return delete(DELETE, id);
    }

    @Override
    public boolean create(GroupEntity entity) throws ExceptionThrowable
    {
        try {
            Executor executor = new Executor(getDataSource().getConnection());
            int count = executor.execUpdate(INSERT, getConsumerInsertGroupEntity(entity));

            return count > 0;
        } catch (SQLException | ExceptionSQL e) {
            throw new ExceptionThrowable(e);
        }
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
