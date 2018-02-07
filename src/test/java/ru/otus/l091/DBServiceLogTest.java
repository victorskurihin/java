package ru.otus.l091;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.Assert.*;

interface Clean extends AutoCloseable {
    public void clear() throws SQLException;
}

// ATTENTION! THIS TEST WILL DROP ALL THE TABLES WITH PREFIX 'ru_otus_l091'
// IN THE SCHEMA PUBLIC AT USER name!!!
class DBClean implements Clean {
    private final String name = "vnsk";
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
}

public class DBServiceLogTest {
    private DBClean dbClean;
    private DBService dbService;

    @Before
    public void setUp() throws Exception {
        dbClean = new DBClean();
        dbService = new DBServiceLog();
    }

    @After
    public void tearDown() throws Exception {
        dbClean.close();
        dbService.close();
        dbService = null;
        dbClean = null;
    }

    private void reCreateTables() throws Exception {
        dbClean.clear();
        dbService.createTables(TestComplexDataSetClass.class);
    }

    @Test
    public void test1() throws Exception {
        reCreateTables();
        TestDataSetClass expectedTestDataSetClass = new TestDataSetClass(13);
        dbService.save(expectedTestDataSetClass);
        TestDataSetClass testDataSetClass = dbService.load(13, TestDataSetClass.class);
        Assert.assertEquals(expectedTestDataSetClass, testDataSetClass);
    }

    @Test
    public void test2() throws Exception {
        reCreateTables();
        TestComplexDataSetClass expectedTestComplexDataSetClass = new TestComplexDataSetClass(13);
        expectedTestComplexDataSetClass.setTest("bla");
        dbService.save(expectedTestComplexDataSetClass);
        TestComplexDataSetClass testComplexDataSetClass = dbService.load(13, TestComplexDataSetClass.class);
        System.out.println("testComplexDataSetClass = " + testComplexDataSetClass);
        System.out.println("expectedTestComplexDataSetClass = " + expectedTestComplexDataSetClass);
//        Assert.assertEquals(expectedTestComplexDataSetClass, testComplexDataSetClass);
    }
}