/*
 * Version.java
 * This file was last modified at 2018.12.03 20:05 by Victor N. Skurikhin.
 * $Id$
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */

package ru.otus.rest;

import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

import static ru.otus.exceptions.ExceptionsFabric.getWebApplicationException;
import static ru.otus.utils.Payments.annuityPayment;

@Stateless
@Path("/v2/annu")
@Produces(MediaType.APPLICATION_JSON)
public class AnnuityPayResource
{
    @GET
    @Path("/{t}/{kr}/{st}")
    public Response read(@PathParam("t") Integer t, @PathParam("kr") Integer kr, @PathParam("st") Integer st)
    {
        if (t < 1) {
            throw getWebApplicationException(new IllegalArgumentException(), Response.Status.NOT_ACCEPTABLE);
        }
        double dst = st*0.01/12.0;
        List<Double> result = new ArrayList<>();

        for (int i = 1; i <= t; ++i)
            result.add(annuityPayment(t.doubleValue(), kr, dst, i));

        return Response.ok(result).build();
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
