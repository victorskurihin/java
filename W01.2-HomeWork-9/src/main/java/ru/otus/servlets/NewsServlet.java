/*
 * NewsServlet.java
 * This file was last modified at 2018.12.01 14:22 by Victor N. Skurikhin.
 * $Id$
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */

package ru.otus.servlets;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.otus.services.DataOrigin;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

import static ru.otus.gwt.shared.Constants.NEWS_SERVICE;
import static ru.otus.gwt.shared.Constants.REQUEST_NEWS;

@WebServlet("/" + REQUEST_NEWS)
public class NewsServlet extends HttpServlet
{
    private static final Logger LOGGER = LogManager.getLogger(NewsServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
    {
        ServletContext sc = getServletContext();
        DataOrigin newsService = (DataOrigin) sc.getAttribute(NEWS_SERVICE);

        resp.setContentType("text/json; charset=UTF-8");

        try (PrintWriter out = resp.getWriter()) {
            String news = newsService.getDataJSON();
            String mycallback = req.getParameter("mycallback");
            out.println(mycallback + '(' + news + ')');
            LOGGER.info("Gave: " + news.substring(0, 10) + " ...");
        } catch (Exception e) {
            LOGGER.error(e);
        }
    }

    @Override
    protected void doHead(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        super.doHead(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
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
