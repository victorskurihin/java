package ru.otus.models;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class DeptEntitiesTest
{
    @Test
    public void pametrizedConstructor()
    {
        List<DeptEntity> expected = new ArrayList<>();
        DeptEntities test = new DeptEntities(expected);
        Assert.assertEquals(expected, test.asList());
    }

    @Test
    public void setDepartments()
    {
        List<DeptEntity> expected = new ArrayList<>();
        DeptEntities test = new DeptEntities();
        test.setDepartments(expected);
        Assert.assertEquals(expected, test.getDepartments());
    }

    @Test
    public void setDepartments1()
    {
        DeptEntities expected = new DeptEntities();
        DeptEntities test = new DeptEntities();
        List<DeptEntity> list = new ArrayList<>();
        DeptEntity entity = new DeptEntity();
        entity.setId(13L);
        expected.setDepartments(list);
        expected.add(entity);
        Assert.assertFalse(expected.equals(test));
        test.setDepartments(list);
        Assert.assertTrue(expected.equals(test));
    }
}