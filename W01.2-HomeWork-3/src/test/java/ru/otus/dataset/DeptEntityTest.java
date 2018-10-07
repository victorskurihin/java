package ru.otus.dataset;

/*
 * Created by VSkurikhin at autumn 2018.
 */

import org.junit.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class DeptEntityTest
{
    public static final String PERSISTENCE_UNIT_NAME = "test-jpa";
    private static EntityManagerFactory emf;
    private EntityManager entityManager;

    @BeforeClass
    public static void setUpClass()
    {
        emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
    }

    @Before
    public void setUp() throws Exception
    {
        entityManager = emf.createEntityManager();
    }

    @After
    public void tearDown()
    {
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
        DeptEntity dep = new DeptEntity();
        dep.setParentId(1L);
        dep.setTitle("title");
        entityManager.persist(dep);
        transaction.commit();

        DeptEntity depFind = entityManager.find(DeptEntity.class, 1L);
        assertNotNull(depFind);
    }
}