package ru.otus.services;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.otus.models.StatisticEntitiesList;
import ru.otus.models.StatisticEntity;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import java.sql.SQLException;
import java.util.List;

import static ru.otus.services.TestExpectedData.getTestStatisticEntity1;
import static ru.otus.services.TestExpectedData.getTestUserEntity1;

public class StatisticCustomTagServiceTest
{
    public static final String PERSISTENCE_UNIT_NAME = "test-jpa";

    private DbService dbService;
    private StatisticCustomTagService service;
    private EntityManagerFactory emf;
    private EntityManager em;

    @Before
    public void setUp() throws Exception
    {
        emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        em = emf.createEntityManager();
        dbService = new DbSQLService(em);
        dbService.saveEntity(getTestUserEntity1());
        dbService.saveEntity(getTestStatisticEntity1());
        service = new StatisticCustomTagService(dbService, true);
    }

    @After
    public void tearDown() throws Exception
    {
        dbService.close();
        emf.close();
        service = null;
        em = null;
    }

    @Test
    public void test1()
    {
        StatisticEntity entity = dbService.getEntityById(1L, StatisticEntity.class);
        System.out.println("entity = " + entity);
    }

    @Test
    public void saveStatisticFromRequestParams()
    {
    }

    @Test
    public void isCollectionEnabled()
    {
    }

    @Test
    public void getAllVisitsStatElements() throws SQLException
    {
        List<StatisticEntity> test = service.getAllVisitsStatElements();
        Assert.assertTrue(test.contains(getTestStatisticEntity1()));
    }

    @Test
    public void setCollectionEnabled()
    {
        service.setCollectionEnabled(false);
        Assert.assertFalse(service.isCollectionEnabled());
        service.setCollectionEnabled(true);
        Assert.assertTrue(service.isCollectionEnabled());
    }

    @Test
    public void fetchDataIsReady() throws SQLException
    {
        Assert.assertFalse(service.isReady());
        service.fetchData();
        Assert.assertTrue(service.isReady());
    }

    @Test
    public void getDataXML() throws SQLException
    {
        service.fetchData();
        String test = service.getDataXML();
        System.out.println("test = " + test);
    }

    @Test
    public void getDataJSON() throws SQLException
    {
        service.fetchData();
        String test = service.getDataJSON();
        Assert.assertTrue(service.isReady());

        Jsonb jsonb = JsonbBuilder.create();
        StatisticEntitiesList list = jsonb.fromJson(test, StatisticEntitiesList.class);
        Assert.assertEquals(service.getAllVisitsStatElements(), list.asList());
    }
}