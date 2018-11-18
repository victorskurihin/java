package ru.otus.services;

/*
 * Created by VSkurikhin at autumn 2018.
 */

import org.junit.*;
import org.mockito.Mockito;
import ru.otus.db.ImporterSmallXML;
import ru.otus.models.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletContext;
import javax.xml.bind.JAXBException;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.mock;
import static ru.otus.services.TestDbJPAUtils.*;
import static ru.otus.services.TestExpectedData.*;
import static ru.otus.utils.IO.getAbsolutePath;

public class DbJPAPostgreSQLServiceTest
{
    public static final String PERSISTENCE_UNIT_NAME = "test-jpa";

    private static final String TEST_RESOURCES = "src" + File.separator
                                               + "test" + File.separator
                                               + "resources" + File.separator;

    private static final Map<String, String> FILE_LOCATIONS = new HashMap<String , String>() {{
        put(DbJPAPostgreSQLService.TABLE_DEP_DIRECTORY, TEST_RESOURCES + "DeptEntities.xml");
        put(DbJPAPostgreSQLService.TABLE_EMP_REGISTRY,  TEST_RESOURCES + "EmpEntities.xml");
        put(DbJPAPostgreSQLService.TABLE_STATISTIC,     TEST_RESOURCES + "StatisticEntities.xml");
        put(DbJPAPostgreSQLService.TABLE_USER_GROUPS,   TEST_RESOURCES + "UsersgroupEntities.xml");
        put(DbJPAPostgreSQLService.TABLE_USERS,         TEST_RESOURCES + "UserEntities.xml");
    }};

    private static final String FIRST_NAME = "FirstName";
    private static final String SECOND_NAME = "SecondName";
    private static final String SUR_NAME = "SurName";

    private static List<ImporterSmallXML<?>> importeres = new ArrayList<>();
    private static ServletContext ctx;

    private EntityManagerFactory emf;
    private EntityManager em;
    private DbService service;

    private static InputStream getInputStream(String relativePath) throws IOException
    {
        String path = getAbsolutePath(relativePath);
        return new FileInputStream(path);
    }

    private static void mockResourceAsStream(String fileLocationTest, String fileLocationMock)
    throws IOException
    {
        Mockito.doReturn(fileLocationTest)
               .when(ctx)
               .getInitParameter(fileLocationMock);
        Mockito.doReturn(getInputStream(fileLocationTest))
               .when(ctx)
               .getResourceAsStream(fileLocationTest);
    }

    private static <T extends EntitiesList> ImporterSmallXML<T> createImporterXML(String s)
    throws IOException, JAXBException
    {
        String entitiesXMLPath = ctx.getInitParameter(s);
        return new ImporterSmallXML<>(ctx, entitiesXMLPath, DbJPAPostgreSQLService.CLASSES);
    }

    private static void createImportersXMLData() throws IOException, JAXBException
    {
        for (String table : DbJPAPostgreSQLService.TABLES) {
            if (FILE_LOCATIONS.get(table) != null)
                importeres.add(createImporterXML(DbJPAPostgreSQLService.FILE_LOCATIONS.get(table)));
        }
    }

    @BeforeClass
    public static void setupServletContext() throws IOException, JAXBException
    {
        ctx = mock(ServletContext.class);
        for (String table : DbJPAPostgreSQLService.TABLES) {
            String mock = DbJPAPostgreSQLService.FILE_LOCATIONS.get(table);
            mockResourceAsStream(FILE_LOCATIONS.get(table), mock);
        }
        createImportersXMLData();
    }

    @Before
    public void setUp() throws Exception
    {
        emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        em = emf.createEntityManager();
        service = new DbJPAPostgreSQLService(em);

        for (ImporterSmallXML<?> importer : importeres) {
            importer.saveEntities(em);
        }
    }

    @After
    public void tearDown() throws Exception
    {
        service.close();
        em.close();
        emf.close();
        service = null;
        em = null;
    }

    @Test
    public void clearDb() throws Exception
    {
        service.clearDb(null);
        Assert.assertEquals(0L, countRowsEntities(em, DeptEntity.class));
        Assert.assertEquals(0L, countRowsEntities(em, UserEntity.class));
        Assert.assertEquals(0L, countRowsEntities(em, EmpEntity.class));
        Assert.assertEquals(0L, countRowsEntities(em, GroupEntity.class));
    }

    /* Error java.io.IOException: Stream Closed!!!
    @Test
    public void importDb() throws Exception
    {
        service.importDb(ctx);

        Assert.assertEquals(3L, countRowsEntities(em, DeptEntity.class));
        Assert.assertEquals(1L, countRowsEntities(em, UserEntity.class));
        Assert.assertEquals(3L, countRowsEntities(em, EmpEntity.class));
        Assert.assertEquals(1L, countRowsEntities(em, GroupEntity.class));
    } */

    @Test
    public void getEmpEntities() throws Exception
    {
        List<EmpEntity> result = service.getEmpEntities();
        for (EmpEntity entity : getTestEmpEntitiesList().asList()) {
            Assert.assertTrue(result.contains(entity));
        }
    }

    @Test
    public void getEntities() throws Exception
    {
        List<DeptEntity> result = service.getEntities(DeptEntity.class);
        for (DeptEntity entity : getTestDeptEntitiesList().asList()) {
            Assert.assertTrue(result.contains(entity));
        }
    }

