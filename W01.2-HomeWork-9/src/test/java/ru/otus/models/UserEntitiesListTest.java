package ru.otus.models;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class UserEntitiesListTest
{
    @Test
    public void equals()
    {
        UserEntitiesList test = new UserEntitiesList();
        List<UserEntity> list = new ArrayList<>();
        UserEntitiesList expected = new UserEntitiesList(list);

        UserEntity entity = new UserEntity();
        entity.setId(13L);
        expected.add(entity);
        Assert.assertFalse(expected.equals(test));
        test.setUsers(list);
        Assert.assertTrue(expected.equals(test));
    }
}