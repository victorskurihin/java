package ru.otus.l131.servlet;

import org.springframework.beans.factory.annotation.Autowired;
import ru.otus.l131.auth.AuthAccount;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Servlet implementation class AuthServlet.
 * This servlet is responsible for authenticating users.
 */
public class AuthServlet extends AbstractServlet {

    public static final String LOGIN_PARAMETER_NAME = "login";
    public static final String TEXT_HTML_CHARSET = "text/html;charset=utf-8";

    private static final String PASSWORD_PARAMETER_NAME = "password";
    private static final String LOGIN_VARIABLE_NAME = "login";
    private static final String LOGIN_PAGE_TEMPLATE = "auth.html";
    private static final String ADMIN_ROUTE = "admin";
    private static final String HOME_ROUTE = "home";
    private static final String AUTH_ROUTE = "auth";

    private String login = "anonymous";

    @Autowired
    private AuthAccount authAccount;

    public AuthAccount getAuthAccount() {
        return authAccount;
    }

    public void setAuthAccount(AuthAccount authAccount) {
        this.authAccount = authAccount;
    }

    private static String getPage(String login) throws IOException {
        Map<String, Object> pageVariables = new HashMap<>();
        pageVariables.put(LOGIN_VARIABLE_NAME, login == null ? "" : login);

        return TemplateProcessor.instance().getPage(LOGIN_PAGE_TEMPLATE, pageVariables);
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        authAccount.put("user", "password");
    }

    /**
     * Called by the server to allow a servlet to handle a GET request.
     * The HTTP GET method allows the client to request a representation of
     * the specified resource.
     *
     * @param request contains the request the client has made of the servlet
     * @param response contains the response the servlet sends to the client
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
     * Called by  the server  to allow  a servlet  to handle  a POST  request.
     * The HTTP POST method allows the client to send data of unlimited length
     * to the Web server a single time.
     *
     * @param request contains the request the client has made of the servlet
     * @param response contains the response the servlet sends to the client
     * @throws ServletException
     * @throws IOException
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        String requestLogin = request.getParameter(LOGIN_PARAMETER_NAME);
        String requestPassword = request.getParameter(PASSWORD_PARAMETER_NAME);

        response.setContentType(TEXT_HTML_CHARSET);

        if (null != requestLogin && authAccount.auth(requestLogin, requestPassword)) {
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
        response.addCookie(new Cookie("L13.1-login", requestLogin));
    }

    private void saveToServlet(HttpServletRequest request, String requestLogin) {
        request.getServletContext().setAttribute("login", requestLogin);
    }

    private void saveToSession(HttpServletRequest request, String requestLogin) {
        request.getSession().setAttribute("login", requestLogin);
    }

    private void saveToVariable(String requestLogin) {
        login = requestLogin != null ? requestLogin : login;
    }

    private void setOK(HttpServletResponse response) {
        response.setContentType(TEXT_HTML_CHARSET);
        response.setStatus(HttpServletResponse.SC_OK);
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
