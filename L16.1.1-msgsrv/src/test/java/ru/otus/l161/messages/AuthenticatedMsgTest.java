package ru.otus.l161.messages;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class AuthenticatedMsgTest {
    public static final String TEST = "test";
    private final Address from = new Address();
    private final Address to = new Address();
    private final String sid = "0";
    private final String name = "name";
    private AuthenticatedMsg authenticatedMsg;

    @Before
    public void setUp() throws Exception {
        authenticatedMsg = new AuthenticatedMsg(from, sid, to, name, true, 1);
    }

    @After
    public void tearDown() throws Exception {
        authenticatedMsg = null;
    }

    @Test
    public void isPositive() {
        Assert.assertTrue(authenticatedMsg.isPositive());
    }

    @Test
    public void getUser() {
        Assert.assertEquals(name, authenticatedMsg.getUser());
    }

    @Test
    public void getSessionId() {
        Assert.assertEquals(sid, authenticatedMsg.getSessionId());
    }

    @Test
    public void getAuth() {
        Assert.assertEquals(1, authenticatedMsg.getAuth());
    }

    @Test
    public void setMessage() {
        authenticatedMsg.setMessage(TEST);
        Assert.assertEquals(TEST, authenticatedMsg.getMessage());
    }

    @Test
    public void getMessage() {
        Assert.assertNull(authenticatedMsg.getMessage());
    }

    @Test
    public void forward() {
        AuthenticatedMsg authenticatedMsgTest = new AuthenticatedMsg(to, sid, from, name, true, 1);
        Assert.assertEquals(authenticatedMsgTest, authenticatedMsg.forward(to, from));
    }

    @Test
    public void getId() {
        Assert.assertEquals(AuthenticatedMsg.ID, authenticatedMsg.getId());
    }

    @Test
    public void equals() {
        AuthenticatedMsg authenticatedMsgTest = new AuthenticatedMsg(from, sid, to, name, true, 1);
        Assert.assertTrue(authenticatedMsg.equals(authenticatedMsgTest));
    }
}