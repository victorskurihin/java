package ru.otus.l101.db;

/*
 * Created by VSkurikhin at winter 2018.
 */

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.otus.l101.dao.*;
import ru.otus.l101.dataset.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.Assert.*;

public class DBServiceMyImplTest {
    private DBService dbService;
    private Connection connection;

    @Before
    public void setUp() throws Exception {
        connection = ConnectionHelper.getConnection(
            DBConf.dbName, DBConf.userName, DBConf.password
        );
        dbService = new DBServiceMyImpl(
            UserDataSetMyDAO.class, AddressDataSetMyDAO.class,
            PhoneDataSetMyDAO.class, TestDataSetClassMyDAO.class,
            TestComplexDataSetClassMyDAO.class
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
            "(tablename LIKE 'ru_otus_l101%%' OR tablename = 'relationship')";

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

    private void reCreateTables() throws Exception {
        clear();
        dropTables("\"java_util_Set ru_otus_l101_dataset_PhoneDataSet\"");
        dbService.createTables(UserDataSet.class);
        dbService.createTables(AddressDataSet.class);
        dbService.createTables(PhoneDataSet.class);
    }

    @Test
    public void create() {
        dbService.createTables(TestDataSetClass.class);
    }

    @Test
    public void testPhoneDataSet() throws Exception {
        reCreateTables();
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

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
