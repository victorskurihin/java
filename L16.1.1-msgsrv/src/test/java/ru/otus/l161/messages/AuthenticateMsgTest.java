package ru.otus.l161.messages;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.otus.l161.dataset.AddressDataSet;
import ru.otus.l161.dataset.UserDataSet;

import java.lang.reflect.Field;

public class AuthenticateMsgTest {
    private static final String NAME = "NAME";
    private static final String PASSWORD = "PASSWORD";
    private static final String ADDRESS = "ADDRESS";
    private final Address from = new Address();
    private final Address to = new Address();
    private final String sid = "0";

    private AddressDataSet addressDataSet = new AddressDataSet(1, ADDRESS);
    private UserDataSet userDataSet = new UserDataSet(-1, NAME, PASSWORD, addressDataSet);

    private AuthenticateMsg authenticateMsg;

    @Before
    public void setUp() throws Exception {
        authenticateMsg = new AuthenticateMsg(from, sid, to, userDataSet);
    }

    @After
    public void tearDown() throws Exception {
        authenticateMsg = null;
    }

    @Test
    public void getId() {
        Assert.assertEquals(AuthenticateMsg.ID, authenticateMsg.getId());
    }

    @Test
    public void equals() {
        AddressDataSet addressDataSetTest = new AddressDataSet(1, ADDRESS);
        UserDataSet userDataSetTest = new UserDataSet(-1, NAME, PASSWORD, addressDataSetTest);
        AuthenticateMsg authenticateMsgTest = new AuthenticateMsg(from, sid, to, userDataSetTest);
        Assert.assertEquals(authenticateMsgTest, authenticateMsg);
    }

    @Test
    public void getUser() {
        AddressDataSet addressDataSetTest = new AddressDataSet(1, ADDRESS);
        UserDataSet userDataSetTest = new UserDataSet(-1, NAME, PASSWORD, addressDataSetTest);
        Assert.assertEquals(userDataSetTest, authenticateMsg.getUser());
    }

    @Test
    public void createAnswer() throws NoSuchFieldException, IllegalAccessException {
        AuthenticatedMsg authenticatedMsgTest = authenticateMsg.createAnswer(true, "Ok");
        Field authField = authenticatedMsgTest.getClass().getDeclaredField("auth");
        authField.setAccessible(true);
        int authId = authField.getInt(authenticatedMsgTest);
        AuthenticatedMsg authenticatedMsgExpected = new AuthenticatedMsg(
            to, sid, from, NAME, true, authId
        );
        authenticatedMsgExpected.setMessage("Ok");
        Assert.assertEquals(authenticatedMsgExpected, authenticatedMsgTest);
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
