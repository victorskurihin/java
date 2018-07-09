package com.github.intermon.messages;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PingMsgTest {
    Address address;

    @Before
    public void setUp() throws Exception {
        address = new Address();
    }

    @After
    public void tearDown() throws Exception {
        address = null;
    }

    @Test
    public void getIdCoverageTest() {
        PingMsg pingMsg = new PingMsg(address, address);
        Assert.assertEquals(pingMsg.getId(), PingMsg.class.getSimpleName());
    }

    @Test
    public void getTimeCoverageTest() {
    }

    @Test
    public void equalsCoverageTest() {
    }

    @Test
    public void hashCodeoverageTest() {
    }

    @Test
    public void toStringCodeoverageTest() {
    }
}