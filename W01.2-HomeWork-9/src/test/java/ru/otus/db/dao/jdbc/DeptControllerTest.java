package ru.otus.db.dao.jdbc;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.otus.db.Executor;
import ru.otus.db.TestDBConf;
import ru.otus.exeptions.ExceptionThrowable;
import ru.otus.models.DeptEntity;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static ru.otus.services.TestExpectedData.getTestDeptEntity1;
import static ru.otus.services.TestExpectedData.getTestDeptEntity2;
import static ru.otus.services.TestExpectedData.getTestDeptEntity3;

public class DeptControllerTest implements TestDBConf
{
    private DeptController controller;

    @Before
    public void setUp() throws Exception
    {
        TestDBConf.configureDataSource();
        controller = new DeptController(dataSource);

        new Executor(dataSource.getConnection()).execUpdate(
            "DROP TABLE IF EXISTS dep_directory CASCADE"
        );
        new Executor(dataSource.getConnection()).execUpdate(
            "CREATE TABLE dep_directory (" +
            " id BIGINT NOT NULL," +
            " pid BIGINT NOT NULL," +
            " title VARCHAR(255) NOT NULL," +
            " PRIMARY KEY (ID))"
        );
    }

    @After
    public void tearDown() throws Exception
    {
        controller = null;
        new Executor(dataSource.getConnection()).execUpdate("DROP TABLE dep_directory CASCADE");
    }

    @Test
    public void testEmptyGetAll() throws ExceptionThrowable
    {
        List<DeptEntity> expected = new ArrayList<>();
        List<DeptEntity> test = controller.getAll();
        Assert.assertEquals(expected, test);
    }

    @Test
    public void testGetEntityById() throws ExceptionThrowable
    {
        Assert.assertNull(controller.getEntityById(1L));
    }

    @Test
    public void testCreate() throws ExceptionThrowable
    {
        DeptEntity expected = getTestDeptEntity1();
        Assert.assertTrue(controller.create(expected));

        DeptEntity test = controller.getEntityById(1L);
        Assert.assertEquals(expected, test);
    }

    @Test(expected = ExceptionThrowable.class)
    public void testCreateExceptionSQL() throws ExceptionThrowable
    {
        DeptEntity notExists = new DeptEntity();
        notExists.setId(-1L);
        notExists.setTitle(null);
        controller.create(notExists);
        Assert.fail();
    }

    @Test
    public void testUpdate() throws ExceptionThrowable
    {
        final DeptEntity expected = getTestDeptEntity1();
        DeptEntity test = getTestDeptEntity1();

        controller.create(test);
        Assert.assertEquals(expected, test);

        test.setTitle("TITLE");
        Assert.assertNotNull(controller.update(test));
        Assert.assertNotEquals(expected, test);
    }

    @Test(expected = ExceptionThrowable.class)
    public void testUpdateNotExists() throws ExceptionThrowable
    {
        DeptEntity notExists = new DeptEntity();
        notExists.setId(-1L);
        notExists.setTitle(null);
        controller.update(notExists);
        Assert.fail();
    }

    @Test
    public void testDelete() throws ExceptionThrowable
    {
        DeptEntity expected = getTestDeptEntity1();
        controller.create(expected);
        Assert.assertNotNull(controller.getEntityById(1L));

        Assert.assertTrue(controller.delete(1L));
        Assert.assertNull(controller.getEntityById(1L));
    }

    @Test
    public void testGetAll() throws ExceptionThrowable
    {
        Assert.assertNotNull(controller.create(getTestDeptEntity1()));
        Assert.assertNotNull(controller.create(getTestDeptEntity2()));
        Assert.assertNotNull(controller.create(getTestDeptEntity3()));
        List<DeptEntity> test = controller.getAll();
        Assert.assertEquals(3, test.size());
        Assert.assertTrue(test.contains(getTestDeptEntity1()));
        Assert.assertTrue(test.contains(getTestDeptEntity2()));
        Assert.assertTrue(test.contains(getTestDeptEntity3()));
    }
}