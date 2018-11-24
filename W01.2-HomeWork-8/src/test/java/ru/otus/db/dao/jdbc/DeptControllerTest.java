package ru.otus.db.dao.jdbc;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.otus.db.Executor;
import ru.otus.db.TestDBConf;
import ru.otus.exeptions.ExceptionThrowable;
import ru.otus.models.DeptEntity;

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
        new Executor(dataSource.getConnection()).execUpdate("DROP TABLE dep_directory");
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
    public void testCreateExceptionSQL() throws ExceptionThrowable
    {
        DeptEntity expected = new DeptEntity();
        expected.setId(1L);
        expected.setParentId(0L);
        expected.setTitle("title");

        Assert.assertTrue(controller.create(expected));
    }

    @Test
    public void testCreate() throws ExceptionThrowable
    {
        DeptEntity expected = getTestDeptEntity1();
        Assert.assertTrue(controller.create(expected));

        DeptEntity test = controller.getEntityById(1L);
        Assert.assertEquals(expected, test);
    }

    @Test
    public void testUpdate() throws ExceptionThrowable
    {
        DeptEntity expected = getTestDeptEntity1();
        controller.create(expected);

        DeptEntity test = controller.getEntityById(1L);
        Assert.assertEquals(expected, test);

        expected.setTitle("TITLE");
        Assert.assertNotEquals(expected, test);

        Assert.assertNotNull(controller.update(expected));
        test = controller.getEntityById(1L);
        Assert.assertEquals(expected, test);

        DeptEntity notExists = new DeptEntity();
        notExists.setId(-1L);
        notExists.setTitle(null);
        Assert.assertNull(controller.update(notExists));
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