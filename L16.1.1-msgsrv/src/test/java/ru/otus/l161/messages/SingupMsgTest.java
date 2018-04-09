package ru.otus.l161.messages;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.otus.l161.dataset.AddressDataSet;
import ru.otus.l161.dataset.UserDataSet;

public class SingupMsgTest {

    private static final String NAME = "NAME";
    private static final String PASSWORD = "PASSWORD";
    private static final String ADDRESS = "ADDRESS";

    private final Address from = new Address();
    private final Address to = new Address();
    private final AddressDataSet addressDataSet = new AddressDataSet(1, ADDRESS);
    private final UserDataSet userDataSet = new UserDataSet(1, NAME, PASSWORD, addressDataSet);
    private final String sid = "0";

    private SingupMsg singupMsg;

    @Before
    public void setUp() throws Exception {
        singupMsg = new SingupMsg(from, sid, to, userDataSet);
    }

    @After
    public void tearDown() throws Exception {
        singupMsg = null;
    }

    @Test
    public void getId() {
        Assert.assertEquals(SingupMsg.ID, singupMsg.getId());
    }

    @Test
    public void equals() {
        AddressDataSet addressDataSet = new AddressDataSet(1, ADDRESS);
        UserDataSet userDataSet = new UserDataSet(1, NAME, PASSWORD, addressDataSet);
        SingupMsg singupMsgTest = new SingupMsg(from, sid, to, userDataSet);
        Assert.assertEquals(singupMsgTest, singupMsg);
    }

    @Test
    public void getUser() {
        AddressDataSet addressDataSetTest = new AddressDataSet(1, ADDRESS);
        UserDataSet userDataSetTest = new UserDataSet(-1, NAME, PASSWORD, addressDataSetTest);
        Assert.assertEquals(userDataSetTest, singupMsg.getUser());
    }

    @Test
    public void createAnswer() {
        SingedMsg answer = singupMsg.createAnswer(true, "Ok");
        SingedMsg singedMsgTest = new SingedMsg(to, sid, from, NAME, true);
        singedMsgTest.setMessage("Ok");
        Assert.assertEquals(singedMsgTest, answer);
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
