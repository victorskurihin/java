package ru.otus.exceptions;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class ExceptionsFabric
{
    public static WebApplicationException getWebApplicationException(Throwable exception, Response.Status status)
    {
        return new WebApplicationException(
            Response.status(status)
                    .entity(exception.getMessage())
                    .type(MediaType.TEXT_PLAIN)
                    .build()
        );
    }

    public static WebApplicationException getWebApplicationException(Throwable exception)
    {
        return getWebApplicationException(exception, Response.Status.NOT_ACCEPTABLE);
    }

}