    @Test
    public void getEmpEntityById() throws Exception
    {
        EmpEntity entity1 = service.getEmpEntityById(1L);
        Assert.assertEquals(getTestEmpEntity1(), entity1);
        EmpEntity entity2 = service.getEmpEntityById(2L);
        Assert.assertEquals(getTestEmpEntity2(), entity2);
        EmpEntity entity3 = service.getEmpEntityById(3L);
        Assert.assertEquals(getTestEmpEntity3(), entity3);
    }

    @Test
    public void saveEntity() throws Exception
    {
        EmpEntity expected = getTestEmpEntity1();
        expected.setId(4L);
        expected.setFirstName(FIRST_NAME);
        expected.setSecondName(SECOND_NAME);
        expected.setSurName(SUR_NAME);
        service.saveEntity(expected);
        EmpEntity entity = service.getEmpEntityById(4L);
        Assert.assertEquals(expected, entity);
    }

    @Test
    public void updateFirstNameInEmpEntityById() throws Exception
    {
        EmpEntity expected = service.getEmpEntityById(1L);
        expected.setFirstName(FIRST_NAME);
        service.updateFirstNameInEmpEntityById(1L, FIRST_NAME);
        EmpEntity test = service.getEmpEntityById(1L);
        Assert.assertEquals(expected, test);
    }

    @Test
    public void updateSecondNameInEmpEntityById() throws Exception
    {
        EmpEntity expected = service.getEmpEntityById(1L);
        expected.setSecondName(SECOND_NAME);
        service.updateSecondNameInEmpEntityById(1L, SECOND_NAME);
        EmpEntity test = service.getEmpEntityById(1L);
        Assert.assertEquals(expected, test);
    }

    @Test
    public void updateSurNameInEmpEntityById() throws Exception
    {
        EmpEntity expected = service.getEmpEntityById(1L);
        expected.setSurName(SUR_NAME);
        service.updateSecondNameInEmpEntityById(1L, SUR_NAME);
        EmpEntity test = service.getEmpEntityById(1L);
        Assert.assertEquals(expected, test);
    }

    @Test
    public void deleteEmpEntityById() throws Exception
    {
        service.deleteEmpEntityById(1L);
        Assert.assertNull(service.getEmpEntityById(1L));
    }

    @Test
    public void searchEmpEntity()
    {
        Map<String, Object> attrs = new HashMap<>();
        attrs.put("name", "%Фунт%");
        List<EmpEntity> empEntities = service.searchEmpEntity(attrs);
        Assert.assertEquals(1, empEntities.size());
        EmpEntity entity = getTestEmpEntity1();
        Assert.assertTrue(empEntities.contains(entity));
        attrs.put("job", "%Зицпредседатель%");
        empEntities = service.searchEmpEntity(attrs);
        Assert.assertEquals(1, empEntities.size());
        entity = getTestEmpEntity1();
        Assert.assertTrue(empEntities.contains(entity));
        attrs.put("city", "%Одесса%");
        empEntities = service.searchEmpEntity(attrs);
        Assert.assertEquals(1, empEntities.size());
        entity = getTestEmpEntity1();
        Assert.assertTrue(empEntities.contains(entity));

        attrs.put("age", 75L);
        empEntities = service.searchEmpEntity(attrs);
        Assert.assertEquals(1, empEntities.size());
        entity = getTestEmpEntity1();
        Assert.assertTrue(empEntities.contains(entity));
    }

    @Test
    public void getEntityById()
    {
        EmpEntity empEntity1 = service.getEntityById(1L, EmpEntity.class);
        Assert.assertEquals(getTestEmpEntity1(), empEntity1);
        EmpEntity empEntity2 = service.getEntityById(2L, EmpEntity.class);
        Assert.assertEquals(getTestEmpEntity2(), empEntity2);
        EmpEntity empEntity3 = service.getEntityById(3L, EmpEntity.class);
        Assert.assertEquals(getTestEmpEntity3(), empEntity3);

        Assert.assertNull(service.getEmpEntityById(Long.MAX_VALUE));

        DeptEntity deptEntity1 = service.getEntityById(1L, DeptEntity.class);
        Assert.assertEquals(getTestDeptEntity1(), deptEntity1);
        DeptEntity deptEntity2 = service.getEntityById(2L, DeptEntity.class);
        Assert.assertEquals(getTestDeptEntity2(), deptEntity2);
        DeptEntity deptEntity3 = service.getEntityById(3L, DeptEntity.class);
        Assert.assertEquals(getTestDeptEntity3(), deptEntity3);

        Assert.assertNull(service.getEntityById(Long.MAX_VALUE, DeptEntity.class));
    }

    @Test
    public void getUserEntityByName()
    {
        UserEntity expected = getTestUserEntity1();
        UserEntity user = service.getUserEntityByName("funt");
        Assert.assertEquals(expected, user);
        Assert.assertNull(service.getUserEntityByName("!!!<<<NONE>>>!!!"));
    }

    @Test
    public void deleteEntityById()
    {
        service.deleteEntityById(1L, StatisticEntity.class);
        Assert.assertNull(service.getEntityById(1L, StatisticEntity.class));
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
