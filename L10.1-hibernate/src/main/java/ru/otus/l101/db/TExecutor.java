package ru.otus.l101.db;

/*
 * Created by VSkurikhin at winter 2018.
 */

import ru.otus.l101.dataset.DataSet;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TExecutor extends Executor {
    public TExecutor(Connection connection) {
        super(connection);
    }

    public <T extends DataSet> T execQuery(String query, TResultHandler<T> handler)
        throws SQLException {

        try(Statement stmt = super.getConnection().createStatement()) {
            stmt.execute(query);
            ResultSet result = stmt.getResultSet();
            return handler.handle(result);
        }
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
