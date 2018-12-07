/*
 * CursOnDate.java
 * This file was last modified at 2018.12.03 20:05 by Victor N. Skurikhin.
 * $Id$
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */

package ru.otus.rest.cbr;

import ru.otus.adapters.soap.GetCursOnDateXMLAdapter;
import ru.otus.models.cbr.Currency;
import ru.otus.utils.CalDateTime;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.JsonbConfig;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.XMLGregorianCalendar;
import java.text.ParseException;
import java.util.List;

@Path("/curs")
@Produces(MediaType.APPLICATION_JSON)
public class CursOnDate
{
    @GET
    @Path("/ondate/{value1}")
    public Response onDate(@PathParam("value1") String value1) {
        try {
            XMLGregorianCalendar date = CalDateTime.parseStringToXMLGregorian(value1);

            List<Currency> list = new GetCursOnDateXMLAdapter(date).getList();

            JsonbConfig config = new JsonbConfig().withFormatting(true);
            Jsonb jsonb = JsonbBuilder.create(config);
            String result = jsonb.toJson(list);

            return Response.status(200).type(MediaType.APPLICATION_JSON_TYPE).entity(result).build();
        }
        catch (ParseException | DatatypeConfigurationException e) {
            e.printStackTrace(); // TODO LOGGER

            throw new WebApplicationException(
                Response.status(Response.Status.BAD_REQUEST)
                        .entity(e.getMessage())
                        .type(MediaType.TEXT_PLAIN)
                        .build()
            );
        }
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
