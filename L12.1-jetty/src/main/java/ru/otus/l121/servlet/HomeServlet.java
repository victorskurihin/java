package ru.otus.l121.servlet;

/*
 * Created by VSkurikhin at winter 2018.
 */

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * TODO
 */
public class HomeServlet extends HttpServlet {

    private static final String DEFAULT_USER_NAME = "UNKNOWN";
    private static final String HOME_PAGE_TEMPLATE = "home.html";
    public static final String TEXT_HTML_CHARSET = "text/html;charset=utf-8";

    protected static
    Map<String, Object> createPageVariablesMap(HttpServletRequest request) {

        Map<String, Object> pageVariables = new HashMap<>();

        pageVariables.put("method", request.getMethod());
        pageVariables.put("URL", request.getRequestURL().toString());
        pageVariables.put("locale", request.getLocale());
        pageVariables.put("sessionId", request.getSession().getId());
        pageVariables.put("parameters", request.getParameterMap().toString());

        //let's get login from session
        String login = (String) request.getSession().getAttribute(
            AuthServlet.LOGIN_PARAMETER_NAME
        );
        pageVariables.put("login", login != null ? login : DEFAULT_USER_NAME);

        return pageVariables;
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

        Map<String, Object> pageVariables = createPageVariablesMap(request);

        response.getWriter().println(TemplateProcessor.instance().getPage(
            HOME_PAGE_TEMPLATE, pageVariables)
        );

        setOK(response);
    }

    void setOK(HttpServletResponse response) {
        response.setContentType(HomeServlet.TEXT_HTML_CHARSET);
        response.setStatus(HttpServletResponse.SC_OK);
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
