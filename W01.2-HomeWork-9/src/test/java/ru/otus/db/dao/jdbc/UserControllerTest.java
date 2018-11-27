package ru.otus.db.dao.jdbc;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.otus.db.Executor;
import ru.otus.db.TestDBConf;
import ru.otus.exeptions.ExceptionThrowable;
import ru.otus.models.UserEntity;

import java.util.ArrayList;
import java.util.List;

import static ru.otus.services.TestExpectedData.*;

public class UserControllerTest implements TestDBConf
{
    private UserController controller;

    @Before
    public void setUp() throws Exception
    {
        TestDBConf.configureDataSource();
        controller = new UserController(dataSource);

        new Executor(dataSource.getConnection()).execUpdate(
            "DROP TABLE IF EXISTS users CASCADE"
        );
        new Executor(dataSource.getConnection()).execUpdate(
            "CREATE TABLE users (" +
            " id BIGINT NOT NULL," +
            " login VARCHAR(255) NOT NULL," +
            " password VARCHAR(255)," +
            " PRIMARY KEY (id))"
        );
    }

    @After
    public void tearDown() throws Exception
    {
        controller = null;
        new Executor(dataSource.getConnection()).execUpdate("DROP TABLE users CASCADE");
    }

    @Test
    public void testEmptyGetAll() throws ExceptionThrowable
    {
        List<UserEntity> expected = new ArrayList<>();
        List<UserEntity> test = controller.getAll();
        Assert.assertEquals(expected, test);
    }

    @Test
    public void testGetEntityById() throws ExceptionThrowable
    {
        Assert.assertNull(controller.getEntityById(1L));
    }

    @Test
    public void testCreateExceptionSQL0() throws ExceptionThrowable
    {
        UserEntity expected = new UserEntity();
        expected.setId(1L);
        expected.setLogin("test");
        expected.setPassword("test");

        Assert.assertTrue(controller.create(expected));
    }

    @Test
    public void testCreate() throws ExceptionThrowable
    {
        UserEntity expected = getTestUserEntity1();
        Assert.assertTrue(controller.create(expected));

        UserEntity test = controller.getEntityById(1L);
        Assert.assertEquals(expected, test);
    }

    @Test(expected = ExceptionThrowable.class)
    public void testCreateExceptionSQL() throws ExceptionThrowable
    {
        UserEntity notExists = new UserEntity();
        notExists.setId(-1L);
        notExists.setName(null);
        controller.create(notExists);
        Assert.fail();
    }

    @Test
    public void testUpdate() throws ExceptionThrowable
    {
        final UserEntity expected = getTestUserEntity1();
        UserEntity test = getTestUserEntity1();

        controller.create(test);
        Assert.assertEquals(expected, test);

        test.setPassword("PASSWORD");
        Assert.assertNotNull(controller.update(test));
        Assert.assertNotEquals(expected, test);
    }

    @Test(expected = ExceptionThrowable.class)
    public void testUpdateNotExists() throws ExceptionThrowable
    {
        UserEntity notExists = new UserEntity();
        notExists.setId(-1L);
        notExists.setLogin(null);
        controller.update(notExists);
        Assert.fail();
    }

    @Test
    public void testDelete() throws ExceptionThrowable
    {
        UserEntity expected = getTestUserEntity1();
        controller.create(expected);
        Assert.assertNotNull(controller.getEntityById(1L));

        Assert.assertTrue(controller.delete(1L));
        Assert.assertNull(controller.getEntityById(1L));
    }

    @Test
    public void testGetAll() throws ExceptionThrowable
    {
        Assert.assertNotNull(controller.create(getTestUserEntity1()));
        List<UserEntity> test = controller.getAll();
        Assert.assertEquals(1, test.size());
        Assert.assertTrue(test.contains(getTestUserEntity1()));
    }
}