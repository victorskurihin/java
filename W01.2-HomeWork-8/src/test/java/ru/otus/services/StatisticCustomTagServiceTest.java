package ru.otus.services;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.otus.models.StatisticEntity;
import ru.otus.models.UserEntity;

import javax.management.j2ee.statistics.Statistic;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import java.time.LocalDateTime;
import java.util.Date;

import static org.junit.Assert.*;

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
        dbService = new DbJPAPostgreSQLService(em);
        StatisticEntity entity = new StatisticEntity();
        entity.setId(13L);
        entity.setNameMarker("DEFAULT_MARKER");
        entity.setJspPageName("browsers");
        entity.setIpAddress("127.0.0.1");
        entity.setUserAgent("Wget/1.19.4 (linux-gnu)");
        entity.setClientTime(LocalDateTime.now());
        entity.setServerTime(LocalDateTime.MAX);
        entity.setSessionId("0");
        UserEntity user = new UserEntity();
        user.setId(1L);
        entity.setUser(user);
        entity.setPreviousId(13L);
        dbService.insertIntoStatistic(entity);
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
    public void saveStatisticFromRequestParams()
    {
    }

    @Test
    public void isCollectionEnabled()
    {
    }

    @Test
    public void getAllVisitsStatElements()
    {
    }

    @Test
    public void setCollectionEnabled()
    {
    }

    @Test
    public void isReady()
    {
    }

    @Test
    public void fetchData()
    {
    }

    @Test
    public void getDataXML()
    {
    }

    @Test
    public void getDataJSON()
    {
    }
}