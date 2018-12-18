package ru.otus.server.services;

import org.glassfish.jersey.client.ClientConfig;
import org.junit.BeforeClass;
import org.junit.Test;
import ru.otus.server.rest.ClientLoggingFilter;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.util.List;

public class RestClientTest
{
    static Client client;

    @BeforeClass
    public static void init() {
        ClientConfig config = new ClientConfig();
        client = ClientBuilder.newClient(config);
        client.register(ClientLoggingFilter.class);
    }

    private static URI getBaseURI() {
        return UriBuilder.fromUri("http://localhost:8080/homework11-rest").build();
    }

    @Test
    public void test9()
    {
//        WebTarget target = client.target(getBaseURI())
//                                 .path("api").path("v1").path("diff")
//                                 .path("{t}").path("{kr}").path("{st}")
//                                 .resolveTemplate("t", 6)
//                                 .resolveTemplate("kr", 10000)
//                                 .resolveTemplate("st", 15);
//        final Invocation.Builder invocationBuilder = target.request().accept(MediaType.APPLICATION_JSON);
//        List<Double> response = invocationBuilder.get(List.class);
//        System.out.println("response = " + response);
    }
}
