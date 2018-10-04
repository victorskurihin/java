package ru.otus.web;

import ru.otus.dataset.EmployeesRegistryEntity;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/delete/*")
public class JPADelete extends HttpServlet {
    public static final String PERSISTENCE_UNIT_NAME = "jpa";
    private static final EntityManagerFactory emf =
            Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME); // for Tomcat
    private Long Id;

    private static long retrieveUserid(HttpServletRequest req) {
        String pathInfo = req.getPathInfo();
        if (pathInfo.startsWith("/")) {
            pathInfo = pathInfo.substring(1);
        }
        return Long.parseLong(pathInfo);
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doDelete(request, response);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
    throws IOException, ServletException
    {
        doDelete(request, response);
    }

    /**
     * We are going to perform the same operations for POST requests
     * as for GET methods, so this method just sends the request to
     * the doGet method.
     */

    public void doPost(HttpServletRequest request, HttpServletResponse response)
    throws IOException, ServletException
    {
        doDelete(request, response);
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<head>");
        out.println("<meta http-equiv='Content-Type' content='text/html; charset=UTF-8' />");
        out.println("<title>Home Work 2 Registry</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h3>Home Work 2 Max Salary</h3>");

        Id = retrieveUserid(request);
        if (Id == null) return;
        EntityManager em = emf.createEntityManager(); // for Tomcat
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();
            EmployeesRegistryEntity empEntity = em.find(EmployeesRegistryEntity.class, Id);
            if (empEntity == null) return;
            em.remove(empEntity);
            em.flush();
            transaction.commit();
            response.getWriter().println("Employee '" + empEntity.getSurName() + "' has been successfully deleted");
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

}
