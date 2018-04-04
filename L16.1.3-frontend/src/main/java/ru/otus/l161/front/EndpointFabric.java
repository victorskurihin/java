package ru.otus.l161.front;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;
import org.eclipse.jetty.websocket.jsr356.server.deploy.WebSocketServerContainerInitializer;

import ru.otus.l161.channel.SocketMsgWorker;

import javax.servlet.ServletException;
import javax.websocket.DeploymentException;
import javax.websocket.server.ServerContainer;
import javax.websocket.server.ServerEndpointConfig;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.regex.Pattern;

public class EndpointFabric {
    private static final String PUBLIC_HTML = "public_html";
    private static final Logger LOG = Log.getLogger(EndpointFabric.class);
    private static final Pattern urlWindowsPattern = Pattern.compile("^file:/+[A-Z]:/");

    private ServletContextHandler context;

    // Add javax.websocket support
    private ServerContainer container;
    private SocketMsgWorker client;
    private boolean isDefinedDefault = false;

    public EndpointFabric(Server server, SocketMsgWorker client) throws ServletException {

        this.context = new ServletContextHandler(
            ServletContextHandler.SESSIONS
        );

        this.context.setContextPath("/");
        server.setHandler(this.context);

        // Add javax.websocket support
        container = WebSocketServerContainerInitializer
                    .configureContext(this.context);
        this.client = client;
    }

    private <T extends FrontEndpoint> void
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

    private String urlBase(URL urlStatics) {
        String url = urlStatics.toExternalForm().replaceFirst("/[^/]*$","/");
        if (urlWindowsPattern.matcher(url).find()) {
            String result = url.replaceFirst("^file:/", "");
            try {
                return Paths.get(result).toRealPath().toString() + "\\";
            } catch (IOException e) {
                LOG.warn(e);
            }
        }
        return url;
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

        ServletHolder defHolder = new ServletHolder("default", new DefaultServlet());
        defHolder.setInitParameter("resourceBase", urlBase(urlStatics));
        defHolder.setInitParameter("dirAllowed","true");

        if (! isDefinedDefault){
            isDefinedDefault = true;
            context.addServlet(defHolder, "/");
        }
    }

    public <T extends FrontEndpoint>
    T createEndpoint(String name, Class<T> clazz) {
        try {
            T endpoint = clazz.newInstance();
            LOG.info("Create endpoint: {}", endpoint);

            endpoint.setClient(client);
            //noinspection RedundantCast
            createEndpointServerConfig(name, clazz, (T) endpoint);
            addDefaultServletEndpoint(name);
            LOG.info("Deploy endpoint: {}", endpoint);

            return endpoint;
        } catch (InstantiationException | IllegalAccessException e) {
            LOG.debug(e);
        } catch (DeploymentException e) {
            LOG.warn(e);
        }
        return null;
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
