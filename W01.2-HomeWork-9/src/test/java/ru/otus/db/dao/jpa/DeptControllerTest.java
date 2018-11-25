package ru.otus.db.dao.jpa;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.otus.exeptions.ExceptionThrowable;
import ru.otus.models.DeptEntity;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import java.util.ArrayList;
import java.util.List;

import static ru.otus.db.TestDBConf.PERSISTENCE_UNIT_NAME;
import static ru.otus.services.TestExpectedData.getTestDeptEntity1;
import static ru.otus.services.TestExpectedData.getTestDeptEntity2;
import static ru.otus.services.TestExpectedData.getTestDeptEntity3;

public class DeptControllerTest
{
    private EntityManagerFactory emf;
    private EntityManager em;
    private DeptController controller;

    @Before
    public void setUp() throws Exception
    {
        emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        em = emf.createEntityManager();
        controller = new DeptController(em);
    }

    @After
    public void tearDown() throws Exception
    {
        em.close();
        emf.close();
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