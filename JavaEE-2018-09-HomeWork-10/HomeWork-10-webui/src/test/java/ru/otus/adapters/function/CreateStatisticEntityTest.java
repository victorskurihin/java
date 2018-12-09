package ru.otus.adapters.function;

import org.junit.Assert;
import org.junit.Test;
import ru.otus.models.StatisticEntity;

import java.util.function.Function;

public class CreateStatisticEntityTest
{
    @Test(expected = IndexOutOfBoundsException.class)
    public void exeption()
    {
        Function<String[], StatisticEntity> f = new CreateStatisticEntity();
        f.apply(
            new String[]{"13", "test", "test", "test", "test", "NULL", "NULL", "0", "NULL"}
        );
    }

    @Test
    public void apply()
    {
        StatisticEntity expected = new StatisticEntity();
        expected.setId(13L);
        expected.setNameMarker("test");
        expected.setJspPageName("test");
        expected.setIpAddress("test");
        expected.setUserAgent("test");
        expected.setClientTime(null);
        expected.setServerTime(null);
        expected.setSessionId("0");
        expected.setUser(null);
        expected.setPreviousId(13L);

        Function<String[], StatisticEntity> f = new CreateStatisticEntity();
        StatisticEntity entity = f.apply(
            new String[]{"13", "test", "test", "test", "test", "NULL", "NULL", "0", "NULL", "13"});
        Assert.assertEquals(expected, entity);
    }

}