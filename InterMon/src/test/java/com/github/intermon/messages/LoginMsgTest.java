package com.github.intermon.messages;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

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
    public void createAnswer() {
        Address address = new Address();
        LoginMsg loginMsg = this.loginMsg.createAnswer(address);
        Assert.assertEquals(address, loginMsg.getFrom());
    }
}