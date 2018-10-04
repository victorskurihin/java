package ru.otus.web;

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
import ru.otus.dataset.UserEntity;

@WebServlet("/registry")
public class JPARegistry extends HttpServlet {
    public static final String PERSISTENCE_UNIT_NAME = "jpa";
    private static final EntityManagerFactory emf =
            Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME); // for Tomcat

    public void doGet(HttpServletRequest request, HttpServletResponse response)
    throws IOException, ServletException
    {
        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<head>");
        out.println("<meta http-equiv='Content-Type' content='text/html; charset=UTF-8' />");
        out.println("<title>Home Work 2 Registry</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h3>Home Work 2 Registry</h3>");
        EntityManager em = emf.createEntityManager(); // for Tomcat
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            Query q = em.createQuery("SELECT empl FROM EmployeesRegistryEntity empl ORDER BY empl.id DESC");
            List<EmployeesRegistryEntity> result = q.getResultList();
            out.println("<ul>");

            for (EmployeesRegistryEntity entity : result) {
                StringBuilder sb = new StringBuilder("<li>");
                sb.append(entity.getId()).append(" ")
                        .append(entity.getFirstName()).append(' ')
                        .append(entity.getSecondName()).append(' ')
                        .append(entity.getSurName());
                out.println(sb);
            }
            out.println("</ul>");
            transaction.commit();
        }
        catch (Exception e){
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
