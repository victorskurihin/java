package ru.otus.web;

/*
 * Created by VSkurikhin at autumn 2018.
 */

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet("/maxsalary")
public class JDBCMaxSalaryServlet extends HttpServlet
{
    @Resource(name = "jdbc/PostgresMyDB") // for Tomcat
    private DataSource ds;


    public void doGet(HttpServletRequest request, HttpServletResponse response)
    throws IOException, ServletException
    {
        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out = response.getWriter();
        ServletUtil.outHTMLHeader(out, "Home Work 2 хранимые процедуры.");

        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM mygetmax();");
             ResultSet resultSet = ps.executeQuery()
        ) {
            while (resultSet.next()) {
                out.println(
                    "Максимальная зарплата у сотрудника по фамилии: "
                    + resultSet.getString(1)
                );
            }

            out.println("</body>");
            out.println("</html>");
        } catch (SQLException e) {
            throw new ServletException(e);
        }
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
