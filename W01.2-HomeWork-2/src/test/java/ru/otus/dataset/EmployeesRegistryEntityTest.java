package ru.otus.dataset;

import org.junit.*;

import javax.persistence.*;

import static org.junit.Assert.*;

public class EmployeesRegistryEntityTest
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
        EmployeesRegistryEntity emp =  entityManager.find(EmployeesRegistryEntity.class, key);
        assertNull(emp);
    }

    @Test
    public void persistEmployeesRegistryEntity()
    {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        EmployeesRegistryEntity emp = new EmployeesRegistryEntity();
        emp.setFirstName("setFirstName");
        emp.setSecondName("setSecondName");
        emp.setSurName("getSurName");
        emp.setDepartment(null);
        emp.setCity("setCity");
        emp.setJob("setJob");
        emp.setSalary(0L);
        emp.setUser(null);
        entityManager.persist(emp);
        transaction.commit();

        EmployeesRegistryEntity empFind = entityManager.find(EmployeesRegistryEntity.class, 1L);
        assertNotNull(empFind);
    }
}