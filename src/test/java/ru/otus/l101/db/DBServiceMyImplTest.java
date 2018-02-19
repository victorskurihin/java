package ru.otus.l101.db;

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
        dbService.createTables(UserDataSet.class);
        dbService.createTables(PhoneDataSet.class);
    }

    @Test
    public void create() {
        dbService.createTables(TestDataSetClass.class);
    }

    @Test
    public void testPhoneDataSet() throws Exception {
//        reCreateTables();
    }

//    @Test
//    public void testUserDataSet() throws Exception {
//        reCreateTables();
//        UserDataSet expectedUserDataSet = new UserDataSet(
//            13, "Test User 13",
//            new AddressDataSet("Elm Street 1984"),
//
//            new PhoneDataSet("1234")
//        );
//        dbService.save(expectedUserDataSet);
//        UserDataSet testUserDataSet = dbService.load(13, UserDataSet.class);
//        Assert.assertEquals(expectedUserDataSet, testUserDataSet);
//    }
}