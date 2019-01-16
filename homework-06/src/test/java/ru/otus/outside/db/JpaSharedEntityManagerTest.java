package ru.otus.outside.db;

import org.junit.jupiter.api.*;

import javax.persistence.*;

public class JpaSharedEntityManagerTest extends JpaEntityManagerTest
{
    protected static EntityManagerFactory emf;
    protected static EntityManager entityManager;

    @BeforeEach
    public void initializeDatabase()
    {
        emf = Persistence.createEntityManagerFactory("mnf-pu-test");
        entityManager = emf.createEntityManager();
        entityManager.getTransaction().begin();
        recreateDatabase();
        entityManager.flush();
        entityManager.getTransaction().commit();
    }

    @Override
    public EntityManager getEntityManager()
    {
        return entityManager;
    }

    @AfterEach
    public void tearDownEach()
    {
        entityManager.clear();
        entityManager.close();
        emf.close();
    }
}
