package ru.otus.models;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class GroupEntitiesTest
{
    @Test
    public void equals()
    {
        GroupEntities test = new GroupEntities();
        List<GroupEntity> list = new ArrayList<>();
        GroupEntities expected = new GroupEntities(list);

        GroupEntity entity = new GroupEntity();
        entity.setId(13L);
        expected.add(entity);
        Assert.assertFalse(expected.equals(test));
        test.setGroups(list);
        Assert.assertTrue(expected.equals(test));
    }
}