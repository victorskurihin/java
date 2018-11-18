package ru.otus.models;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class GroupEntitiesListTest
{
    @Test
    public void equals()
    {
        GroupEntitiesList test = new GroupEntitiesList();
        List<GroupEntity> list = new ArrayList<>();
        GroupEntitiesList expected = new GroupEntitiesList(list);

        GroupEntity entity = new GroupEntity();
        entity.setId(13L);
        expected.add(entity);
        Assert.assertFalse(expected.equals(test));
        test.setGroups(list);
        Assert.assertTrue(expected.equals(test));
    }
}