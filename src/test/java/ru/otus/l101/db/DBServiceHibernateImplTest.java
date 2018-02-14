package ru.otus.l101.db;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.otus.l101.dataset.PhoneDataSet;
import ru.otus.l101.dataset.UserDataSet;

public class DBServiceHibernateImplTest {
    public static final String USER_NAME = "User Name 13";
    DBService dbService;

    @Before
    public void setUp() throws Exception {
        dbService = new DBServiceHibernateImpl();
    }

    @After
    public void tearDown() throws Exception {
        dbService.close();
        dbService = null;
    }

    @Test
    public void test1() {
        String status = dbService.getLocalStatus();
        System.out.println("Status: " + status);

        UserDataSet expectedUserDataSet = new UserDataSet(
            1, USER_NAME, new PhoneDataSet("1000003")
        );
        dbService.save(expectedUserDataSet);
        UserDataSet testUserDataSet = (UserDataSet) dbService.load(1, UserDataSet.class);
        Assert.assertEquals(expectedUserDataSet, testUserDataSet);
    }

    @Test
    public void testName() {
        String status = dbService.getLocalStatus();
        System.out.println("Status: " + status);

        UserDataSet expectedUserDataSet = new UserDataSet(
            1, USER_NAME, new PhoneDataSet("1000003")
        );
        dbService.save(expectedUserDataSet);
        UserDataSet testUserDataSet = dbService.loadByName(USER_NAME);
        Assert.assertEquals(expectedUserDataSet, testUserDataSet);
    }
}