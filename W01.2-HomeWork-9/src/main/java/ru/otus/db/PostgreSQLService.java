package ru.otus.db;

/*
 * Created by VSkurikhin at autumn 2018.
 */

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.servlet.ServletContext;
import java.io.Closeable;

public class PostgreSQLService implements DBConf, Closeable
{
    protected EntityManager em;

    private static final String ALTER_SEQ_RESTART_WITH_1 = "ALTER SEQUENCE emp_id_seq RESTART WITH 1";
    private static final String ALTER_SEQ_RESTART_WITH_2 = "ALTER SEQUENCE hibernate_sequence RESTART WITH 1";
    private static final String ALTER_SEQ_RESTART_WITH_3 = "ALTER SEQUENCE statistic_id_seq RESTART WITH 1";

    public PostgreSQLService(EntityManager em)
    {
        this.em = em;
    }

    public void clearDb(ServletContext sc) throws Exception
    {
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();
            for (String table : ORDER_OF_DELETE_TABLES) {
                em.createNativeQuery("DELETE FROM " + table + " CASCADE").executeUpdate();
            }
            em.createNativeQuery(ALTER_SEQ_RESTART_WITH_1).executeUpdate();
            em.createNativeQuery(ALTER_SEQ_RESTART_WITH_2).executeUpdate();
            em.createNativeQuery(ALTER_SEQ_RESTART_WITH_3).executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        }
    }

    public void importDb(ServletContext sc) throws Exception
    {
        for (String table : TABLES) {
            DBConf.createImporterXML(sc, FILE_LOCATIONS.get(table)).saveEntities(em);
        }
    }

    public void exportDb(ServletContext sc)
    {
        // TODO
    }

    @Override
    public void close()
    {
        em.close();
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
