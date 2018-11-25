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

@WebServlet("/" + "corporate-tax")
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
            Double taxRateReportingPeriod = port.getCurrentTax(new Double(1000000.0),  new Double(200000.0), new Double(20.0));
            pw.write(String.format("{ 'tax-rate-reporting-period' : %.2f }", taxRateReportingPeriod));
            LOGGER.info("{ tax-rate-reporting-period: {} }", taxRateReportingPeriod);
        } catch (Exception e) {
            LOGGER.error(e);
        }
    }
}
