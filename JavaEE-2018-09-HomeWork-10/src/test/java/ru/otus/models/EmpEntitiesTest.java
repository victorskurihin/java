package ru.otus.models;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class EmpEntitiesTest
{

    @Before
    public void setUp() throws Exception
    {
    }

    @After
    public void tearDown() throws Exception
    {
    }

    @Test
    public void testGetSetEmployees()
    {
        EmpEntities list = new EmpEntities();
        List<EmpEntity> employees = new ArrayList<>();
        list.setEmployees(employees);
        assertEquals(new ArrayList<>(), list.getEmployees());
    }

    @Test
    public void testAddGetEmployees()
    {
        EmpEntities list = new EmpEntities(new ArrayList<>());
        EmpEntity entity = new EmpEntity();
        list.add(entity);
        List<EmpEntity> expected = new ArrayList<>();
        expected.add(entity);
        assertEquals(expected, list.getEmployees());
    }

    @Test
    public void canEqual()
    {
    }
}