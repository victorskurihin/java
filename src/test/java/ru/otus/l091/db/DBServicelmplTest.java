package ru.otus.l091.db;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.otus.l091.dataset.TestComplexDataSetClass;
import ru.otus.l091.dataset.TestDataSetClass;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

interface Clean extends AutoCloseable {
    public void clear() throws SQLException;
}

// ATTENTION! THIS TEST WILL DROP ALL THE TABLES WITH PREFIX 'ru_otus_l091'
// IN THE SCHEMA PUBLIC AT USER name!!!
class DBClean implements Clean {
    private final Connection connection;

    protected DBClean() {
        connection = ConnectionHelper.getConnection(
            DBConf.dbName, DBConf.userName, DBConf.password
        );
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

public class DBServicelmplTest {
    private DBClean dbClean;
    private DBService dbService;

    @Before
    public void setUp() throws Exception {
        dbClean = new DBClean();
        dbService = new DBServiceImpl();
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
    public void testDataSetClass() throws Exception {
        reCreateTables();
        TestDataSetClass expectedTestDataSetClass = new TestDataSetClass(13);
        dbService.save(expectedTestDataSetClass);
        TestDataSetClass testDataSetClass = dbService.load(13, TestDataSetClass.class);
        Assert.assertEquals(expectedTestDataSetClass, testDataSetClass);
    }

    @Test
    public void testComplexDataSetClass() throws Exception {
        reCreateTables();
        TestComplexDataSetClass expectedTestComplexDataSetClass = new TestComplexDataSetClass(13);
        expectedTestComplexDataSetClass.setTest("tezd");
        dbService.save(expectedTestComplexDataSetClass);
        TestComplexDataSetClass testComplexDataSetClass = dbService.load(13, TestComplexDataSetClass.class);
        Assert.assertEquals(expectedTestComplexDataSetClass, testComplexDataSetClass);
    }
}