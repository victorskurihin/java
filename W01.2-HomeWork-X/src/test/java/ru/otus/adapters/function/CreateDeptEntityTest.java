package ru.otus.adapters.function;

import org.junit.Assert;
import org.junit.Test;
import ru.otus.models.DeptEntity;

import java.util.function.Function;

public class CreateDeptEntityTest
{
    @Test(expected = IndexOutOfBoundsException.class)
    public void exeption()
    {
        Function<String[], DeptEntity> f = new CreateDeptEntity();
        f.apply(new String[]{"13", "0"});
    }

    @Test
    public void apply()
    {
        DeptEntity expected = new DeptEntity();
        expected.setId(13L);
        expected.setParentId(0L);
        expected.setTitle("test");
        Function<String[], DeptEntity> f = new CreateDeptEntity();
        DeptEntity entity = f.apply(new String[]{"13", "0", "test"});
        Assert.assertEquals(expected, entity);
    }
}