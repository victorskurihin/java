package ru.otus.web;

/*
 * Created by VSkurikhin at autumn 2018.
 */

import javax.persistence.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

@WebServlet(urlPatterns = {"/jsonmarshal", "/jsonmarshal/*"})
public class MarshalJsonServlet extends HttpServlet
{
    private static final String PERSISTENCE_UNIT_NAME = "jpa";
    private static final EntityManagerFactory emf =
            Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME); // for Tomcat
    static final String GET = "get";
    static final String OK = "ok";

    public void doGet(HttpServletRequest request, HttpServletResponse response)
    throws IOException, ServletException
    {
        response.setContentType("text/xml; charset=UTF-8");
        PrintWriter out = response.getWriter();

        EntityManager em = emf.createEntityManager(); // for Tomcat
        EntityTransaction transaction = em.getTransaction();

        try {
            String command = ServletUtil.retrieveCommand(request);
            if (command == null) {
                command = OK;
            }
            if (command.equals(OK)) {
                ServletUtil.okXML(out);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
