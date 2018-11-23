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
}