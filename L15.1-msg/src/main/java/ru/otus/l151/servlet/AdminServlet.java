package ru.otus.l151.servlet;

/*
 * Created by VSkurikhin at winter 2018.
 */

import ru.otus.l151.auth.AuthAccount;
import ru.otus.l151.db.DBService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * TODO
 */
public class AdminServlet extends HomeServlet {

    private static final String DEFAULT_USER_NAME = "UNKNOWN";
    private static final String ADMIN_PAGE_TEMPLATE = "admin.html";
    private static final String FORBIDDEN_PAGE_TEMPLATE = "forbidden.html";
    private static final String CACHE_HIT = "cacheHit";
    private static final String CACHE_MISS = "cacheMiss";

    private DBService dbService;
    private AuthAccount authAccount;

    /**
     * TODO
     * @param authAccount
     * @param dbService
     */
    public AdminServlet(AuthAccount authAccount, DBService dbService) {
        super();
        this.authAccount = authAccount;
        this.dbService = dbService;
    }

    /**
     * TODO
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        Map<String, Object> pageVariables = HomeServlet.createPageVariablesMap(request);
        String login = (String) pageVariables.get(AuthServlet.LOGIN_PARAMETER_NAME);
        String login2 = (String) request.getSession().getAttribute("login");

        if (null != login && authAccount.isAdministrator(login)) {
            pageVariables.put(CACHE_HIT, dbService.getHitCount());
            pageVariables.put(CACHE_MISS, dbService.getMissCount());
            pageVariables.put("login2", login2);

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
