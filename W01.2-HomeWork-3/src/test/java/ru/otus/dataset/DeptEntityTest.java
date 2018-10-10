package ru.otus.dataset;

/*
 * Created by VSkurikhin at autumn 2018.
 */

import org.junit.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class DeptEntityTest
{
    public static final String PERSISTENCE_UNIT_NAME = "test-jpa";
    public static final String TEST = "test";
    public static final String TSET = "tset";
    private static EntityManagerFactory emf;
    private EntityManager entityManager;
    DeptEntity entity;

    @BeforeClass
    public static void setUpClass()
    {
        emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
    }

    @Before
    public void setUp() throws Exception
    {
        entityManager = emf.createEntityManager();
        entity = new DeptEntity();
    }

    @After
    public void tearDown()
    {
        entity = null;
        entityManager.close();
    }

    @AfterClass
    public static void tearDownClass()
    {
        emf.close();
    }

    @Test
    public void testNull()
    {
        long key = Long.MIN_VALUE;
        DeptEntity dep =  entityManager.find(DeptEntity.class, key);
        assertNull(dep);
    }

    @Test
    public void persistDeptEntity()
    {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entity.setParentId(1L);
        entity.setTitle("title");
        entityManager.persist(entity);
        transaction.commit();

        DeptEntity depFind = entityManager.find(DeptEntity.class, 1L);
        assertNotNull(depFind);
    }

    @Test
    public void testGetSetId()
    {
        entity.setId(13L);
        assertEquals(13L, entity.getId());
    }

    @Test
    public void testGetSetPid()
    {
        entity.setParentId(13L);
        assertEquals(13L, entity.getParentId());
    }

    @Test
    public void testGetSetTitle()
    {
        entity.setTitle(TEST);
        assertEquals(TEST, entity.getTitle());
        assertEquals(TEST, entity.getName());
        entity.setName(TSET);
        assertEquals(TSET, entity.getTitle());
        assertEquals(TSET, entity.getName());
    }
}