/*
 * AbstractController.java
 * This file was last modified at 2018.12.03 20:05 by Victor N. Skurikhin.
 * $Id$
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */

package ru.otus.db.dao.jdbc;

import ru.otus.db.Executor;
import ru.otus.db.ResultHandler;
import ru.otus.db.dao.DAOController;
import ru.otus.exeptions.ExceptionSQL;
import ru.otus.exeptions.ExceptionThrowable;
import ru.otus.models.DataSet;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public abstract class AbstractController<E extends DataSet, K> implements DAOController<E, K>
{
    private DataSource dataSource;

    public AbstractController()
    {
        try {
            InitialContext initContext = new InitialContext();
            dataSource = (DataSource) initContext.lookup("java:comp/env/jdbc/PostgresMyDB");
        }
        catch (NamingException e) {
            throw new RuntimeException(e);
        }
    }

    public AbstractController(DataSource dataSource)
    {
        this.dataSource = dataSource;
    }

    public void setDataSource(DataSource dataSource)
    {
        this.dataSource = dataSource;
    }

    public Consumer<PreparedStatement> getConsumerLongId(long id)
    {
        return preparedStatement -> {
            try {
                preparedStatement.setLong(1, id);
            }
            catch (SQLException e) {
                throw new ExceptionSQL(e);
            }
        };
    }

    protected DataSource getDataSource()
    {
        return dataSource;
    }

    protected List<E> getArrayListAll(String sql, Function<ResultSet, E> f) throws SQLException
    {
        List<E> result = new ArrayList<>();
        Executor executor = new Executor(getDataSource().getConnection());

        executor.execQuery(sql, rs -> {
            while (rs.next()) {
                result.add(f.apply(rs));
            }
        });

        return result;
    }

    protected void execQueryEntityById(String sql, ResultHandler handler, Consumer<PreparedStatement> consumer)
    throws ExceptionThrowable
    {
        try {
            Executor executor = new Executor(getDataSource().getConnection());
            executor.execQuery(sql, handler, consumer);
        }
        catch (SQLException e) {
            throw new ExceptionThrowable(e);
        }
    }

    protected boolean delete(String sql, Long id) throws ExceptionThrowable
    {
        try {
            Executor executor = new Executor(getDataSource().getConnection());
            int count = executor.execUpdate(sql, getConsumerLongId(id));

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
