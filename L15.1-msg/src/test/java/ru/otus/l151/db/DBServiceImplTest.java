package ru.otus.l151.db;

/*
 * Created by VSkurikhin at winter 2018.
 */

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.otus.l151.dataset.*;

public class DBServiceImplTest {
    public static final String USER_NAME = "User Name 13";
    DBService dbService;

    @Before
    public void setUp() throws Exception {
        dbService = new DBServiceImpl();
    }

    @After
    public void tearDown() throws Exception {
        dbService.close();
        dbService = null;
    }

    @Test
    public void testLoadById() {
        String status = dbService.getLocalStatus();
        System.out.println("Status: " + status);

        UserDataSet expectedUserDataSet = new UserDataSet(
            1, USER_NAME, new AddressDataSet("Elm Street 1984")
        );
        expectedUserDataSet.addPhone(new PhoneDataSet("1000003"));
        dbService.save(expectedUserDataSet);
        UserDataSet testUserDataSet = dbService.load(1, UserDataSet.class);
        Assert.assertEquals(expectedUserDataSet, testUserDataSet);
    }

    @Test
    public void testLoadByName() {
        String status = dbService.getLocalStatus();
        System.out.println("Status: " + status);

        UserDataSet expectedUserDataSet = new UserDataSet(
            1, USER_NAME, new AddressDataSet("Elm Street 2010")
        );
        expectedUserDataSet.addPhone(new PhoneDataSet(-1, "1000003"));
        expectedUserDataSet.addPhone(new PhoneDataSet(-1, "1000033"));
        dbService.save(expectedUserDataSet);
        UserDataSet testUserDataSet = dbService.loadByName(USER_NAME);
        Assert.assertEquals(expectedUserDataSet, testUserDataSet);
    }

    @Test
    public void testCache() throws Exception {
        final int size = 20;
        int j = 0;

        for (int i = 0; i < size; i++) {
            UserDataSet expectedUserDataSet = new UserDataSet(
                i,"User " + i, new AddressDataSet("Street " + i)
            );
            expectedUserDataSet.addPhone(
                new PhoneDataSet("100" + String.format("%04d", j++))
            );
            expectedUserDataSet.addPhone(
                new PhoneDataSet("100" + String.format("%04d", j++))
            );
            dbService.save(expectedUserDataSet);
        }

        for (int i = (size - (DBServiceImpl.cacheSize + 5)); i < size; i++) {
            UserDataSet testUserDataSet = dbService.load(i, UserDataSet.class);
        }

        Assert.assertEquals(DBServiceImpl.cacheSize, dbService.getHitCount());
        Assert.assertEquals(5, dbService.getMissCount());
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
