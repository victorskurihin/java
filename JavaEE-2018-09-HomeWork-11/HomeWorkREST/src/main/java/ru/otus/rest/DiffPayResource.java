/*
 * Version.java
 * This file was last modified at 2018.12.03 20:05 by Victor N. Skurikhin.
 * $Id$
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */

package ru.otus.rest;

import io.swagger.annotations.*;

import javax.ejb.Stateless;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

import static ru.otus.exceptions.ExceptionsFabric.getWebApplicationException;
import static ru.otus.utils.Payments.differentiatedPayment;

@Stateless
@Path("/v1/diff")
@Produces(MediaType.APPLICATION_JSON)
@SwaggerDefinition(
    info = @Info(
        title = "Swagger-generated RESTful API",
        description = "RESTful Description example",
        version = "1.0.0",
        termsOfService = "share and care",
        contact = @Contact(
            name = "Victor", email = "vskurikhin@gmail.com",
            url = "https://otus.ru"),
        license = @License(
            name = "Apache 2.0",
            url = "http://www.apache.org")),
    tags = {
        @Tag(name = "User Resource", description = "RESTful API to interact with Differentiated payment resource.")
    },
    host = "localhost:8080",
    basePath = "/homework11/api/v1",
    schemes = {SwaggerDefinition.Scheme.HTTP},
    externalDocs = @ExternalDocs(
        value = "Developing a Swagger-enabled REST API using WebSphere Developer Tools",
        url = "https://tinyurl.com/swagger-wlp")
)
@Api(tags = "User Resource")
public class DiffPayResource
{
    private double calcF(double t, int kr, double st, int i)
    {
        return kr/t + kr*(t - i + 1)*st/t;
    }

    @GET
    @Path("/{t}/{kr}/{st}")
    @ApiOperation("Differentiated payment")
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK")
    })
    @ApiImplicitParams({
        @ApiImplicitParam(name = "t", value = "Number of payment periods", dataType = "int", paramType = "path"),
        @ApiImplicitParam(name = "kr", value = "Credit amount", dataType = "int", paramType = "path"),
        @ApiImplicitParam(name = "st", value = "Interest rate", dataType = "int", paramType = "path"),
    })
    public Response read(@PathParam("t") Integer t, @PathParam("kr") Integer kr, @PathParam("st") Integer st)
    {
        if (t < 1) {
            throw getWebApplicationException(new IllegalArgumentException(), Response.Status.NOT_ACCEPTABLE);
        }
        double dst = st*0.01/12.0;
        List<Double> result = new ArrayList<>();

        for (int i = 1; i <= t; ++i) {
            result.add(differentiatedPayment(t.doubleValue(), kr, dst, i));
        }

        return Response.ok(result).build();
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
