package ru.otus.dataset;

import org.junit.*;

import javax.persistence.*;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
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
    public void getName()
    {
        // TODO
    }

    @Test
    public void setName()
    {
        // TODO
    }
}