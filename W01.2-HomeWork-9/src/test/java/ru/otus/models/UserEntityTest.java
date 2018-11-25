package ru.otus.models;

import org.junit.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import static org.junit.Assert.*;

public class UserEntityTest
{
    public static final String PERSISTENCE_UNIT_NAME = "test-jpa";
    public static final String TEST = "test";
    public static final String TSET = "tset";
    private static EntityManagerFactory emf;
    private EntityManager entityManager;
    UserEntity entity;

    @BeforeClass
    public static void setUpClass()
    {
        emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
    }

    @Before
    public void setUp() throws Exception
    {
        entityManager = emf.createEntityManager();
        entity = new UserEntity();
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
        UserEntity dep =  entityManager.find(UserEntity.class, key);
        assertNull(dep);
    }

    @Test
    public void persistDepartmentsDirectoryEntity()
    {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entity.setId(1L);
        entity.setName("setName");
        entity.setPassword("setPassword");
        entityManager.persist(entity);
        transaction.commit();

        UserEntity userFind = entityManager.find(UserEntity.class, 1L);
        assertNotNull(userFind);
    }

    @Test
    public void testGetSetId()
    {
        entity.setId(13L);
        assertEquals(13L, entity.getId());
    }

    @Test
    public void testGetSetLogin()
    {
        entity.setLogin(TEST);
        assertEquals(TEST, entity.getLogin());
        assertEquals(TEST, entity.getName());
        entity.setName(TSET);
        assertEquals(TSET, entity.getLogin());
        assertEquals(TSET, entity.getName());
    }

    @Test
    public void testGetSetPassword()
    {
        entity.setPassword(TEST);
        assertEquals(TEST, entity.getPassword());
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
