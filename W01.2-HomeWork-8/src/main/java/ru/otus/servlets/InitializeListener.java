package ru.otus.servlets;

/*
 * Created by VSkurikhin at autumn 2018.
 */

import ru.otus.services.*;
import ru.otus.services.SearchCacheService;
import ru.otus.services.SearchCacheServiceImpl;

import javax.annotation.Resource;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.sql.DataSource;

import static ru.otus.gwt.shared.Constants.*;

@WebListener
public class InitializeListener implements ServletContextListener
{
    // @Resource(name = "jdbc/PostgresMyDB") // for Tomcat
    @Resource(lookup = "jdbc/PostgresMyDB") // for Glassfish
    private DataSource dataSource;

    public static final String PERSISTENCE_UNIT_NAME = "jpa";
    // private static final EntityManagerFactory emf =
    // Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME); // for Tomcat
    @PersistenceUnit(unitName = PERSISTENCE_UNIT_NAME)
    private EntityManagerFactory emf; // for Glassfish

    @Override
    public void contextInitialized(ServletContextEvent sce)
    {
        System.out.println("On start web app ...");
        ServletContext ctx = sce.getServletContext();

        DbService dbService = new DbJPAPostgreSQLService(emf.createEntityManager());
        SearchCacheService cacheService = new SearchCacheServiceImpl();
        StatisticService statisticService = new StatisticCustomTagService(true);

        DataRouter dataRouter = new SequentialDataRouter();
        DataOrigin newsService = new RBCNewsService(ctx);
        DataOrigin forexService = new ForexCBRService(ctx);
        dataRouter.registerDataOrigin(NEWS_SERVICE, newsService);
        dataRouter.registerDataOrigin(FOREX_SERVICE, forexService);
        dataRouter.start();

        ctx.setAttribute(DB_SERVICE, dbService);
        ctx.setAttribute(ROUTER_SERVICE, dataRouter);
        ctx.setAttribute(NEWS_SERVICE, newsService);
        ctx.setAttribute(FOREX_SERVICE, forexService);
        ctx.setAttribute(CACHE_SERVICE, cacheService);
        ctx.setAttribute(STAT_SERVICE, statisticService);

        System.out.println("Ok initialized web app.");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce)
    {
        ServletContext ctx = sce.getServletContext();
        DbService dbService = (DbService) ctx.getAttribute(DB_SERVICE);
        DataRouter dataRouter = (DataRouter) ctx.getAttribute(ROUTER_SERVICE);
        try {
            dbService.close();
            dataRouter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("On shutdown web app");
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
