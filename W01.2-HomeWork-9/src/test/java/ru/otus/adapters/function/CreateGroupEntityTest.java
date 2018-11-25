package ru.otus.adapters.function;

import org.junit.Assert;
import org.junit.Test;
import ru.otus.models.GroupEntity;

import java.util.function.Function;

public class CreateGroupEntityTest
{
    @Test(expected = IndexOutOfBoundsException.class)
    public void exeption()
    {
        Function<String[], GroupEntity> f = new CreateGroupEntity();
        f.apply(new String[]{"13", "test"});
    }

    @Test
    public void apply()
    {
        GroupEntity expected = new GroupEntity();
        expected.setId(13L);
        expected.setGroupname("test");
        expected.setLogin("test");
        Function<String[], GroupEntity> f = new CreateGroupEntity();
        GroupEntity entity = f.apply(new String[]{"13", "test", "test"});
        Assert.assertEquals(expected, entity);
    }
}