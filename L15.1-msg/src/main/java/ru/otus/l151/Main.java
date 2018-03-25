package ru.otus.l151;

import org.eclipse.jetty.server.Server;
import ru.otus.l151.app.*;
import ru.otus.l151.db.DBServiceImpl;
import ru.otus.l151.messageSystem.Address;
import ru.otus.l151.messageSystem.MessageSystem;

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

    public static void main(String[] args) throws Exception {

        Server server = new Server(PORT);
        MessageSystem messageSystem = new MessageSystem();
        MessageSystemContext context = new MessageSystemContext(messageSystem);
        Address dbAddress = new Address("DB");

        context.setDbAddress(dbAddress);

        EndpointFabric fabric = new EndpointFabric(server, context);
        DBService dbService = new DBServiceImpl(context, dbAddress);

        dbService.init();

        fabric.createEndpoint("auth", AuthEndpoint.class);
        fabric.createEndpoint("chat", ChatEndpoint.class);

        try {
            messageSystem.start();
            server.start();
            server.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
