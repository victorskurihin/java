/*
 * Version.java
 * This file was last modified at 2018.12.03 20:05 by Victor N. Skurikhin.
 * $Id$
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */

package ru.otus.rest;

import ru.otus.db.dao.jpa.EmpController;
import ru.otus.models.EmpEntity;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Stateless
@Path("/v1/registry")
@Produces(MediaType.APPLICATION_JSON)
public class EmpResource extends CRUDResource<EmpEntity, EmpController>
{
    @Context
    private HttpServletRequest servletRequest;

    @EJB
    EmpController controller;

    @Override
    public EmpController getDAO()
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
    public Response create(EmpEntity entity)
    {
        return super.create(entity);
    }

    @PUT
    public Response update(EmpEntity entity)
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
