/*
 * Version.java
 * This file was last modified at 2018.12.03 20:05 by Victor N. Skurikhin.
 * $Id$
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */

package ru.otus.rest;

import ru.otus.db.dao.jpa.StatisticController;
import ru.otus.exceptions.ExceptionThrowable;
import ru.otus.models.StatisticEntity;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static ru.otus.exceptions.ExceptionsFabric.getWebApplicationException;

@Stateless
@Path("/v1/statistic")
@Produces(MediaType.APPLICATION_JSON)
public class StatisticResource extends CRUDResource<StatisticEntity, StatisticController>
{
    @Context
    private HttpServletRequest servletRequest;

    @EJB
    StatisticController controller;

    @Override
    public StatisticController getDAO()
    {
        return controller;
    }

    @Override
    public HttpServletRequest getHttpServletRequest()
    {
        return servletRequest;
    }

    @GET
    public Response readAll()
    {
        return super.readAll();
    }

    @GET
    @Path("/{id}")
    public Response read(@PathParam("id") Integer id)
    {
        return super.read(id);
    }

    @POST
    public Response create(StatisticEntity entity)
    {
        try {
            Long id = getDAO().insertProcedure(entity);
            Response.Status status = id > -1 ? Response.Status.CREATED : Response.Status.NOT_MODIFIED;
            return Response.status(status)
                           .entity(id)
                           .type(MediaType.TEXT_PLAIN)
                           .build();
        }
        catch (ExceptionThrowable exceptionThrowable) {
            throw getWebApplicationException(exceptionThrowable);
        }
    }

    @PUT
    public Response update(StatisticEntity entity)
    {
        return super.update(entity);
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Integer id)
    {
        return super.delete(id);
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
