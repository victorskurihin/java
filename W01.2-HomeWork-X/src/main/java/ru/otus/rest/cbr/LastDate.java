/*
 * LastDate.java
 * This file was last modified at 2018.12.03 20:05 by Victor N. Skurikhin.
 * $Id$
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */

package ru.otus.rest.cbr;

import ru.otus.soap.wsclient.cbr.DailyInfo;
import ru.otus.soap.wsclient.cbr.DailyInfoSoap;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.datatype.XMLGregorianCalendar;

@Path("/curs")
@Produces(MediaType.APPLICATION_JSON)
public class LastDate
{
    @GET
    @Path("/latestdatetime")
    public Response lastDate() {
        try {
            DailyInfoSoap service = new DailyInfo().getDailyInfoSoap();
            XMLGregorianCalendar lastCalendar = service.getLatestDateTime();
            String result = lastCalendar.toString();

            return Response.status(200).type(MediaType.APPLICATION_JSON_TYPE).entity(result).build();
        }
        catch (Exception e) {
            e.printStackTrace(); // TODO LOGGER

            throw new WebApplicationException(
                Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).type(MediaType.TEXT_PLAIN).build()
            );
        }
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
