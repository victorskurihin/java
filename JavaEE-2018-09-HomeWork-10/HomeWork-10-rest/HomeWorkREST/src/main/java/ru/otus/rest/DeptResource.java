/*
 * Version.java
 * This file was last modified at 2018.12.03 20:05 by Victor N. Skurikhin.
 * $Id$
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */

package ru.otus.rest;

import ru.otus.db.dao.jpa.DeptController;
import ru.otus.exceptions.ExceptionThrowable;
import ru.otus.models.DeptEntity;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Stateless
@Path("/dept")
@Produces(MediaType.APPLICATION_JSON)
public class DeptResource
{
    @EJB
    DeptController deptController;

    @GET
    @Path("/{id}")
    public Response read(@PathParam("id") Integer id){
        try {
            DeptEntity entity = deptController.getEntityById(id.longValue());
            if (null != entity) {
                return Response.ok(entity).build();
            }
            throw new WebApplicationException(
                Response.status(Response.Status.BAD_REQUEST)
                        .entity("Not Found!")
                        .type(MediaType.TEXT_PLAIN)
                        .build()
            );
        }
        catch (ExceptionThrowable exceptionThrowable) {
            throw new WebApplicationException(
                Response.status(Response.Status.BAD_REQUEST)
                        .entity(exceptionThrowable.getMessage())
                        .type(MediaType.TEXT_PLAIN)
                        .build()
            );
        }
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
