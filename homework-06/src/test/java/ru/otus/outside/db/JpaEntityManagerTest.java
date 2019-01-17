package ru.otus.outside.db;

import org.h2.tools.RunScript;
import org.hibernate.Session;
import org.hibernate.jdbc.Work;

import javax.persistence.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.SQLException;

import static ru.otus.outside.utils.TestData.*;

public abstract class JpaEntityManagerTest
{
    public abstract EntityManager getEntityManager();

    public void recreateDatabase()
    {
        Session session = getEntityManager().unwrap(Session.class);
        session.doWork(new Work() {
            @Override
            public void execute(Connection connection) throws SQLException
            {
                try {
                    File script = new File(getClass().getResource("/data-test-h2.sql").getFile());
                    RunScript.execute(connection, new FileReader(script));
                } catch (FileNotFoundException e) {
                    throw new RuntimeException("could not initialize with script");
                }
            }
        });
    }

    private int nativeQuery(EntityManager entityManager, String sql)
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
            System.err.println("Catch RuntimeException:e = " + e);
            throw new RuntimeException(e);
        }
        catch (Exception e) {
            System.err.println("Catch Exception:e = " + e);
            transaction.rollback();
            System.err.println("rollback.");
            throw new RuntimeException(e);
        }
    }

    public void runInTransaction(Runnable r)
    {
        EntityTransaction transaction = getEntityManager().getTransaction();

        if (!transaction.isActive()) {
            transaction.begin();
        }
        try {
            r.run();
            transaction.commit();
        }
        catch (RollbackException e) {
            System.err.println("Catch RollbackException:e = " + e);
            throw new RuntimeException(e);
        }
        catch (RuntimeException e) {
            System.err.println("Catch RuntimeException:e = " + e);
            transaction.rollback();
        }
    }

    public int clearAuthorIsbn()
    {
        return nativeQuery(getEntityManager(), DELETE_FROM_AUTHOR_ISBN);
    }

    public int clearAuthor()
    {
        return nativeQuery(getEntityManager(), DELETE_FROM_AUTHOR);
    }

    public int clearGenre()
    {
        return nativeQuery(getEntityManager(), DELETE_FROM_GENRE);
    }

    public int clearBook()
    {
        return nativeQuery(getEntityManager(), DELETE_FROM_BOOK);
    }

    public int clearPublisher()
    {
        return nativeQuery(getEntityManager(), DELETE_FROM_PUBLISHER);
    }

    public int clearBookReview()
    {
        return nativeQuery(getEntityManager(), DELETE_FROM_BOOK_REVIEW);
    }
}
