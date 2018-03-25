package ru.otus.l151.app;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.websocket.jsr356.server.deploy.WebSocketServerContainerInitializer;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;
import ru.otus.l151.messageSystem.Address;

import javax.servlet.ServletException;
import javax.websocket.DeploymentException;
import javax.websocket.server.ServerContainer;
import javax.websocket.server.ServerEndpointConfig;
import java.net.URL;
import java.util.Objects;

public class EndpointFabric {
    private static final String PUBLIC_HTML = "public_html";
    private static final Logger LOG = Log.getLogger(EndpointFabric.class);

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

    private <T extends FrontendEndpoint>
    void initEndpoint(String name, T endpoint) {
        Address frontAddress = new Address("Frontend service " + name);
        endpoint.setAddress(frontAddress);
        endpoint.setContext(messageSystemContext);
        endpoint.init();
        LOG.info(
            "Init endpoint: {}, with address: {} at {} and context: {}",
            endpoint, frontAddress.getId(), frontAddress, messageSystemContext
        );
    }

    private <T extends FrontendEndpoint> void
    createEndpointServerConfig(String name, Class<T> clazz, T endpoint)
        throws DeploymentException {

        // Add chat endpoint to server container
        ServerEndpointConfig config = ServerEndpointConfig.Builder.create(
            clazz, "/" + name
        ).configurator(
            new ServerEndpointConfig.Configurator() {
                @Override
                public <T> T getEndpointInstance(Class<T> endpointClass)
                        throws InstantiationException {
                    //noinspection unchecked
                    return (T) endpoint;
                }
            }
        ).build();
        container.addEndpoint(config);
    }

    private void addDefaultServletEndpoint(String name) {
        // Add default servlet (to serve the html/css/js)
        // Figure out where the static files are stored.
        URL urlStatics = Thread.currentThread()
            .getContextClassLoader()
            .getResource(PUBLIC_HTML + "/" + name + ".html");

        Objects.requireNonNull(
            urlStatics,"Unable to find " + name + ".html in classpath"
        );
        String urlBase = urlStatics.toExternalForm().replaceFirst("/[^/]*$","/");
        ServletHolder defHolder = new ServletHolder("default", new DefaultServlet());
        defHolder.setInitParameter("resourceBase",urlBase);
        defHolder.setInitParameter("dirAllowed","true");
        if (! isDefinedDefault){
            isDefinedDefault = true;
            context.addServlet(defHolder, "/");
        }
    }

    public <T extends FrontendEndpoint>
    void createEndpoint(String name, Class<T> clazz) {
        try {
            T endpoint = clazz.newInstance();
            LOG.info("Create endpoint: {}", endpoint);
            initEndpoint(name, endpoint);
            //noinspection RedundantCast
            createEndpointServerConfig(name, clazz, (T) endpoint);
            addDefaultServletEndpoint(name);
            LOG.info("Deploy endpoint: {}", endpoint);
        } catch (InstantiationException | IllegalAccessException e) {
            LOG.debug(e);
        } catch (DeploymentException e) {
            LOG.warn(e);
        }
    }

}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
