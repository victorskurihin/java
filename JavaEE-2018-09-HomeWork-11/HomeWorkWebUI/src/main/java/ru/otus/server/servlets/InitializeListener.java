/*
 * InitializeListener.java
 * This file was last modified at 2018.12.03 20:05 by Victor N. Skurikhin.
 * $Id$
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */

package ru.otus.server.servlets;

import ru.otus.server.services.*;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import static ru.otus.shared.Constants.*;

@WebListener
public class InitializeListener implements ServletContextListener
{
    @Override
    public void contextInitialized(ServletContextEvent sce)
    {
        System.out.println("On start web app ...");
        ServletContext ctx = sce.getServletContext();

        SearchCacheService cacheService = new SearchCacheServiceImpl();
        // StatisticCustomTagService statisticService = new StatisticCustomTagService(dbService, true);

        DataBroadcaster dataPublicBroadcaster = new SequentialDataBroadcaster();
        DataOrigin newsService = new RBCNewsService(ctx);
        DataOrigin forexService = new ForexCBRService(ctx);
        dataPublicBroadcaster.registerDataOrigin(NEWS_SERVICE, newsService);
        dataPublicBroadcaster.registerDataOrigin(FOREX_SERVICE, forexService);
        dataPublicBroadcaster.start();

        DataBroadcaster dataInsideBroadcaster = new SequentialDataBroadcaster();
        // dataInsideBroadcaster.registerDataOrigin(STAT_SERVICE, statisticService);
        dataInsideBroadcaster.start();

        ctx.setAttribute(BROADCASTER_PUBLIC_SERVICE, dataPublicBroadcaster);
        ctx.setAttribute(BROADCASTER_INSIDE_SERVICE, dataInsideBroadcaster);
        ctx.setAttribute(NEWS_SERVICE, newsService);
        ctx.setAttribute(FOREX_SERVICE, forexService);
        ctx.setAttribute(CACHE_SERVICE, cacheService);
        // ctx.setAttribute(STAT_SERVICE, statisticService);

        System.out.println("Ok initialized web app.");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce)
    {
        ServletContext ctx = sce.getServletContext();
        DataBroadcaster dataBroadcaster = (DataBroadcaster) ctx.getAttribute(BROADCASTER_PUBLIC_SERVICE);
        try {
            dataBroadcaster.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("On shutdown web app");
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
