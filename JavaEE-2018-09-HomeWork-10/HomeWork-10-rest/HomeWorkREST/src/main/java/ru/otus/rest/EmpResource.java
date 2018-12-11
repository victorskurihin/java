/*
 * Version.java
 * This file was last modified at 2018.12.03 20:05 by Victor N. Skurikhin.
 * $Id$
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */

package ru.otus.rest;

import ru.otus.db.dao.jpa.EmpController;
import ru.otus.exceptions.ExceptionThrowable;
import ru.otus.models.EmpEntity;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static ru.otus.exceptions.ExceptionsFabric.getWebApplicationException;

@Stateless
@Path("/registry")
@Produces(MediaType.APPLICATION_JSON)
public class EmpResource
{
    @EJB
    EmpController controller;

    @GET
    public Response readAll(@PathParam("id") Integer id)
    {
        try {
            return Response.ok(controller.getAll()).build();
        } catch (ExceptionThrowable exceptionThrowable) {
            throw getWebApplicationException(exceptionThrowable);
        }
    }

    @GET
    @Path("/{id}")
    public Response read(@PathParam("id") Integer id)
    {
        try {
            EmpEntity entity = controller.getEntityById(id.longValue());

            if (null != entity) {
                return Response.ok(entity).build();
            }
            throw new ExceptionThrowable(new Throwable("Not Found!"));
        }
        catch (ExceptionThrowable exceptionThrowable) {
            throw getWebApplicationException(exceptionThrowable);
        }
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
