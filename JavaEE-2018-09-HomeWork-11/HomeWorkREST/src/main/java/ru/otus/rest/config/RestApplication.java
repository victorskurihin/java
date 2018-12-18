/*
 * RestApplication.java
 * This file was last modified at 2018.12.03 20:05 by Victor N. Skurikhin.
 * $Id$
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */

package ru.otus.rest.config;

import javax.ws.rs.ApplicationPath;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.glassfish.jersey.jackson.internal.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import org.glassfish.jersey.server.ResourceConfig;
import ru.otus.GroupResource;
import ru.otus.rest.*;

import java.nio.charset.StandardCharsets;

import static ru.otus.rest.config.RestApplication.API_URL;

@ApplicationPath(API_URL)
public class RestApplication extends ResourceConfig
{
    public static final String API_URL = "/api";

    private static final String ENCODING_PROPERTY = "encoding";

    private static final String DEFAULT_SCHEME_NAME = "http";

    public RestApplication(){
        registerResourceClasses();

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        JacksonJaxbJsonProvider jacksonProvider = new JacksonJaxbJsonProvider();
        jacksonProvider.setMapper(objectMapper);
        register(jacksonProvider);

        property(ENCODING_PROPERTY, StandardCharsets.UTF_8.toString());
    }

    private void registerResourceClasses() {
        register(Version.class);
        register(DeptResource.class);
        register(EmpResource.class);
        register(UserResource.class);
        register(GroupResource.class);
        register(StatisticResource.class);
        register(DiffPayResource.class);
        register(AnnuityPayResource.class);
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
