package ru.otus.models;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class DeptEntitiesListTest
{
    @Test
    public void pametrizedConstructor()
    {
        List<DeptEntity> expected = new ArrayList<>();
        DeptEntitiesList test = new DeptEntitiesList(expected);
        Assert.assertEquals(expected, test.asList());
    }

    @Test
    public void setDepartments()
    {
        List<DeptEntity> expected = new ArrayList<>();
        DeptEntitiesList test = new DeptEntitiesList();
        test.setDepartments(expected);
        Assert.assertEquals(expected, test.getDepartments());
    }

    @Test
    public void setDepartments1()
    {
        DeptEntitiesList expected = new DeptEntitiesList();
        DeptEntitiesList test = new DeptEntitiesList();
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