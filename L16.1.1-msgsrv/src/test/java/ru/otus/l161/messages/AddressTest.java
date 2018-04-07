package ru.otus.l161.messages;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class AddressTest {
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
    public void equals() {
        Address addressTest = new Address(address.getId());
        Assert.assertEquals(addressTest, address);
        Assert.assertEquals(addressTest.hashCode(), address.hashCode());
    }

    @Test
    public void getId() {
        Address addressTest = new Address(address.getId());
        Assert.assertEquals(addressTest.getId(), address.getId());
    }

    @Test
    public void compareTo() {
        Address addressA = new Address("A");
        Address addressATest = new Address("A");
        Address addressB = new Address("B");
        Assert.assertTrue(addressA.compareTo(addressB) < 0);
        Assert.assertTrue(addressA.compareTo(addressATest) == 0);
        Assert.assertTrue(addressB.compareTo(addressA) > 0);
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
