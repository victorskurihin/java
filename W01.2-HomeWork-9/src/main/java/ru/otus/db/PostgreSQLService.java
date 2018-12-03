/*
 * PostgreSQLService.java
 * This file was last modified at 2018.12.01 15:19 by Victor N. Skurikhin.
 * $Id$
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */

package ru.otus.db;

import ru.otus.exeptions.ExceptionThrowable;

import javax.persistence.*;
import javax.servlet.ServletContext;

public class PostgreSQLService implements DBConf
{
    public static final String PERSISTENCE_UNIT_NAME = "jpa";
    // private static final EntityManagerFactory emf =
    // Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME); // for Tomcat
    @PersistenceUnit(unitName = PERSISTENCE_UNIT_NAME)
    private EntityManagerFactory emf; // for Glassfish

    private static final String ALTER_SEQ_RESTART_WITH_1 = "ALTER SEQUENCE dept_id_seq RESTART WITH 1";
    private static final String ALTER_SEQ_RESTART_WITH_2 = "ALTER SEQUENCE emp_id_seq RESTART WITH 1";
    private static final String ALTER_SEQ_RESTART_WITH_3 = "ALTER SEQUENCE group_id_seq RESTART WITH 1";
    private static final String ALTER_SEQ_RESTART_WITH_4 = "ALTER SEQUENCE statistic_id_seq RESTART WITH 1";
    private static final String ALTER_SEQ_RESTART_WITH_5 = "ALTER SEQUENCE user_id_seq RESTART WITH 1";

    public PostgreSQLService()
    {
        emf.createEntityManager();
    }

    public PostgreSQLService(EntityManagerFactory entityManagerFactory)
    {
        this.emf = entityManagerFactory;
    }

    protected EntityManagerFactory getEntityManagerFactory()
    {
        return emf;
    }

    public EntityManager getEntityManager()
    {
        return emf.createEntityManager();
    }

    public void clearDb(ServletContext sc) throws ExceptionThrowable
    {
        EntityManager em = getEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();
            for (String table : ORDER_OF_DELETE_TABLES) {
                em.createNativeQuery("DELETE FROM " + table + " CASCADE").executeUpdate();
            }
            em.createNativeQuery(ALTER_SEQ_RESTART_WITH_1).executeUpdate();
            em.createNativeQuery(ALTER_SEQ_RESTART_WITH_2).executeUpdate();
            em.createNativeQuery(ALTER_SEQ_RESTART_WITH_3).executeUpdate();
            em.createNativeQuery(ALTER_SEQ_RESTART_WITH_4).executeUpdate();
            em.createNativeQuery(ALTER_SEQ_RESTART_WITH_5).executeUpdate();
            transaction.commit();
        }
        catch (RollbackException e) {
            throw new ExceptionThrowable(e);
        }
        catch (Exception e) {
            transaction.rollback();
            throw new ExceptionThrowable(e);
        }
        finally {
            em.close();
        }
    }

    public void importDb(ServletContext sc) throws ExceptionThrowable
    {
        EntityManager em = getEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();
            for (String table : TABLES) {
                DBConf.createImporterXML(sc, FILE_LOCATIONS.get(table)).saveEntities(em);
            }
            transaction.commit();
        }
        catch (RollbackException e) {
            throw new ExceptionThrowable(e);
        }
        catch (Exception e) {
            transaction.rollback();
            throw new ExceptionThrowable(e);
        }
        finally {
            em.close();
        }
    }

    public void exportDb(ServletContext sc)
    {
        // TODO
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
