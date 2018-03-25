package ru.otus.l151.app;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.websocket.jsr356.server.deploy.WebSocketServerContainerInitializer;
import ru.otus.l151.messageSystem.Address;

import javax.servlet.ServletException;
import javax.websocket.DeploymentException;
import javax.websocket.server.ServerContainer;
import javax.websocket.server.ServerEndpointConfig;
import java.net.URL;
import java.util.Objects;

public class EndpointFabric {
    private final static String PUBLIC_HTML = "public_html";

    private ServletContextHandler context;

    // Add javax.websocket support
    private ServerContainer container;

    private MessageSystemContext messageSystemContext;

    private boolean isDefinedDefault = false;

    public EndpointFabric(Server server, MessageSystemContext msContext) throws ServletException {
        this.context = new ServletContextHandler(
                ServletContextHandler.SESSIONS
        );

        this.context.setContextPath("/");
        server.setHandler(this.context);

        // Add javax.websocket support
        container = WebSocketServerContainerInitializer
                .configureContext(this.context);
        messageSystemContext = msContext;
    }


    public <T extends FrontendEndpoint>
    void createEndpoint(String name, Class<T> clazz) throws DeploymentException {
        // Add chat endpoint to server container
        ServerEndpointConfig config = ServerEndpointConfig.Builder.create(
                clazz, "/" + name
        ).build();

        try {
            T endpoint = config.getConfigurator().getEndpointInstance(clazz);
            Address frontAddress = new Address("Frontend service " + name);
            endpoint.setAddress(frontAddress);
            endpoint.setContext(messageSystemContext);
            endpoint.init();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
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
        if (! isDefinedDefault){
            isDefinedDefault = true;
            context.addServlet(defHolder, "/");
        }
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
