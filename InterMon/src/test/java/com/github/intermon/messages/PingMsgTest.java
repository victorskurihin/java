package com.github.intermon.messages;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.Objects;

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
    public void getTimeCoverageTest() throws InterruptedException {
        long timeBefore = System.currentTimeMillis();
        PingMsg pingMsg = new PingMsg(address, address);
        Assert.assertTrue(timeBefore <= pingMsg.getTime());
        long timeAfter = System.currentTimeMillis();
        Assert.assertTrue(pingMsg.getTime() <= timeAfter);
    }

    private PingMsg clonePingMsg(PingMsg ping) throws NoSuchFieldException, IllegalAccessException {
        Class<PingMsg> classPingMsg = PingMsg.class;
        Field fieldTime = classPingMsg.getDeclaredField("time");
        fieldTime.setAccessible(true);
        PingMsg result = new PingMsg(ping.getFrom(), ping.getTo());
        fieldTime.set(result, ping.getTime());
        return result;
    }

    @Test
    public void equalsCoverageTest() throws NoSuchFieldException,
        IllegalAccessException {
        PingMsg pingMsg1 = new PingMsg(address, address);
        PingMsg pingMsg2 = clonePingMsg(pingMsg1);
        Assert.assertEquals(pingMsg1, pingMsg2);
    }

    @Test
    public void hashCoverageTest() {
        PingMsg pingMsg = new PingMsg(address, address);
        int expectedHash = Objects.hash(pingMsg.getTime(), address, address);
        Assert.assertEquals(expectedHash, pingMsg.hashCode());
    }

    @Test
    public void toStringCoverageTest() throws NoSuchFieldException,
        IllegalAccessException {
        PingMsg pingMsg1 = new PingMsg(address, address);
        PingMsg pingMsg2 = clonePingMsg(pingMsg1);
        Assert.assertEquals(pingMsg1.toString(), pingMsg2.toString());
    }
}