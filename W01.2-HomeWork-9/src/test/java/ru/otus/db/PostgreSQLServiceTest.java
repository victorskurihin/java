package ru.otus.db;

import org.junit.*;
import ru.otus.exeptions.ExceptionThrowable;
import ru.otus.models.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletContext;
import javax.xml.bind.JAXBException;
import java.io.IOException;

import static ru.otus.db.TestDBConf.PERSISTENCE_UNIT_NAME;
import static ru.otus.services.TestDbJPAUtils.countRowsEntities;

public class PostgreSQLServiceTest
{
    private static ServletContext ctx;

    private EntityManagerFactory emf;
    private PostgreSQLService service;

    @BeforeClass
    public static void setupServletContext() throws IOException, JAXBException
    {
        ctx = TestDBConf.getMockedServletContext();
    }

    @Before
    public void setUp() throws Exception
    {
        emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        service = new PostgreSQLService(emf);
    }

    @After
    public void tearDown() throws Exception
    {
        // em.close(); Will close in testImportDb()
        emf.close();
    }

    @Test
    public void testImportDb() throws ExceptionThrowable
    {
        service.importDb(ctx);
        EntityManager em = emf.createEntityManager();
        Assert.assertEquals(3L, countRowsEntities(em, DeptEntity.class));
        Assert.assertEquals(1L, countRowsEntities(em, UserEntity.class));
        Assert.assertEquals(3L, countRowsEntities(em, EmpEntity.class));
        Assert.assertEquals(1L, countRowsEntities(em, GroupEntity.class));
        // Assert.assertEquals(3L, countRowsEntities(em, StatisticEntity.class));
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
