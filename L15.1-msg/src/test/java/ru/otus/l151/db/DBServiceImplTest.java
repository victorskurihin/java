package ru.otus.l151.db;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.otus.l151.app.DBService;
import ru.otus.l151.app.MessageSystemContext;
import ru.otus.l151.dataset.UserDataSet;
import ru.otus.l151.messageSystem.Address;
import ru.otus.l151.messageSystem.MessageSystem;

import static org.junit.Assert.*;

public class DBServiceImplTest {
    private DBServiceImpl dbService;

    public static final String USER_NAME = "user";

    @Before
    public void setUp() throws Exception {
        MessageSystem messageSystem = new MessageSystem();
        MessageSystemContext context = new MessageSystemContext(messageSystem);
        Address dbAddress = new Address("DB");

        context.setDbAddress(dbAddress);

        dbService = new DBServiceImpl(context, dbAddress);

        dbService.init();
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