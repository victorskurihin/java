package ru.otus.outside.db;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import javax.persistence.*;

public class JpaDedicatedEntityManagerTest extends JpaEntityManagerTest
{
    protected static EntityManagerFactory emf;
    protected static EntityManager entityManager;

    @BeforeAll
    public static void init()
    {
         emf = Persistence.createEntityManagerFactory("mnf-pu-test");
         entityManager = emf.createEntityManager();
    }

    @Override
    public EntityManager getEntityManager()
    {
        return entityManager;
    }

    @SuppressWarnings("Duplicates")
    @BeforeEach
    public void initializeDatabase()
    {
        recreateDatabase();
    }

    @AfterAll
    public static void tearDown()
    {
        entityManager.clear();
        entityManager.close();
        emf.close();
    }
}
