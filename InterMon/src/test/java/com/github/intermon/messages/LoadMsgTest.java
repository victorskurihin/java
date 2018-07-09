package com.github.intermon.messages;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class LoadMsgTest {
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
        LoadMsg loadMsg = new LoadMsg(address, address, 0.0);
        Assert.assertEquals(loadMsg.getId(), LoadMsg.class.getSimpleName());
    }

    @Test
    public void getLoadCoverageTest() {
        LoadMsg loadMsg = new LoadMsg(address, address, 0.1);
        Assert.assertTrue ((0.1 - loadMsg.getLoad()) < Double.MIN_VALUE);
    }

    @Test
    public void toStringCoverageTest() {
        LoadMsg loadMsg1 = new LoadMsg(address, address, 0.0);
        LoadMsg loadMsg2 = new LoadMsg(address, address, 0.0);
        Assert.assertEquals(loadMsg1.toString(), loadMsg2.toString());
    }
}