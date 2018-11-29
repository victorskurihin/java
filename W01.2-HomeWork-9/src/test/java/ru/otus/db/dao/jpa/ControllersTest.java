package ru.otus.db.dao.jpa;

import org.h2.tools.RunScript;
import org.hibernate.Session;
import org.hibernate.jdbc.Work;
import org.junit.*;
import ru.otus.db.dao.DAOController;
import ru.otus.exeptions.ExceptionThrowable;
import ru.otus.models.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static ru.otus.db.TestDBConf.PERSISTENCE_UNIT_NAME;
import static ru.otus.services.TestExpectedData.*;

public class ControllersTest
{
    public static final double EPSILON = Math.ulp(1.0D);

    private static final String TEST = "__ TEST __";
    protected static EntityManagerFactory emf;
    protected static EntityManager em;

    private DeptController deptController;
    private EmpController empController;
    private GroupController groupController;
    private UserController userController;

    @BeforeClass
    public static void initClass() throws FileNotFoundException, SQLException
    {
        emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        em = emf.createEntityManager();
    }

    @Before
    public void setUp() throws Exception
    {
        em.clear();
        Session session = em.unwrap(Session.class);
        session.doWork(new Work() {
            @Override
            public void execute(Connection connection) throws SQLException {
                try {
                    File script = new File(getClass().getResource("/data.sql").getFile());
                    RunScript.execute(connection, new FileReader(script));
                } catch (FileNotFoundException e) {
                    throw new RuntimeException("could not initialize with script");
                }
            }
        });
        deptController = new DeptController(em);
        empController = new EmpController(em);
        groupController = new GroupController(em);
        userController = new UserController(em);
    }

    @After
    public void tearDown(){
        deptController = null;
        empController = null;
        groupController = null;
        userController = null;
    }

    @AfterClass
    public static void tearDownClass(){
        em.clear();
        em.close();
        emf.close();
    }

    <E extends DataSet> void testEmptyGetAll(AbstractController<E, Long> controller)
    throws ExceptionThrowable
    {
        List<E> test = controller.getAll();
        Assert.assertEquals(new ArrayList<>(), test);
    }

    @Test
    public void testEmptyGetAll() throws ExceptionThrowable
    {
        testEmptyGetAll(deptController);
        testEmptyGetAll(empController);
        testEmptyGetAll(groupController);
        testEmptyGetAll(userController);
    }

    @Test
    public void testGetDoesNtEntityById() throws ExceptionThrowable
    {
        Assert.assertNull(deptController.getEntityById(1L));
        Assert.assertNull(empController.getEntityById(1L));
        Assert.assertNull(groupController.getEntityById(1L));
        Assert.assertNull(userController.getEntityById(1L));
    }

    @Test
    public void testGetDoesNtEntityByName() throws ExceptionThrowable
    {
        Assert.assertNull(deptController.getEntityViaClassByName("title", TEST, DeptEntity.class));
        Assert.assertNull(groupController.getEntityViaClassByName("groupname", TEST, GroupEntity.class));
        Assert.assertNull(userController.getEntityViaClassByName("login", TEST, UserEntity.class));
    }

    <E extends DataSet> E getTestCreate(AbstractController<E, Long> controller, E expectedEntity)
    throws ExceptionThrowable
    {
        Assert.assertTrue(controller.create(expectedEntity));
        E testEntity = controller.getEntityById(expectedEntity.getId());
        Assert.assertEquals(expectedEntity, testEntity);
        return testEntity;
    }

    @Test
    public void testCreate() throws ExceptionThrowable
    {
        getTestCreate(deptController, getTestDeptEntity1());
        getTestCreate(groupController, getTestGroupEntity1());
        getTestCreate(userController, getTestUserEntity1());
    }

    void testCreateEmpEntity(EmpEntity entity) throws ExceptionThrowable
    {
        if (null != entity.getDepartment()) {
            getTestCreate(deptController, entity.getDepartment());
        }
        if (null != entity.getUser()) {
            getTestCreate(userController, entity.getUser());
        }
        getTestCreate(empController, entity);
    }

    @Test
    public void testCreateEmpEntity() throws ExceptionThrowable
    {
        testCreateEmpEntity(getTestEmpEntity1());
    }

    private void testPersistEntity(TestEntity testEntity) throws ExceptionThrowable
    {
        testEntity.setId(1L);
        testEntity.setTest(TEST);
        TestController testController = new TestController(em);
        testController.persistEntity(testEntity);
    }

    @Test(expected = ExceptionThrowable.class)
    public void testPersistEntityExceptionThrowable() throws ExceptionThrowable
    {
        TestEntity testEntity = new TestEntity();
        testPersistEntity(testEntity);
        Assert.fail();
    }

    @Test
    public void testPersistEntity() throws ExceptionThrowable
    {
        em.createNativeQuery("CREATE TABLE test ( id BIGINT NOT NULL, test VARCHAR(9))");
        TestEntity testEntity = new TestEntity();
        testPersistEntity(testEntity);
    }

