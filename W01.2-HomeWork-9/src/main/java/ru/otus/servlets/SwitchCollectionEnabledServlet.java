package ru.otus.servlets;

/*
 * Created by VSkurikhin at autumn 2018.
 */

import ru.otus.services.StatisticService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static ru.otus.gwt.shared.Constants.REQUEST_VISITS_SWITCH_COLLECTION;
import static ru.otus.gwt.shared.Constants.STAT_SERVICE;

@WebServlet("/" + REQUEST_VISITS_SWITCH_COLLECTION)
public class SwitchCollectionEnabledServlet extends HttpServlet
{
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    {
        StatisticService statService = (StatisticService) getServletContext().getAttribute(STAT_SERVICE);
        statService.setCollectionEnabled( ! statService.isCollectionEnabled());
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
