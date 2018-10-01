package ru.otus;

import java.sql.*;
import java.io.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Resource;
import javax.naming.*;
import javax.sql.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.*;
import javax.servlet.http.*;

@WebServlet("/jdbc")
public class JDBCServletHomeWork2 extends HttpServlet {
    @Resource(name = "jdbc/PostgresMyDB") // for Tomcat
    private DataSource ds;


    public void doGet(HttpServletRequest request, HttpServletResponse response)
    throws IOException, ServletException
    {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<body>");
        out.println("<head>");
        out.println("<title>Home Work 2</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h3>Home Work 2</h3>");

        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM users");
             ResultSet resultSet = ps.executeQuery()){
            StringBuilder sb = new StringBuilder();
            while(resultSet.next()){
                sb.append(
                        Stream.of(
                                resultSet.getString("id") + " " + resultSet.getString("login")
                        ).collect(Collectors.joining("|"))
                );
            }
            out.println(sb.toString());
        } catch (SQLException e) {
            throw new ServletException(e);
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
