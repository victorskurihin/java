package ru.otus.l151;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.websocket.jsr356.server.ServerContainer;
import org.eclipse.jetty.websocket.jsr356.server.deploy.WebSocketServerContainerInitializer;
import ru.otus.l151.app.AuthEndpoint;
import ru.otus.l151.app.ChatEndpoint;

import javax.websocket.DeploymentException;
import javax.websocket.server.ServerEndpointConfig;
import java.net.URL;
import java.util.Objects;

/**
 * Created by VSkurikhin at winter 2018.
 *
 * Solution for L15.1
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
    public static final String ADMINS = "admins.properties";

    public static void main(String[] args) throws Exception {

        Server server = new Server(PORT);
        ServletContextHandler context = new ServletContextHandler(
            ServletContextHandler.SESSIONS
        );

        context.setContextPath("/");
        server.setHandler(context);

        // Add javax.websocket support
        ServerContainer container = WebSocketServerContainerInitializer
            .configureContext(context);

        createEndpoint(context, container, "auth", AuthEndpoint.class, true);
        createEndpoint(context, container, "chat", ChatEndpoint.class, false);

        try {
            server.start();
            server.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void createEndpoint(
        ServletContextHandler context, ServerContainer container, String name, Class<?> clazz, boolean def
    ) throws DeploymentException {
        // Add chat endpoint to server container
        ServerEndpointConfig config = ServerEndpointConfig.Builder.create(
            clazz, "/" + name
        ).build();
        container.addEndpoint(config);

        // Add default servlet (to serve the html/css/js)
        // Figure out where the static files are stored.
        URL urlStatics = Thread.currentThread()
            .getContextClassLoader()
            .getResource(PUBLIC_HTML + "/" + name + ".html");

        Objects.requireNonNull(urlStatics,"Unable to find " + name + ".html in classpath");
        String urlBase = urlStatics.toExternalForm().replaceFirst("/[^/]*$","/");
        ServletHolder defHolder = new ServletHolder("default", new DefaultServlet());
        defHolder.setInitParameter("resourceBase",urlBase);
        defHolder.setInitParameter("dirAllowed","true");
        if (def){
            context.addServlet(defHolder, "/");
        }
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
