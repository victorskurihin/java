package ru.otus.l161.messages;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.otus.l161.dataset.AddressDataSet;
import ru.otus.l161.dataset.UserDataSet;

public class SingedMsgTest {

    private static final String NAME = "NAME";
    private static final String PASSWORD = "PASSWORD";
    private static final String ADDRESS = "ADDRESS";
    private static final String TEXT = "TEXT";

    private final Address from = new Address();
    private final Address to = new Address();
    private final AddressDataSet addressDataSet = new AddressDataSet(1, ADDRESS);
    private final UserDataSet userDataSet = new UserDataSet(1, NAME, PASSWORD, addressDataSet);
    private final String sid = "0";

    private SingedMsg singedMsg;

    @Before
    public void setUp() throws Exception {
        singedMsg = new SingedMsg(from, sid, to, NAME, true);
    }

    @After
    public void tearDown() throws Exception {
        singedMsg = null;
    }

    @Test
    public void getId() {
        Assert.assertEquals(SingedMsg.ID, singedMsg.getId());
    }

    @Test
    public void equals() {
        SingedMsg singedMsgTest = new SingedMsg(from, sid, to, NAME, true);
        Assert.assertTrue(singedMsg.equals(singedMsgTest));
        Assert.assertEquals(singedMsgTest, singedMsg);
        SingedMsg singedMsgFalseTest = new SingedMsg(from, sid, to, NAME, false);
        Assert.assertFalse(singedMsg.equals(singedMsgFalseTest));
    }

    @Test
    public void message() {
        singedMsg.setMessage(TEXT);
        Assert.assertEquals(TEXT, singedMsg.getMessage());
    }

    @Test
    public void isPositive() {
        Assert.assertTrue(singedMsg.isPositive());
        SingedMsg singedMsgFalseTest = new SingedMsg(from, sid, to, NAME, false);
        Assert.assertFalse(singedMsgFalseTest.isPositive());
    }

    @Test
    public void getUser() {
        Assert.assertEquals(NAME, singedMsg.getUser());
    }

    @Test
    public void getSessionId() {
        Assert.assertEquals(sid, singedMsg.getSessionId());
    }
}