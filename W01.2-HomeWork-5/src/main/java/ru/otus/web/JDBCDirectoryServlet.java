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
import java.util.Stack;

@WebServlet("/directory")
public class JDBCDirectoryServlet extends HttpServlet
{
    // @Resource(name = "jdbc/PostgresMyDB") // for Tomcat
    @Resource(lookup = "jdbc/PostgresMyDB") // for Glassfish
    private DataSource ds;
    private String directorySQL =
        "WITH RECURSIVE recurs (id, pid, level, title)\n" +
        "  AS (\n" +
        "    SELECT id, pid, CAST(0 AS BIGINT), title FROM dep_directory WHERE pid = 0\n" +
        "    UNION ALL\n" +
        "    SELECT next_to.id, recurs.level + 1, next_to.pid, next_to.title\n" +
        "      FROM recurs \n" +
        "      JOIN dep_directory next_to\n" +
        "        ON recurs.id = next_to.pid\n" +
        "  )\n" +
        "  SELECT * FROM recurs\n;";

    StringBuilder sbAppendOrganizationUnit(ResultSet resultSet, StringBuilder sb)
    throws SQLException
    {
        sb.append("<organizationUnit id='")
          .append(resultSet.getLong("id"))
          .append("' title='")
          .append(resultSet.getString("title"))
          .append("'>\n");
        return sb;
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
    throws IOException, ServletException
    {
        response.setContentType("text/xml; charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        out.println("<directory>");

        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(directorySQL);
             ResultSet resultSet = ps.executeQuery()
        ) {
            long prevLevel = -1;
            StringBuilder sb = new StringBuilder();
            Stack<Object> stack = new Stack<>();

            while (resultSet.next()) {
                long level = resultSet.getLong("level");
                if (prevLevel < level) {
                    sb = sbAppendOrganizationUnit(resultSet, sb);
                    stack.push(new Object());
                } else if (prevLevel > level) {
                    sb.append("</organizationUnit>\n").append("</organizationUnit>\n");
                    sb = sbAppendOrganizationUnit(resultSet, sb);
                    stack.pop();
                } else {
                    sb.append("</organizationUnit>\n");
                    sb = sbAppendOrganizationUnit(resultSet, sb);
                }
                prevLevel = level;
            }

            while ( ! stack.empty()) {
                sb.append("</organizationUnit>\n");
                stack.pop();
            }

            out.print(sb);
            out.println("</directory>");
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
