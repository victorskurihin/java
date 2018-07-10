package com.github.intermon.messages;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class RegisterDBServerMsgTest {
    Address address;

    @Before
    public void setUp() {
        address = new Address();
    }

    @After
    public void tearDown() {
        address = null;
    }

    @Test
    public void getIdCoverageTest() {
        RegisterDBServerMsg registerDBServerMsg = new RegisterDBServerMsg(address);
        Assert.assertEquals(registerDBServerMsg.getId(), RegisterDBServerMsg.class.getSimpleName());
    }

    @Test
    public void toStringCoverageTest() {
        RegisterDBServerMsg registerDBServerMsg1 = new RegisterDBServerMsg(address);
        RegisterDBServerMsg registerDBServerMsg2 = new RegisterDBServerMsg(address);
        Assert.assertEquals(registerDBServerMsg1.toString(), registerDBServerMsg2.toString());
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
