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
        em.createQuery("DELETE FROM UserEntity");
        em.createQuery("DELETE FROM GroupEntity");
        em.createQuery("DELETE FROM DeptEntity");
        em.createQuery("DELETE FROM EmpEntity");
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

    <E extends DataSet> E getTestCreate(AbstractController<E, Long> controller, E expectedEntity)
    throws ExceptionThrowable
    {
        Assert.assertTrue(controller.create(expectedEntity));
        E testEntity = controller.getEntityById(1L);
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
        getTestCreate(deptController, entity.getDepartment());
        getTestCreate(userController, entity.getUser());
        getTestCreate(empController, entity);
    }

    @Test
    public void testCreateEmpEntity() throws ExceptionThrowable
    {
        testCreateEmpEntity(getTestEmpEntity1());
    }

    <E extends DataSet> void testUpdate(AbstractController<E, Long> controller, E expectedEntity, E testEntity)
    throws ExceptionThrowable
    {
        testEntity = controller.getEntityById(testEntity.getId());
        testEntity.setName("__ TEST __");
        Assert.assertNotEquals(expectedEntity, testEntity);
    }

    @Test
    public void testUpdate() throws ExceptionThrowable
    {
        testUpdate(deptController, getTestDeptEntity1(),  getTestCreate(deptController, getTestDeptEntity1()));
        testUpdate(groupController, getTestGroupEntity1(),  getTestCreate(groupController, getTestGroupEntity1()));
        testUpdate(userController, getTestUserEntity1(),  getTestCreate(userController, getTestUserEntity1()));
    }

    @Test
    public void testUpdateEmpEntity() throws ExceptionThrowable
    {
        EmpEntity testEmpEntity = getTestEmpEntity1();
        testCreateEmpEntity(testEmpEntity);
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