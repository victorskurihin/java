package ru.otus.l091;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.Assert.*;

// ATTENTION THIS TEST CLEAR ALL TABLES with prefix 'ru_otus_l091' IN SCHEMA PUBLIC AT USER name!!!
// SELECT 'DROP TABLE IF EXISTS "' || tablename || '" CASCADE;'
//   FROM pg_tables
//  WHERE schemaname = 'public' AND (tablename LIKE 'ru_otus_l091%%' OR tablename = 'relationship');
interface Clean extends AutoCloseable {
    public void clear() throws SQLException;
}

class DBClean implements Clean {
    private final String name = "mb24681";
    private final Connection connection;

    protected DBClean() {
        connection = ConnectionHelper.getConnection(name, name);
    }

    private void execSimpleSQL(String update) throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(update);
        }
    }

    @Override
    public void clear() throws SQLException {
        ResultHandler handler;
        String generateDropTablesSQL = "SELECT " +
            "'DROP TABLE IF EXISTS \"' || tablename || '\" CASCADE;' AS sql " +
            "FROM pg_tables WHERE schemaname = 'public' AND " +
            "(tablename LIKE 'ru_otus_l091%%' OR tablename = 'relationship')";

        try (Statement stmt = connection.createStatement()) {
            stmt.execute(generateDropTablesSQL);
            ResultSet result = stmt.getResultSet();
            while (result.next()) {
                execSimpleSQL(result.getString("sql"));
            }
        }
    }

    @Override
    public void close() throws Exception {
        connection.close();
    }

    protected Connection getConnection() {
        return connection;
    }
}

public class DBServiceLogTest {
    private DBClean dbClean;
    private DBService dbService;

    @Before
    public void setUp() throws Exception {
        dbClean = new DBClean();
        dbService = new DBServiceLog();
        dbClean.clear();
    }

    @After
    public void tearDown() throws Exception {
        dbClean.clear();
        dbClean.close();
        dbService.close();
        dbService = null;
        dbClean = null;
    }

    @Test
    public void test() throws Exception {
        dbService.createTables(TestDataSetClass.class);
        dbService.save(new TestDataSetClass(13));
        TestDataSetClass testDataSetClass = dbService.load(13, TestDataSetClass.class);
    }
}