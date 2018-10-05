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
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import ru.otus.dataset.EmployeesRegistryEntity;

@WebServlet("/registry")
public class JPARegistryServlet extends HttpServlet
{
    private static final String PERSISTENCE_UNIT_NAME = "jpa";
    private static final EntityManagerFactory emf =
            Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME); // for Tomcat

    public void doGet(HttpServletRequest request, HttpServletResponse response)
    throws IOException, ServletException
    {
        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out = response.getWriter();
        ServletUtil.outHTMLHeader(out, "Home Work 2 подготовка Application уровня.");

        EntityManager em = emf.createEntityManager(); // for Tomcat
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();
            Query q = em.createQuery(
                "SELECT empl FROM EmployeesRegistryEntity empl ORDER BY empl.id DESC"
            );
            //noinspection unchecked
            List<EmployeesRegistryEntity> result = q.getResultList();
            out.println("<ul>");

            for (EmployeesRegistryEntity entity : result) {
                StringBuilder sb = new StringBuilder("<li>");
                sb.append(entity.getId()).append(" ")
                        .append(entity.getFirstName()).append(' ')
                        .append(entity.getSecondName()).append(' ')
                        .append(entity.getSurName()).append(',').append(' ')
                        .append(entity.getJob());
                out.println(sb);
            }
            out.println("</ul>");
            transaction.commit();
        }
        catch (Exception e) {
            transaction.rollback();
            throw new ServletException(e);
        }
        finally {
            em.close();
        }

        out.println("</body>");
        out.println("</html>");
    }

    /**
     * We are going to perform the same operations for POST requests
     * as for GET methods, so this method just sends the request to
     * the doGet method.
     */

    public void doPost(HttpServletRequest request, HttpServletResponse response)
    throws IOException, ServletException
    {
        doGet(request, response);
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
