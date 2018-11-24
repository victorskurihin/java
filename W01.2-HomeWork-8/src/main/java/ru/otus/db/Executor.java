package ru.otus.db;

/*
 * Created by VSkurikhin at winter 2018.
 */

import ru.otus.exeptions.ExceptionSQL;

import java.sql.*;
import java.util.function.Consumer;

public class Executor
{
    private final Connection connection;

    public Executor(Connection connection)
    {
        this.connection = connection;
    }

    public void execQuery(String query, ResultHandler handler, Consumer<PreparedStatement> c)
    throws ExceptionSQL
    {
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            if (null != c) c.accept(stmt);
            ResultSet result = stmt.executeQuery();
            handler.handle(result);
        }
        catch (SQLException e) {
            throw new ExceptionSQL(e);
        }
    }

    public void execQuery(String query, ResultHandler handler) throws ExceptionSQL
    {
        execQuery(query, handler, null);
    }

    public int execUpdate(String update, Consumer<PreparedStatement> c) throws ExceptionSQL
    {
        try (PreparedStatement stmt = connection.prepareStatement(update)) {
            if (null != c) c.accept(stmt);
            stmt.executeUpdate();

            return stmt.getUpdateCount();
        }
        catch (SQLException e) {
            throw new ExceptionSQL(e);
        }
    }

    public int execUpdate(String update) throws ExceptionSQL
    {
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(update);
            return stmt.getUpdateCount();
        }
        catch (SQLException e) {
            throw new ExceptionSQL(e);
        }
    }

    protected Connection getConnection()
    {
        return connection;
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
