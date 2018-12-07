/*
 * CollectStatisticServlet.java
 * This file was last modified at 2018.12.03 20:05 by Victor N. Skurikhin.
 * $Id$
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */

package ru.otus.servlets;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.otus.services.DbService;
import ru.otus.services.StatisticService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.PrintWriter;

import static ru.otus.gwt.shared.Constants.*;

@WebServlet("/" + REQUEST_COLLECT_STATISTIC)
public class CollectStatisticServlet extends HttpServlet
{
    private static final Logger LOGGER = LogManager.getLogger(CollectStatisticServlet.class.getName());

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
    {
        LOGGER.info("doPost");

        resp.setHeader("Content-Type", "application/json; charset=UTF-8");
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setHeader("Access-Control-Allow-Methods", "POST");

        try (PrintWriter pw = resp.getWriter()) {
            StatisticService statService = (StatisticService) getServletContext().getAttribute(STAT_SERVICE);

            if (statService.isCollectionEnabled()) {
                DbService dbService = (DbService) getServletContext().getAttribute(DB_SERVICE);
                long result = statService.saveStatisticFromRequestParams(req, dbService);
                LOGGER.info("visits_stat_id: {}", result);
                pw.write("{\"visits_stat_id\":\"" + result + "\"}");
            }
            else {
                pw.write("{\"visits_stat_id\":\"-1\"}");
            }
        }
        catch (Exception e) {
            LOGGER.error(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
    {
        resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
    }

    @Override
    protected void doHead(HttpServletRequest req, HttpServletResponse resp)
    {
        resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp)
    {
        resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
    {
        resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
    }

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp)
    {
        resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
    }

    @Override
    protected void doTrace(HttpServletRequest req, HttpServletResponse resp)
    {
        resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
