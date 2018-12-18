package ru.otus.db;

import ru.otus.cfg.CFGProperties;
import ru.otus.utils.ResourceAsStream;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static ru.otus.db.DBConf.FILE_LOCATIONS;
import static ru.otus.db.DBConf.ORDER_OF_DELETE_TABLES;
import static ru.otus.db.DBConf.TABLES;

@Singleton
@Startup
public class InitializePostgreSQL
{
    public static final String PERSISTENCE_UNIT_NAME = "jpa";

    @PersistenceContext(unitName = PERSISTENCE_UNIT_NAME)
    private EntityManager em;

    @EJB
    CFGProperties cfgProperties;

    protected EntityManager getEntityManager()
    {
        return em;
    }

    void setEntityManager(EntityManager emf)
    {
        this.em = em;
    }

    private static final String ALTER_SEQ_RESTART_WITH_1 = "ALTER SEQUENCE dept_id_seq RESTART WITH 1";
    private static final String ALTER_SEQ_RESTART_WITH_2 = "ALTER SEQUENCE emp_id_seq RESTART WITH 1";
    private static final String ALTER_SEQ_RESTART_WITH_3 = "ALTER SEQUENCE group_id_seq RESTART WITH 1";
    private static final String ALTER_SEQ_RESTART_WITH_4 = "ALTER SEQUENCE statistic_id_seq RESTART WITH 1";
    private static final String ALTER_SEQ_RESTART_WITH_5 = "ALTER SEQUENCE user_id_seq RESTART WITH 1";

    @PostConstruct
    public void importDb()
    {
        EntityManager em = getEntityManager();

        try {
            for (String table : TABLES) {
                String filename = cfgProperties.getProperty(FILE_LOCATIONS.get(table));
                ResourceAsStream resStream = new ResourceAsStream(filename);
                DBConf.createImporterXML(resStream).saveEntities(em);
            }
        }
        catch (Exception e) {
            throw new RuntimeException(e); // TODO Custom Exception
        }
    }

    @PreDestroy
    public void clearDb()
    {
        try {
            for (String table : ORDER_OF_DELETE_TABLES) {
                em.createNativeQuery("DELETE FROM " + table + " CASCADE").executeUpdate();
            }
            em.createNativeQuery(ALTER_SEQ_RESTART_WITH_1).executeUpdate();
            em.createNativeQuery(ALTER_SEQ_RESTART_WITH_2).executeUpdate();
            em.createNativeQuery(ALTER_SEQ_RESTART_WITH_3).executeUpdate();
            em.createNativeQuery(ALTER_SEQ_RESTART_WITH_4).executeUpdate();
            em.createNativeQuery(ALTER_SEQ_RESTART_WITH_5).executeUpdate();
        }
        catch (Exception e) {
            throw new RuntimeException(e); // TODO Custom Exception
        }
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
