/*
 * VersionServlet.java
 * This file was last modified at 2018.12.03 20:05 by Victor N. Skurikhin.
 * $Id$
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */

package ru.otus.servlets;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static ru.otus.utils.Payments.differentiatedPayment;

@WebServlet("/v1/diff")
public class DiffPayServlet extends HttpServlet
{
    private static final Logger LOGGER = LogManager.getLogger(DiffPayServlet.class.getName());

    private boolean checkKey(Map<String, String[]> map, String key)
    {
        return ! map.containsKey(key) || map.get(key).length != 1 || map.get(key)[0].length() < 1;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
    {
        resp.setContentType("text/plain; charset=UTF-8");

        try (PrintWriter out = resp.getWriter()) {
            Map<String, String[]> parameterMap = req.getParameterMap();

            for (String key : new String[]{"t", "kr", "st"}) {
                if (checkKey(parameterMap, key)) {
                    resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                }
            }
            Integer t = Integer.parseInt(parameterMap.get("t")[0]);
            int kr = Integer.parseInt(parameterMap.get("kr")[0]);
            int st = Integer.parseInt(parameterMap.get("st")[0]);
            double dst = st*0.01/12.0;
            List<Double> result = new ArrayList<>();

            for (int i = 1; i <= t; ++i) {
                out.println(differentiatedPayment(t.doubleValue(), kr, dst, i));
            }
        }
        catch (IOException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
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
