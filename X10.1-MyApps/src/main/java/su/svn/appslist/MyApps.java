package su.svn.appslist;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import javax.servlet.*;
import javax.servlet.http.*;

public class MyApps extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response)
    throws IOException, ServletException
    {
        List<App> items = new ArrayList<>();
        items.add(new App(0, "name0", "location0"));
        items.add(new App(1, "name1", "location1"));
        request.setAttribute("items1", items);
        request.getRequestDispatcher("/index.jsp").forward(request, response);
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
