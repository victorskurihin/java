package ru.otus.l161.db;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.otus.l161.dataset.UserDataSet;
import ru.otus.l161.messages.Address;

public class DBServiceImplTest {
    private DBServiceImpl dbService;

    public static final String USER_NAME = "user";

    @Before
    public void setUp() throws Exception {
        Address dbAddress = new Address("DB");

        dbService = new DBServiceImpl(dbAddress);
    }

    @After
    public void tearDown() throws Exception {
        dbService = null;
    }

    @Test
    public void getUserId() {
        UserDataSet expectedUserDataSet = new UserDataSet(
            1, USER_NAME, "password", null
        );
        dbService.save(expectedUserDataSet);
        UserDataSet testUserDataSet = dbService.load(1, UserDataSet.class);
        Assert.assertEquals(expectedUserDataSet, testUserDataSet);
    }
}