/*
 * Version.java
 * This file was last modified at 2018.12.03 20:05 by Victor N. Skurikhin.
 * $Id$
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */

package ru.otus.rest;

import ru.otus.db.dao.jpa.UserController;
import ru.otus.exceptions.ExceptionThrowable;
import ru.otus.models.UserEntity;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;

import static ru.otus.exceptions.ExceptionsFabric.getWebApplicationException;
import static ru.otus.utils.UniformResource.getRequestURL;

@Stateless
@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
public class UserResource
{
    @Context
    private HttpServletRequest servletRequest;

    @EJB
    UserController controller;

    @GET
    public Response readAll()
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
            UserEntity entity = controller.getEntityById(id.longValue());

            if (null != entity) {
                return Response.ok(entity).build();
            }
            throw new ExceptionThrowable(new Throwable("Not Found!"));
        }
        catch (ExceptionThrowable exceptionThrowable) {
            throw getWebApplicationException(exceptionThrowable);
        }
    }

    @POST
    public Response create(UserEntity entity)
    {
        try {
            if (controller.create(entity)) {
                return Response.created(URI.create(getRequestURL(servletRequest) + '/' + entity.getId())).build();
            }
            throw getWebApplicationException(new Throwable("Error create!"), Response.Status.INTERNAL_SERVER_ERROR);
        }
        catch (ExceptionThrowable exceptionThrowable) {
            throw getWebApplicationException(exceptionThrowable);
        }
    }

    @PUT
    public Response update(UserEntity entity)
    {
        try {
            entity = controller.update(entity);
            if (null != entity) {
                return Response.ok(URI.create(getRequestURL(servletRequest) + '/' + entity.getId())).build();
            }
            throw getWebApplicationException(new Throwable("Error update!"), Response.Status.INTERNAL_SERVER_ERROR);
        }
        catch (ExceptionThrowable exceptionThrowable) {
            throw getWebApplicationException(exceptionThrowable);
        }
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Integer id)
    {
        try {
            if (controller.delete(id.longValue())) {
                return Response.status(Response.Status.NO_CONTENT).build();
            }
            throw getWebApplicationException(new Throwable("Error delete!"), Response.Status.INTERNAL_SERVER_ERROR);
        }
        catch (ExceptionThrowable exceptionThrowable) {
            throw getWebApplicationException(exceptionThrowable);
        }
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
