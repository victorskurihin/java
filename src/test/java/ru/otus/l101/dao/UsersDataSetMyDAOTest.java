package ru.otus.l101.dao;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.otus.l101.dataset.UsersDataSet;

import static org.junit.Assert.*;

public class UsersDataSetMyDAOTest {
    UsersDataSetMyDAO usersDataSetMyDAO;

    @Before
    public void setUp() throws Exception {
        usersDataSetMyDAO = new UsersDataSetMyDAO(null);
    }

    @After
    public void tearDown() throws Exception {
        usersDataSetMyDAO = null;
    }

    @Test
    public void getAdapteeOfType() {
        Assert.assertEquals(UsersDataSet.class.getName(), usersDataSetMyDAO.getAdapteeOfType());
    }
}