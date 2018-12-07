package ru.otus.models;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class UserEntitiesTest
{
    @Test
    public void equals()
    {
        UserEntities test = new UserEntities();
        List<UserEntity> list = new ArrayList<>();
        UserEntities expected = new UserEntities(list);

        UserEntity entity = new UserEntity();
        entity.setId(13L);
        expected.add(entity);
        Assert.assertFalse(expected.equals(test));
        test.setUsers(list);
        Assert.assertTrue(expected.equals(test));
    }
}