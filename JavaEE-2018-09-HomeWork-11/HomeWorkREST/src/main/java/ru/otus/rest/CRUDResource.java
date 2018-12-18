/*
 * Version.java
 * This file was last modified at 2018.12.03 20:05 by Victor N. Skurikhin.
 * $Id$
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */

package ru.otus.rest;

import ru.otus.db.dao.DAOController;
import ru.otus.exceptions.ExceptionThrowable;
import ru.otus.models.DataSet;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.List;

import static ru.otus.exceptions.ExceptionsFabric.getWebApplicationException;
import static ru.otus.utils.UniformResource.getRequestURL;

public abstract class CRUDResource<E extends DataSet, C extends DAOController<E, Long>>
{
    public abstract C getDAO();

    public abstract HttpServletRequest getHttpServletRequest();

    private URI getLocation(E entity)
    {
        return URI.create(getRequestURL(getHttpServletRequest()) + '/' + entity.getId());
    }

    public Response create(E entity)
    {
        try {
            if (getDAO().create(entity)) {
                return Response.created(getLocation(entity)).build();
            }
            throw getWebApplicationException(new Throwable("Error create!"));
        }
        catch (ExceptionThrowable exceptionThrowable) {
            throw getWebApplicationException(exceptionThrowable);
        }
    }

    public Response readAll()
    {
        try {
            List<E> result = getDAO().getAll();

            return Response.ok(result).build();
        } catch (ExceptionThrowable exceptionThrowable) {
            throw getWebApplicationException(exceptionThrowable);
        }
    }

    public Response read(Integer id)
    {
        try {
            E entity = getDAO().getEntityById(id.longValue());

            if (null != entity) {
                return Response.ok(entity).build();
            }
            throw new ExceptionThrowable(new Throwable("Not Found!"));
        }
        catch (ExceptionThrowable exceptionThrowable) {
            throw getWebApplicationException(exceptionThrowable);
        }
    }

    public Response update(E entity)
    {
        try {
            entity = getDAO().update(entity);

            if (null != entity) {
                return Response.ok(getLocation(entity)).build();
            }
            throw getWebApplicationException(new Throwable("Error update!"));
        }
        catch (ExceptionThrowable exceptionThrowable) {
            throw getWebApplicationException(exceptionThrowable);
        }
    }

    public Response delete(Integer id)
    {
        try {
            if (getDAO().delete(id.longValue())) {
                return Response.status(Response.Status.NO_CONTENT).build();
            }
            throw getWebApplicationException(new Throwable("Error delete!"));
        }
        catch (ExceptionThrowable exceptionThrowable) {
            throw getWebApplicationException(exceptionThrowable);
        }
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
