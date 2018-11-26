package ru.otus.db.dao.jpa;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.otus.exeptions.ExceptionThrowable;
import ru.otus.models.UserEntity;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.ArrayList;
import java.util.List;

import static ru.otus.db.TestDBConf.PERSISTENCE_UNIT_NAME;
import static ru.otus.services.TestExpectedData.*;

public class UserControllerTest
{
    private EntityManagerFactory emf;
    private EntityManager em;
    private UserController controller;

    @Before
    public void setUp() throws Exception
    {
        emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        em = emf.createEntityManager();
        controller = new UserController(em);
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
    public void testCreate() throws ExceptionThrowable
    {
        UserEntity expected = getTestUserEntity1();
        Assert.assertTrue(controller.create(expected));

        UserEntity test = controller.getEntityById(1L);
        Assert.assertEquals(expected, test);
    }

    @Test
    public void testUpdate() throws ExceptionThrowable
    {
        final UserEntity expected = getTestUserEntity1();
        UserEntity test = getTestUserEntity1();

        controller.create(test);
        Assert.assertEquals(expected, test);

        test.setLogin("TEST");
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