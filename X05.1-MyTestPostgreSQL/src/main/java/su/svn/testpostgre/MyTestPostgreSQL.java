package su.svn.testpostgre;

import java.sql.*;
import java.io.*;
import javax.naming.*;
import javax.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class MyTestPostgreSQL extends HttpServlet {

    String connectionData = "Not Connected";
    int count = -1;

    public void init() {

        try {
            Context ctx = new InitialContext();
            if(ctx == null )
                throw new Exception("Boom - No Context");

            // /jdbc/postgres is the name of the resource above
            DataSource ds = (DataSource)ctx.lookup("java:comp/env/jdbc/PostgresMyDB");

            if (ds != null) {
                Connection connection = ds.getConnection();

                if(connection != null) {
                    connectionData = "Got Connection " + connection.toString();
                    Statement stmt = connection.createStatement();
                    ResultSet rst = stmt.executeQuery(
                        "SELECT * FROM checkout LIMIT 1"
                    );
                    if(rst.next()) {
                        connectionData = rst.getString(1);
                        count++;
                    }
                    connection.close();
                }
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    public String getConnectionData() { return connectionData; }

    public int getCount() { return count;}

    public void doGet(HttpServletRequest request, HttpServletResponse response)
    throws IOException, ServletException
    {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<body>");
        out.println("<head>");
        out.println("<title>My Test PostgreSQL</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h3>My Test PostgreSQL</h3>");
        out.println(
            "Get data from PostgresDB: " + getConnectionData() + " count: " + getCount()
        );
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
