package ru.otus.dataset;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class EmpEntitiesListTest
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
        EmpEntitiesList list = new EmpEntitiesList();
        List<EmpEntity> employees = new ArrayList<>();
        list.setEmployees(employees);
        assertEquals(new ArrayList<>(), list.getEmployees());
    }

    @Test
    public void testAddGetEmployees()
    {
        EmpEntitiesList list = new EmpEntitiesList(new ArrayList<>());
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