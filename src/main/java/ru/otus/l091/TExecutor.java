package ru.otus.l091;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TExecutor extends Executor {
    public TExecutor(Connection connection) {
        super(connection);
    }

    public <T> T execQuery(String query, TResultHandler<T> handler)
        throws SQLException {

        try(Statement stmt = super.getConnection().createStatement()) {
            stmt.execute(query);
            ResultSet result = stmt.getResultSet();
            return handler.handle(result);
        }
    }
}
