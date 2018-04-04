package ru.otus.l161;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;

import ru.otus.l161.channel.FrontendWorker;
import ru.otus.l161.front.*;

public class FrontendMain {

    private static final Logger LOG = Log.getLogger(FrontendMain.class);
    private static final String HOST = "localhost";

    private static final int MESSAGES_PORT = 5050;
    private static final int HTTP_PORT = 8090;

    public static void main(String[] args) {
        String host = (args.length > 0) ? args[0] : HOST;
        int msgSrvPort = (args.length > 1) ? Integer.parseInt(args[1]) : MESSAGES_PORT;
        int httpPort = (args.length > 2) ? Integer.parseInt(args[2]) : HTTP_PORT;

        Server server = new Server(httpPort);
        try (FrontendWorker client = new FrontendWorker(host, msgSrvPort)) {
            LOG.info("FrontendWorker address:{}", client.getAddress());
            EndpointFabric fabric = new EndpointFabric(server, client);
            FrontEndpoint auth = fabric.createEndpoint("auth", AuthEndpoint.class);
            FrontEndpoint chat = fabric.createEndpoint("chat", ChatEndpoint.class);
            auth.setChatEndpoint(chat);
            client.init(auth, chat);
            server.start();
            client.loop();
            server.join();
        } catch (Exception e) {
            LOG.warn(e);
        }
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
