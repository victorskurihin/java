package ru.otus.l111.db;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.otus.l111.dao.*;
import ru.otus.l111.dataset.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBServiceOtusImplTest {
    private DBService dbService;
    private Connection connection;

    @Before
    public void setUp() throws Exception {
        connection = ConnectionHelper.getConnection(
            DBConf.dbName, DBConf.userName, DBConf.password
        );
        dbService = new DBServiceMyImpl(
            UserDataSetOtusDAO.class, AddressDataSetOtusDAO.class,
            PhoneDataSetOtusDAO.class, TestDataSetOtusDAO.class
        );
    }

    @After
    public void tearDown() throws Exception {
        dbService.shutdown();
        dbService = null;
    }

    public void dropTables(String tableName) throws SQLException {
        String dropTablesSQL = "DROP TABLE IF EXISTS %s CASCADE";

        try (Statement stmt = connection.createStatement()) {
            stmt.execute(String.format(dropTablesSQL, tableName));
        }
    }

    private void execSimpleSQL(String update) {
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(update);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void clear() {
        ResultHandler handler;
        String generateDropTablesSQL = "SELECT " +
            "'DROP TABLE IF EXISTS \"' || tablename || '\" CASCADE;' AS sql " +
            "FROM pg_tables WHERE schemaname = 'public' AND " +
            "(tablename LIKE 'ru_otus_l111%%' OR tablename = 'relationship')";

        try (Statement stmt = connection.createStatement()) {
            stmt.execute(generateDropTablesSQL);
            ResultSet result = stmt.getResultSet();
            while (result.next()) {
                execSimpleSQL(result.getString("sql"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void reCreateTables() throws Exception {
        clear();
        dropTables("\"java_util_Set ru_otus_l111_dataset_PhoneDataSet\"");
        dbService.createTables(AddressDataSet.class);
        dbService.createTables(PhoneDataSet.class);
        dbService.createTables(UserDataSet.class);
    }

    @Test
    public void create() {
        dbService.createTables(TestDataSet.class);
    }

    @Test
    public void testUserDataSet() throws Exception {
        reCreateTables();
        UserDataSet expectedUserDataSet = new UserDataSet(
            12, "Test User 12",
            new AddressDataSet(1984, "Elm Street 1984")
        );
        expectedUserDataSet.addPhone(new PhoneDataSet(1, "1000003"));
        expectedUserDataSet.addPhone(new PhoneDataSet(2, "1000033"));
        dbService.save(expectedUserDataSet);
        UserDataSet testUserDataSet = dbService.load(12, UserDataSet.class);
        Assert.assertEquals(expectedUserDataSet, testUserDataSet);
    }
}