    <E extends DataSet> void testUpdate(AbstractController<E, Long> controller, E expectedEntity, E testEntity)
    throws ExceptionThrowable
    {
        testEntity = controller.getEntityById(testEntity.getId());
        testEntity.setName(TEST);
        Assert.assertNotNull(controller.update(testEntity));
        Assert.assertNotEquals(expectedEntity, testEntity);
    }

    @Test
    public void testUpdate() throws ExceptionThrowable
    {
        testUpdate(deptController,  getTestDeptEntity1(),  getTestCreate(deptController,  getTestDeptEntity1()));
        testUpdate(groupController, getTestGroupEntity1(), getTestCreate(groupController, getTestGroupEntity1()));
        testUpdate(userController,  getTestUserEntity1(),  getTestCreate(userController,  getTestUserEntity1()));
    }

    @Test
    public void testUpdateEmpEntity() throws ExceptionThrowable
    {
        EmpEntity testEmpEntity = getTestEmpEntity1();
        testCreateEmpEntity(testEmpEntity);
        testUpdate(empController, getTestEmpEntity1(), testEmpEntity);
    }

    @Test
    public void testUpdateEmpEntityExceptionThrowable() throws ExceptionThrowable
    {
        EmpEntity testEmpEntity = getTestEmpEntity1();
        testCreateEmpEntity(testEmpEntity);
        testEmpEntity.setFirstName(null);
        testUpdate(empController, getTestEmpEntity1(), testEmpEntity);
    }

    <E extends DataSet> E getTestEntityNotExists(E testEntity) throws ExceptionThrowable
    {
        testEntity.setId(-1L);
        testEntity.setName(null);
        return testEntity;
    }

    @Test(expected = ExceptionThrowable.class)
    public void testUpdateDeptEntityNotExists() throws ExceptionThrowable
    {
        deptController.update(getTestEntityNotExists(new DeptEntity()));
    }

    @Test(expected = ExceptionThrowable.class)
    public void testUpdateGroupEntityNotExists() throws ExceptionThrowable
    {
        groupController.update(getTestEntityNotExists(new GroupEntity()));
    }

    @Test(expected = ExceptionThrowable.class)
    public void testUpdateNotExists() throws ExceptionThrowable
    {
        userController.update(getTestEntityNotExists(new UserEntity()));
    }

    <E extends DataSet> void testDeleteId1(DAOController<E, Long> controller, E expected) throws ExceptionThrowable
    {
        controller.create(expected);
        Assert.assertNotNull(controller.getEntityById(1L));

        Assert.assertTrue(controller.delete(1L));
        Assert.assertNull(controller.getEntityById(1L));
    }

    @Test
    public void testDelete() throws ExceptionThrowable
    {
        testDeleteId1(deptController, getTestDeptEntity1());
        testDeleteId1(groupController, getTestGroupEntity1());
        testDeleteId1(userController, getTestUserEntity1());
    }

    @Test
    public void testDeleteEmpEntity() throws ExceptionThrowable
    {
        testCreateEmpEntity(getTestEmpEntity1());
        testDeleteId1(empController, getTestEmpEntity1());
    }

    @Test(expected = ExceptionThrowable.class)
    public void testDeleteEmpEntityExceptionThrowable1() throws ExceptionThrowable
    {
        testCreateEmpEntity(getTestEmpEntity1());
        testDeleteId1(deptController, getTestEmpEntity1().getDepartment());
        Assert.fail();
    }

    @Test(expected = ExceptionThrowable.class)
    public void testDeleteEmpEntityExceptionThrowable2() throws ExceptionThrowable
    {
        testCreateEmpEntity(getTestEmpEntity1());
        testDeleteId1(userController, getTestEmpEntity1().getUser());
        Assert.fail();
    }

    @Test
    public void testGetMaxSalaryEmpEntity() throws ExceptionThrowable
    {
        testCreateEmpEntity(getTestEmpEntity1());
        testCreateEmpEntity(getTestEmpEntity2());
        testCreateEmpEntity(getTestEmpEntity3());
        long maxSalary = empController.getMaxSalary();
        Assert.assertEquals(getTestEmpEntity1().getSalary().longValue(), maxSalary);
        double avgSalary = empController.getAvgSalary();
        Assert.assertTrue(Math.abs(avgSalary - 11.333333333333334) < EPSILON);
    }

    @Test
    public void testGetAll() throws ExceptionThrowable
    {
        Assert.assertNotNull(deptController.create(getTestDeptEntity1()));
        Assert.assertNotNull(deptController.create(getTestDeptEntity2()));
        Assert.assertNotNull(deptController.create(getTestDeptEntity3()));
        List<DeptEntity> test = deptController.getAll();
        Assert.assertEquals(3, test.size());
        Assert.assertTrue(test.contains(getTestDeptEntity1()));
        Assert.assertTrue(test.contains(getTestDeptEntity2()));
        Assert.assertTrue(test.contains(getTestDeptEntity3()));
    }
}