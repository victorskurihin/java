package com.github.intermon.dataset;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class AddressDataSetTest {
    private static final String TEST = "test";
    private static final String OTHER = "OTHER";
    AddressDataSet addressDataSet;

    @Before
    public void setUp() {
        addressDataSet = new AddressDataSet(1, TEST);
    }

    @After
    public void tearDown() {
        addressDataSet = null;
    }

    @Test
    public void getId() {
        Assert.assertEquals(1, addressDataSet.getId());
    }

    @Test
    public void getStreet() {
        Assert.assertEquals(TEST, addressDataSet.getStreet());
    }

    @Test
    public void setStreet() {
        addressDataSet.setStreet(OTHER);
        Assert.assertEquals(OTHER, addressDataSet.getStreet());
    }

    @Test
    public void equals() {
        AddressDataSet addressDataSetTest = new AddressDataSet(1, TEST);
        Assert.assertEquals(addressDataSetTest, addressDataSet);
    }

    @Test
    public void equalsHashCode() {
        AddressDataSet addressDataSetTest = new AddressDataSet(1, TEST);
        Assert.assertEquals(addressDataSetTest.hashCode(), addressDataSet.hashCode());
    }

    @Test
    public void testConstructor() {
        AddressDataSet testDataSet = new AddressDataSet();
        AddressDataSet expectedDataSet = new AddressDataSet(null);
        Assert.assertEquals(expectedDataSet.toString(), testDataSet.toString());
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
