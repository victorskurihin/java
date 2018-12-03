/*
 * CorporateTaxServlet.java
 * This file was last modified at 2018.12.01 16:12 by Victor N. Skurikhin.
 * $Id$
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */

package ru.otus.soap.wservice.corptax;

import ru.otus.soap.wsclient.corptax.CorporateTaxProvider;
import ru.otus.soap.wsclient.corptax.CorporateTaxWebService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.WebServiceRef;
import java.io.PrintWriter;

import static ru.otus.gwt.shared.Constants.REQUEST_CORPORATE_TAX;

@WebServlet("/" + REQUEST_CORPORATE_TAX)
public class CorporateTaxServlet extends HttpServlet
{
    @WebServiceRef
    private CorporateTaxWebService service;

    private static final Logger LOGGER = LogManager.getLogger(CorporateTaxServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
    {
        LOGGER.info("doGet");

        resp.setHeader("Content-Type", "application/json; charset=UTF-8");
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setHeader("Access-Control-Allow-Methods", "GET");

        try (PrintWriter pw = resp.getWriter()) {
            CorporateTaxProvider port = service.getCorporateTaxProviderPort();
            Double taxRateReportingPeriod = port.getCurrentTax(1000000.0, 200000.0, 20.0);

            pw.write(String.format("{ 'tax-rate-reporting-period' : %.2f }", taxRateReportingPeriod));
            LOGGER.info("{ tax-rate-reporting-period: {} }", taxRateReportingPeriod);
        }
        catch (Exception e) {
            LOGGER.error(e);
        }
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
