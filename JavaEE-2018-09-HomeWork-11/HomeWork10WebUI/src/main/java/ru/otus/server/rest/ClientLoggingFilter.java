package ru.otus.server.rest;

import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;
import javax.ws.rs.client.ClientResponseContext;
import javax.ws.rs.client.ClientResponseFilter;
import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ClientLoggingFilter implements ClientRequestFilter, ClientResponseFilter
{
    @Override
    public void filter(ClientRequestContext clientRequestCtx) throws IOException
    {
        String method = clientRequestCtx.getMethod();
        URI uri = clientRequestCtx.getUri();

        for (Map.Entry<String, List<Object>> e : clientRequestCtx.getHeaders().entrySet()) {
            String key = e.getKey();
            List<Object> value = e.getValue();
            System.out.println(
                "Header name " + key + " with value : " +
                    value.stream()
                         .map(Object::toString)
                         .collect(Collectors.joining(", "))
            );
        }
    }

    @Override
    public void filter(ClientRequestContext clRequestCtx, ClientResponseContext clResponseCtx)
    throws IOException
    {
        for (Map.Entry<String, List<Object>> e : clRequestCtx.getHeaders().entrySet()) {
            String key = e.getKey();
            List<Object> value = e.getValue();
            System.out.println(
                "Header name " + key + " with value : " +
                    value.stream()
                         .map(Object::toString)
                         .collect(Collectors.joining(", "))
            );
        }
    }
}
