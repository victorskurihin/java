package ru.otus.models;

import org.junit.*;

import javax.persistence.*;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;

public class EmpEntityTest
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

    private EmpEntity getTestEmpEntity()
    {
        EmpEntity emp = new EmpEntity();
        emp.setFirstName("firstName");
        emp.setSecondName("secondName");
        emp.setSurName("surName");
        emp.setDepartment(null);
        emp.setCity("City");
        emp.setJob("Job");
        emp.setSalary(0L);
        emp.setUser(null);
        return emp;
    }

    @Test
    public void testNull()
    {
        long key = Long.MIN_VALUE;
        EmpEntity emp =  entityManager.find(EmpEntity.class, key);
        assertNull(emp);
    }

    @Test
    public void persistEmpEntity()
    {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        EmpEntity emp = getTestEmpEntity();
        entityManager.persist(emp);
        transaction.commit();

        EmpEntity empFind = entityManager.find(EmpEntity.class, 1L);
        assertNotNull(empFind);
    }

    @Test
    public void persistEmpEntitySearch()
    {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        EmpEntity emp = getTestEmpEntity();
        entityManager.persist(emp);
        transaction.commit();

        Query q = entityManager.createQuery(
        "SELECT e FROM EmpEntity e WHERE (e.firstName LIKE :name OR e.secondName LIKE :name OR e.surName LIKE :name) "
        );
        q.setParameter("name", "%" + "first" + "%");
        //noinspection unchecked
        ArrayList<EmpEntity> list = new ArrayList<>(q.getResultList());
    }

    @Test
    public void marshallEmpEntity() throws JAXBException
    {
        ByteArrayOutputStream expected = new ByteArrayOutputStream();
        final String result[] = new String[] {
              "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n",
              "<employee id=\"0\" salary=\"0\">\n",
              "    <firstName>firstName</firstName>\n",
              "    <secondName>secondName</secondName>\n",
              "    <surName>surName</surName>\n",
              "    <city>City</city>\n",
              "    <job>Job</job>\n",
              "</employee>\n" };
        Arrays.stream(result).forEach(s -> {
            try {
                expected.write(s.getBytes());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        EmpEntity emp = getTestEmpEntity();
        JAXBContext context = JAXBContext.newInstance(emp.getClass());
        Marshaller m = context.createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        m.marshal(emp, os);
        assertEquals(os.toString(), expected.toString());
    }

    @Test
    public void testGetSetId()
    {
        EmpEntity entity = new EmpEntity();
        entity.setId(13L);
        assertEquals(13L, entity.getId());
    }

    @Test
    public void testGetSetFirstNamed()
    {
        EmpEntity entity = new EmpEntity();
        entity.setFirstName("test");
        assertEquals("test", entity.getFirstName());
    }

    @Test
    public void testGetSetSecondName()
    {
        EmpEntity entity = new EmpEntity();
        entity.setSecondName("test");
        assertEquals("test", entity.getSecondName());
    }

    @Test
    public void testGetSetSurName()
    {
        EmpEntity entity = new EmpEntity();
        entity.setSurName("test");
        assertEquals("test", entity.getSurName());
    }

    @Test
    public void testGetSetCity()
    {
        EmpEntity entity = new EmpEntity();
        entity.setCity("test");
        assertEquals("test", entity.getCity());
    }

    @Test
    public void testGetSetJob()
    {
        EmpEntity entity = new EmpEntity();
        entity.setJob("test");
        assertEquals("test", entity.getJob());
    }

    @Test
    public void testGetSetSalary()
    {
        EmpEntity entity = new EmpEntity();
        entity.setSalary(13L);
        assertEquals(new Long(13), entity.getSalary());
    }


    @Test
    public void testGetSetDepartment()
    {
        EmpEntity entity = new EmpEntity();
        DeptEntity dept = new DeptEntity();
        entity.setDepartment(dept);
        assertEquals(new DeptEntity(), entity.getDepartment());
    }

    @Test
    public void testGetSetUser()
    {
        EmpEntity entity = new EmpEntity();
        UserEntity user = new UserEntity();
        entity.setUser(user);
        assertEquals(new UserEntity(), entity.getUser());
    }

    @Test
    public void testGetSetName()
    {
        String expected = "FirstName SecondName SurName";
        EmpEntity entity = new EmpEntity();
        entity.setName(expected);
        assertEquals(expected, entity.getName());
        String[] expectedArray = expected.split(" ");
        assertEquals(expectedArray[0], entity.getFirstName());
        assertEquals(expectedArray[1], entity.getSecondName());
        assertEquals(expectedArray[2], entity.getSurName());
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testIndexOutOfBoundsException() // TODO Custom exception
    {
        String expected = "";
        EmpEntity entity = new EmpEntity();
        entity.setName(expected);
    }
}