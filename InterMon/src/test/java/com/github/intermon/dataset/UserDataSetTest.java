package com.github.intermon.dataset;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class UserDataSetTest {
    private static final String NAME = "NAME";
    private static final String PASSWORD = "PASSWORD";
    private static final String ADDRESS = "ADDRESS";
    private static final String OTHER = "OTHER";
    private UserDataSet userDataSet;
    private AddressDataSet addressDataSet;
    private Set<PhoneDataSet> phones;

    @Before
    public void setUp() {
        phones = new HashSet<>();
        addressDataSet = new AddressDataSet(1, ADDRESS);
        userDataSet = new UserDataSet(1, NAME, PASSWORD, addressDataSet);
    }

    @After
    public void tearDown() {
        userDataSet = null;
        addressDataSet = null;
        phones = null;
    }

    @Test
    public void getName() {
        Assert.assertEquals(NAME, userDataSet.getName());
    }

    @Test
    public void setName() {
        userDataSet.setName(OTHER);
        Assert.assertEquals(OTHER, userDataSet.getName());
    }

    @Test
    public void getPassword() {
        Assert.assertEquals(PASSWORD, userDataSet.getPassword());
    }

    @Test
    public void setPassword() {
        userDataSet.setPassword(OTHER);
        Assert.assertEquals(OTHER, userDataSet.getPassword());
    }

    @Test
    public void getAddress() {
        Assert.assertEquals(addressDataSet, userDataSet.getAddress());
    }

    @Test
    public void setAddress() {
        AddressDataSet addressDataSetTest = new AddressDataSet(2, OTHER);
        userDataSet.setAddress(addressDataSetTest);
        Assert.assertEquals(addressDataSetTest, userDataSet.getAddress());
    }

    @Test
    public void equals() {
        PhoneDataSet phoneDataSet = new PhoneDataSet(1, OTHER);
        phoneDataSet.setUserDataSet(userDataSet);
        phones.add(phoneDataSet);
        userDataSet.setPhones(phones);
        AddressDataSet addressDataSetTest = new AddressDataSet(1, ADDRESS);
        UserDataSet userDataSetTest = new UserDataSet(1, NAME, PASSWORD, addressDataSetTest);
        userDataSetTest.addPhone(new PhoneDataSet(1, OTHER));
        Assert.assertEquals(userDataSetTest, userDataSet);
    }


    @Test
    public void testHashCode() {
        userDataSet.setPhones(phones);
        UserDataSet expectedUserDataSet  = new UserDataSet(1, NAME, PASSWORD, addressDataSet);
        expectedUserDataSet.setPhones(phones);
        Assert.assertEquals(expectedUserDataSet.hashCode(), addressDataSet.hashCode());
    }

    @Test
    public void testConstructor() {
        UserDataSet testUserDataSet = new UserDataSet();
        UserDataSet expectedUserDataSet = new UserDataSet(null, null);
        Assert.assertEquals(expectedUserDataSet.toString(), testUserDataSet.toString());
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
