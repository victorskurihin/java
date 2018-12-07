package ru.otus.adapters.function;

import org.junit.Assert;
import org.junit.Test;
import ru.otus.models.EmpEntity;

import java.util.function.Function;

public class CreateEmpEntityTest
{
    @Test(expected = IndexOutOfBoundsException.class)
    public void exeption()
    {
        Function<String[], EmpEntity> f = new CreateEmpEntity();
        f.apply(
            new String[]{"13", "test", "test", "test", "test", "test", "13", "NULL", "NULL"}
        );
    }

    @Test
    public void apply()
    {
        EmpEntity expected = new EmpEntity();
        expected.setId(13L);
        expected.setCity("test");
        expected.setFirstName("test");
        expected.setSecondName("test");
        expected.setSurName("test");
        expected.setJob("test");
        expected.setSalary(13L);
        expected.setDepartment(null);
        expected.setUser(null);
        expected.setAge(13L);

        Function<String[], EmpEntity> f = new CreateEmpEntity();
        EmpEntity entity = f.apply(
                new String[]{"13", "test", "test", "test", "test", "test", "13", "NULL", "NULL", "13"});
        Assert.assertEquals(expected, entity);
    }

}