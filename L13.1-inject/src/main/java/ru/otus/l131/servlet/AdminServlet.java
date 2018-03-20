package ru.otus.l131.servlet;

/*
 * Created by VSkurikhin at winter 2018.
 */

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otus.l131.auth.AuthAccount;
import ru.otus.l131.db.DBService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * Servlet implementation class HomeServlet.
 * This servlet provides the information for administrators.
 */
public class AdminServlet extends HomeServlet {

    private static final String DEFAULT_USER_NAME = "UNKNOWN";
    private static final String ADMIN_PAGE_TEMPLATE = "admin.html";
    private static final String FORBIDDEN_PAGE_TEMPLATE = "forbidden.html";
    private static final String CACHE_HIT = "cacheHit";
    private static final String CACHE_MISS = "cacheMiss";

    private DBService dbService;
    private AuthAccount authAccount;
    private Workload workload;

    public void init() {
        ApplicationContext context = new ClassPathXmlApplicationContext("SpringBeans.xml");
        authAccount = (AuthAccount) context.getBean("authAccount");
        dbService = (DBService) context.getBean("dbService");
    }

    /**
     * Called by the server to allow a servlet to handle a GET request.
     *
     * @param request contains the request the client has made of the servlet
     * @param response contains the response the servlet sends to the client
     * @throws ServletException
     * @throws IOException
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        Map<String, Object> pageVariables = HomeServlet.createPageVariablesMap(request);
        String login = (String) pageVariables.get(AuthServlet.LOGIN_PARAMETER_NAME);
        if (null != login && authAccount.isAdministrator(login)) {
            if (null == workload) {
                workload = new Workload(dbService);
                workload.run();
            }
            pageVariables.put(CACHE_HIT, dbService.getHitCount());
            pageVariables.put(CACHE_MISS, dbService.getMissCount());

            response.getWriter().println(TemplateProcessor.instance().getPage(
                ADMIN_PAGE_TEMPLATE, pageVariables)
            );

            setOK(response);
        } else {
            response.getWriter().println(TemplateProcessor.instance().getPage(
                FORBIDDEN_PAGE_TEMPLATE, pageVariables)
            );

            setForbidden(response);
        }
    }

    private void setForbidden(HttpServletResponse response) {
        response.setContentType(TEXT_HTML_CHARSET);
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
