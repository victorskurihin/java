package ru.otus.l121.servlet;

/*
 * Created by VSkurikhin at winter 2018.
 */

import ru.otus.l121.auth.AuthAccount;
import ru.otus.l121.db.DBService;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * TODO
 */
public class AuthServlet extends HttpServlet {

    public static final String LOGIN_PARAMETER_NAME = "login";
    private static final String PASSWORD_PARAMETER_NAME = "password";
    private static final String LOGIN_VARIABLE_NAME = "login";
    private static final String LOGIN_PAGE_TEMPLATE = "auth.html";
    private static final String ADMIN_ROUTE = "admin";
    private static final String HOME_ROUTE = "home";
    private static final String AUTH_ROUTE = "auth";

    private String login;
    private AuthAccount authAccount;

    /**
     * TODO
     * @param login
     * @param authAccount
     */
    public AuthServlet(String login, AuthAccount authAccount) {
        this.login = login;
        this.authAccount = authAccount;
    }

    private static String getPage(String login) throws IOException {
        Map<String, Object> pageVariables = new HashMap<>();

        pageVariables.put(LOGIN_VARIABLE_NAME, login == null ? "" : login);

        return TemplateProcessor.instance().getPage(LOGIN_PAGE_TEMPLATE, pageVariables);
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

        String page = getPage(login); //save to the page
        response.getWriter().println(page);

        setOK(response);
    }

    /**
     * TODO
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        String requestLogin = request.getParameter(LOGIN_PARAMETER_NAME);
        String requestPassword = request.getParameter(PASSWORD_PARAMETER_NAME);

        response.setContentType(HomeServlet.TEXT_HTML_CHARSET);

        if (requestLogin != null && authAccount.auth(requestLogin, requestPassword)) {
            saveToVariable(requestLogin);
            saveToSession(request, requestLogin);
            saveToServlet(request, requestLogin);
            saveToCookie(response, requestLogin);

            if (authAccount.isAdministrator(requestLogin)) {
                response.sendRedirect(ADMIN_ROUTE);
            } else {
                response.sendRedirect(HOME_ROUTE);
            }
        } else {
            response.sendRedirect(AUTH_ROUTE);
        }
    }

    private void saveToCookie(HttpServletResponse response, String requestLogin) {
        response.addCookie(new Cookie("L12.1-login", requestLogin));
    }

    private void saveToServlet(HttpServletRequest request, String requestLogin) {
        request.getServletContext().setAttribute("login", requestLogin);
    }

    private void saveToServlet(HttpServletRequest request, DBService dbService) {
        request.getServletContext().setAttribute("dbService", dbService);
    }

    private void saveToSession(HttpServletRequest request, String requestLogin) {
        request.getSession().setAttribute("login", requestLogin);
    }

    private void saveToVariable(String requestLogin) {
        login = requestLogin != null ? requestLogin : login;
    }

    private void setOK(HttpServletResponse response) {
        response.setContentType(HomeServlet.TEXT_HTML_CHARSET);
        response.setStatus(HttpServletResponse.SC_OK);
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
