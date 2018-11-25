package ru.otus.models;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class StatisticEntitiesListTest
{
    @Test
    public void equals()
    {
        StatisticEntitiesList test = new StatisticEntitiesList();
        List<StatisticEntity> list = new ArrayList<>();
        StatisticEntitiesList expected = new StatisticEntitiesList(list);

        StatisticEntity entity = new StatisticEntity();
        entity.setId(13L);
        expected.add(entity);
        Assert.assertFalse(expected.equals(test));
        test.setRegistrations(list);
        Assert.assertTrue(expected.equals(test));
    }
}