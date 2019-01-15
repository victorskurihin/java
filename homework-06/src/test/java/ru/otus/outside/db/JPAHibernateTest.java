package ru.otus.outside.db;

import org.h2.tools.RunScript;
import org.hibernate.Session;
import org.hibernate.jdbc.Work;
import org.junit.jupiter.api.*;

import javax.persistence.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.SQLException;

import static ru.otus.outside.utils.TestData.DELETE_FROM_AUTHOR;
import static ru.otus.outside.utils.TestData.DELETE_FROM_AUTHOR_ISBN;
import static ru.otus.outside.utils.TestData.DELETE_FROM_BOOK;

public class JPAHibernateTest
{
    protected static EntityManagerFactory emf;
    protected static EntityManager entityManager;

    @BeforeAll
    public static void init()
    {
        emf = Persistence.createEntityManagerFactory("mnf-pu-test");
        entityManager = emf.createEntityManager();
    }

    @BeforeEach
    public void initializeDatabase()
    {
        entityManager.getTransaction().begin();
        Session session = entityManager.unwrap(Session.class);
        session.doWork(new Work() {
            @Override
            public void execute(Connection connection) throws SQLException
            {
                try {
                    File script = new File(getClass().getResource("/data-test-h2.sql").getFile());
                    RunScript.execute(connection, new FileReader(script));
                    System.out.println("Ok");
                } catch (FileNotFoundException e) {
                    throw new RuntimeException("could not initialize with script");
                }
            }
        });
        entityManager.flush();
        entityManager.getTransaction().commit();
    }

    private int clear(String sql)
    {
        EntityTransaction transaction = entityManager.getTransaction();

        if (!transaction.isActive()) {
            transaction.begin();
        }
        try {
            int count = entityManager.createNativeQuery(sql).executeUpdate();
            transaction.commit();

            return count;
        }
        catch (RollbackException e) {
            throw new RuntimeException(e);
        }
        catch (Exception e) {
            transaction.rollback();
            throw new RuntimeException(e);
        }
    }

    public int clearAuthorIsbn()
    {
        return clear(DELETE_FROM_AUTHOR_ISBN);
    }

    public int clearAuthor()
    {
        return clear(DELETE_FROM_AUTHOR);
    }

    public int clearBook()
    {
        return clear(DELETE_FROM_BOOK);
    }

    @AfterAll
    public static void tearDown()
    {
        entityManager.clear();
        entityManager.close();
        emf.close();
    }
}
