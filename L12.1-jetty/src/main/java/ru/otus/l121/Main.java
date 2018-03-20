package ru.otus.l121;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.resource.Resource;
import ru.otus.l121.auth.AuthAccount;
import ru.otus.l121.dataset.*;
import ru.otus.l121.db.*;
import ru.otus.l121.servlet.*;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by VSkurikhin at winter 2018.
 *
 * Solution for L12.1
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
    public static final String JAR_FILE = "jar:file:";
    public static final String RU_OTUS_MAIN_CLASS = "ru/otus/l121/Main.class";
    public static final String ADMINS = "admins.properties";

    public static void main(String[] args) throws Exception {
        DBService dbService = new DBServiceImpl();
        AuthAccount authAccount = new AuthAccount(dbService, ADMINS);
        Workload workload = new Workload(dbService);

        authAccount.put("user", "password");
        workload.run();

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

        Server server = new Server(PORT);
        server.setHandler(new HandlerList(resourceHandler, context));

        server.start();
        server.join();
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
