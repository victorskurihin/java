package ru.otus.models;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class StatisticEntitiesTest
{
    @Test
    public void equals()
    {
        StatisticEntities test = new StatisticEntities();
        List<StatisticEntity> list = new ArrayList<>();
        StatisticEntities expected = new StatisticEntities(list);

        StatisticEntity entity = new StatisticEntity();
        entity.setId(13L);
        expected.add(entity);
        Assert.assertFalse(expected.equals(test));
        test.setRegistrations(list);
        Assert.assertTrue(expected.equals(test));
    }
}