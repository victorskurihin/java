package ru.otus.l131;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.resource.Resource;
import ru.otus.l131.auth.AuthAccount;
import ru.otus.l131.dataset.*;
import ru.otus.l131.db.*;
import ru.otus.l131.servlet.*;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by VSkurikhin.
 *
 * Solution for L13.1
 *
 * PreReq: A DB compatible with Hibernate ORM.
 *
 * Configure application in db/DBServiceImpl.java
 * and resources/admins.properties
 *
 * To start:
 * mvn clean package
 *
 * ./run.sh
 * or
 * run.bat
 */

public class Main {
    private final static int PORT = 8090;
    private final static String PUBLIC_HTML = "public_html";

    private static Map<String, String> loadAdmins(DBService dbService, String fileName)
        throws Exception {

        URL url = Resources.getResource(fileName);
        List<String> lines = Resources.readLines(url, Charsets.UTF_8);
        int index = 1;
        Map<String, String> result = new HashMap<>();

        for (String line : lines) {
            String[] variablePair = line.split("=", 2 );
            if (2 == variablePair.length) {
                String variableName = variablePair[0].trim();
                String variableValue = variablePair[1].trim();

                result.put(variableName, variableValue);
                if (variableName.equals(AuthAccount.ADMIN_NAME)) {
                    UserDataSet adminUser = new UserDataSet(
                        index++, variableValue, null
                    );
                    dbService.save(adminUser);
                }
            }
        }

        dbService.load(1, UserDataSet.class); // One hit

        return result;
    }

    public static void main(String[] args) throws Exception {
        DBService dbService = new DBServiceImpl();
        AuthAccount authAccount = new AuthAccount(loadAdmins(
            dbService, "admins.properties"
        ));
        UserDataSet user = new UserDataSet("user", null);

        dbService.save(user);
        authAccount.put("user", "password");
        dbService.load(0, UserDataSet.class); // One miss

        ResourceHandler resourceHandler = new ResourceHandler();
        Resource resource = Resource.newClassPathResource(PUBLIC_HTML);
        resourceHandler.setBaseResource(resource);

        ServletContextHandler context = new ServletContextHandler(
            ServletContextHandler.SESSIONS
        );

        context.addServlet(new ServletHolder(
            new AuthServlet("anonymous", authAccount)), "/auth"
        );
        context.addServlet(new ServletHolder(
            new AdminServlet(authAccount, dbService)), "/admin"
        );
        context.addServlet(HomeServlet.class, "/home");
        context.setContextPath("/");

        Server server = new Server(PORT);
        server.setHandler(new HandlerList(resourceHandler, context));

        server.start();
        server.join();
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
