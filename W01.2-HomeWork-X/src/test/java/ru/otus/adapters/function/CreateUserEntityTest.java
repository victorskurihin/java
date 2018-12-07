package ru.otus.adapters.function;

import org.junit.Assert;
import org.junit.Test;
import ru.otus.models.UserEntity;

import java.util.function.Function;

public class CreateUserEntityTest
{
    @Test(expected = IndexOutOfBoundsException.class)
    public void exeption()
    {
        Function<String[], UserEntity> f = new CreateUserEntity();
        f.apply(new String[]{"13", "test"});
    }

    @Test
    public void apply()
    {
        UserEntity expected = new UserEntity();
        expected.setId(13L);
        expected.setLogin("test");
        expected.setPassword("test");
        Function<String[], UserEntity> f = new CreateUserEntity();
        UserEntity entity = f.apply(new String[]{"13", "test", "test"});
        Assert.assertEquals(expected, entity);
    }

}