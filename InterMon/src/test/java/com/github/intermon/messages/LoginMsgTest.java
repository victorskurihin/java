package com.github.intermon.messages;

import org.json.simple.parser.ParseException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import com.google.gson.Gson;

import static org.junit.Assert.*;

public class LoginMsgTest {

    private LoginMsg loginMsg;

    @Before
    public void setUp() throws Exception {
        loginMsg = new LoginMsg();
    }

    @After
    public void tearDown() throws Exception {
        loginMsg = null;
    }

    @Test
    public void addressFrom() {
        Assert.assertEquals(LoginMsg.BROADCAST, loginMsg.getFrom());
    }

    @Test
    public void addressTo() {
        Assert.assertEquals(LoginMsg.BROADCAST, loginMsg.getTo());
    }


    @Test
    public void getIdCoverageTest() {
        LoginMsg loginMsg = new LoginMsg();
        Assert.assertEquals(loginMsg.getId(), LoginMsg.class.getSimpleName());
    }

    @Test
    public void createAnswer() {
        Address address = new Address();
        LoginMsg loginMsg = this.loginMsg.createAnswer(address);
        Assert.assertEquals(address, loginMsg.getFrom());
    }

    @Test
    public void toStringCoverageTest() {
        LoginMsg loginMsg1 = new LoginMsg();
        LoginMsg loginMsg2 = new LoginMsg();
        Assert.assertEquals(loginMsg1.toString(), loginMsg2.toString());
    }

    @Test
    public void createJsonLoginMsgGetCoverageTest() throws ParseException, ClassNotFoundException {
        String stringLoginMsg1 = LoginMsg.createJsonLoginMsg();
        LoginMsg loginMsg2 = (LoginMsg) Msg.get(stringLoginMsg1);
        Assert.assertEquals(stringLoginMsg1, new Gson().toJson(loginMsg2));
    }
}