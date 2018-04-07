package ru.otus.l161.dataset;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PhoneDataSetTest {
    private static final String TEST = "test";
    private static final String OTHER = "OTHER";
    PhoneDataSet phoneDataSet;
    UserDataSet userDataSet;

    @Before
    public void setUp() throws Exception {
        userDataSet = new UserDataSet(TEST, OTHER);
        phoneDataSet = new PhoneDataSet(1, TEST);
        phoneDataSet.setUserDataSet(userDataSet);
    }

    @After
    public void tearDown() throws Exception {
        phoneDataSet = null;
        userDataSet = null;
    }

    @Test
    public void getNumber() {
        Assert.assertEquals(TEST, phoneDataSet.getNumber());
    }

    @Test
    public void setNumber() {
        phoneDataSet.setNumber(OTHER);
        Assert.assertEquals(OTHER, phoneDataSet.getNumber());
    }

    @Test
    public void getUserDataSet() {
        Assert.assertEquals(userDataSet, phoneDataSet.getUserDataSet());
    }

    @Test
    public void equals() {
        UserDataSet userDataSetTest = new UserDataSet(TEST, OTHER);
        PhoneDataSet phoneDataSetTest = new PhoneDataSet(1, TEST);
        phoneDataSetTest.setUserDataSet(userDataSetTest);
        phoneDataSetTest.equals(phoneDataSet);
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
