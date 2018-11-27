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
import ru.otus.models.UserEntity;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

public class UserController extends AbstractController <UserEntity, Long>
{
    public static final String SELECT_ALL = "SELECT id, login, password FROM users";
    public static final String SELECT_BY_ID = "SELECT id, login, password FROM users WHERE id = ?";
    public static final String INSERT = "INSERT INTO users (id, login, password) VALUES (?, ?, ?)";
    public static final String UPDATE = "UPDATE users SET login = ?, password = ? WHERE id = ?";
    public static final String DELETE = "DELETE FROM users WHERE id = ?";

    UserController(DataSource dataSource)
    {
        super(dataSource);
    }

    private UserEntity setUserEntity(UserEntity entity, ResultSet resultSet) throws ExceptionSQL
    {
        try {
            entity.setId(resultSet.getLong("id"));
            entity.setLogin(resultSet.getString("login"));
            entity.setPassword(resultSet.getString("password"));
        } catch (SQLException e) {
            throw new ExceptionSQL(e);
        }

        return entity;
    }

    private UserEntity getUserEntity(ResultSet resultSet) throws ExceptionSQL
    {
        UserEntity entity = new UserEntity();
        setUserEntity(entity, resultSet);

        return entity;
    }

    @Override
    public List<UserEntity> getAll() throws ExceptionThrowable
    {
        try {
            return getArrayListAll(SELECT_ALL, this::getUserEntity);
        } catch (SQLException | ExceptionSQL e) {
            throw new ExceptionThrowable(e);
        }
    }

    @Override
    public UserEntity getEntityById(Long id) throws ExceptionThrowable
    {
        AtomicBoolean exists = new AtomicBoolean(false);
        final UserEntity result = new UserEntity();

        ResultHandler handler = resultSet -> {
            if (resultSet.next()) {
                UserController.this.setUserEntity(result, resultSet);
                exists.set(true);
            }
        };

        execQueryEntityById(SELECT_BY_ID, handler, getConsumerLongId(id));

        return exists.get() ? result : null;
    }

    public Consumer<PreparedStatement> getConsumerInsertUserEntity(UserEntity entity)
    {
        return preparedStatement -> {
            try {
                preparedStatement.setLong(1, entity.getId());
                preparedStatement.setString(2, entity.getLogin());
                preparedStatement.setString(3, entity.getPassword());
            } catch (SQLException e) {
                throw new ExceptionSQL(e);
            }
        };
    }

    public Consumer<PreparedStatement> getConsumerUpdateUserEntity(UserEntity entity)
    {
        return preparedStatement -> {
            try {
                preparedStatement.setString(1, entity.getLogin());
                preparedStatement.setString(2, entity.getPassword());
                preparedStatement.setLong(3, entity.getId());
            } catch (SQLException e) {
                throw new ExceptionSQL(e);
            }
        };
    }

    @Override
    public UserEntity update(UserEntity entity) throws ExceptionThrowable
    {
        try {
            Executor executor = new Executor(getDataSource().getConnection());
            if (executor.execUpdate(UPDATE, getConsumerUpdateUserEntity(entity)) < 1) {
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
    public boolean create(UserEntity entity) throws ExceptionThrowable
    {
        try {
            Executor executor = new Executor(getDataSource().getConnection());
            int count = executor.execUpdate(INSERT, getConsumerInsertUserEntity(entity));

            return count > 0;
        } catch (SQLException | ExceptionSQL e) {
            throw new ExceptionThrowable(e);
        }
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
