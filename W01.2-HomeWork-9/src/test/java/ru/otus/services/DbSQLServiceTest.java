package ru.otus.services;

/*
 * Created by VSkurikhin at autumn 2018.
 */

import org.junit.*;
import ru.otus.db.ImporterSmallXML;
import ru.otus.db.TestDBConf;
import ru.otus.exeptions.ExceptionThrowable;
import ru.otus.models.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.xml.bind.JAXBException;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ru.otus.services.TestDbJPAUtils.*;
import static ru.otus.services.TestExpectedData.*;

public class DbSQLServiceTest implements TestDBConf
{
    private static List<ImporterSmallXML<?>> importeres;

    private EntityManagerFactory emf;
    private DbService service;

    @BeforeClass
    public static void setupServletContext() throws IOException, JAXBException
    {
        importeres = TestDBConf.createImporters();
    }

    @Before
    public void setUp() throws Exception
    {
        emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        service = new DbSQLService(emf);
        TestDBConf.importTestDB(importeres, emf.createEntityManager());
    }

    @After
    public void tearDown() throws Exception
    {
        emf.close();
        service = null;
        emf = null;
    }

    @Test
    public void clearDb() throws ExceptionThrowable
    {
        service.clearDb(null);
        EntityManager em = emf.createEntityManager();
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

    /* LEGACY
    @Test
    public void getEmpEntities()
    {
        List<EmpEntity> result = service.getEmpEntities();
        for (EmpEntity entity : getTestEmpEntitiesList().asList()) {
            Assert.assertTrue(result.contains(entity));
        }
    }

    @Test
    public void getEntities()
    {
        List<DeptEntity> result = service.getEntities(DeptEntity.class);
        for (DeptEntity entity : getTestDeptEntitiesList().asList()) {
            Assert.assertTrue(result.contains(entity));
        }
    }

    @Test
    public void getEmpEntityById()
    {
        EmpEntity entity1 = service.getEmpEntityById(1L);
        Assert.assertEquals(getTestEmpEntity1(), entity1);
        EmpEntity entity2 = service.getEmpEntityById(2L);
        Assert.assertEquals(getTestEmpEntity2(), entity2);
        EmpEntity entity3 = service.getEmpEntityById(3L);
        Assert.assertEquals(getTestEmpEntity3(), entity3);
    }

    @Test
    public void saveEntity()
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
    public void updateFirstNameInEmpEntityById()
    {
        EmpEntity expected = service.getEmpEntityById(1L);
        expected.setFirstName(FIRST_NAME);
        service.updateFirstNameInEmpEntityById(1L, FIRST_NAME);
        EmpEntity test = service.getEmpEntityById(1L);
        Assert.assertEquals(expected, test);
    }

    @Test
    public void updateSecondNameInEmpEntityById()
    {
        EmpEntity expected = service.getEmpEntityById(1L);
        expected.setSecondName(SECOND_NAME);
        service.updateSecondNameInEmpEntityById(1L, SECOND_NAME);
        EmpEntity test = service.getEmpEntityById(1L);
        Assert.assertEquals(expected, test);
    }

    @Test
    public void updateSurNameInEmpEntityById()
    {
        EmpEntity expected = service.getEmpEntityById(1L);
        expected.setSurName(SUR_NAME);
        service.updateSecondNameInEmpEntityById(1L, SUR_NAME);
        EmpEntity test = service.getEmpEntityById(1L);
        Assert.assertEquals(expected, test);
    }

    @Test
    public void deleteEmpEntityById()
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
    */
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
