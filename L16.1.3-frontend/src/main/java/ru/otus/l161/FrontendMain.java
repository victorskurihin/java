package ru.otus.l161;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;

import ru.otus.l161.channel.FrontendWorker;
import ru.otus.l161.front.AuthEndpoint;
import ru.otus.l161.front.ChatEndpoint;
import ru.otus.l161.front.EndpointFabric;

/**
 * Created by tully.
 */
public class FrontendMain {

    private static final Logger LOG = Log.getLogger(FrontendMain.class);
    private static final String HOST = "localhost";

    private static final int MESSAGES_PORT = 5050;
    private static final int HTTP_PORT = 8090;

    public static void main(String[] args) throws Exception {
        Server server = new Server(HTTP_PORT);
        try (FrontendWorker client = new FrontendWorker(HOST, MESSAGES_PORT)) {
            EndpointFabric fabric = new EndpointFabric(server, client);
            client.init(
                fabric.createEndpoint("auth", AuthEndpoint.class),
                fabric.createEndpoint("chat", ChatEndpoint.class)
            );
            server.start();
            client.loop();
            server.join();
        } catch (Exception e) {
            LOG.warn(e);
        } finally {
            LOG.warn("finally!!!");
        }
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
