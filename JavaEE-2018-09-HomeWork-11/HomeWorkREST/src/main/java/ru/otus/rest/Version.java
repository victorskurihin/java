/*
 * Version.java
 * This file was last modified at 2018.12.03 20:05 by Victor N. Skurikhin.
 * $Id$
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */

package ru.otus.rest;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/version")
@Produces(MediaType.APPLICATION_JSON)
public class Version
{
    public static final String VERSION = "{\"version\": 1}";

    @GET
    public Response onJson() {
        return Response.status(200)
                       .type(MediaType.APPLICATION_JSON_TYPE)
                       .entity(VERSION)
                       .build();
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
