package ru.otus.dataset;

import org.junit.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import static org.junit.Assert.*;

public class UserEntityTest
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
        UserEntity dep =  entityManager.find(UserEntity.class, key);
        assertNull(dep);
    }

    @Test
    public void persistDepartmentsDirectoryEntity()
    {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        UserEntity user = new UserEntity();
        user.setLogin("setLogin");
        user.setPassword("setPassword");
        entityManager.persist(user);
        transaction.commit();

        UserEntity userFind = entityManager.find(UserEntity.class, 1L);
        assertNotNull(userFind);
    }
}