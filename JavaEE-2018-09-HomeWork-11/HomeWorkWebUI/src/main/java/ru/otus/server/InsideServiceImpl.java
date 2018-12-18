/*
 * InsideServiceImpl.java
 * This file was last modified at 2018.12.03 20:05 by Victor N. Skurikhin.
 * $Id$
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */

package ru.otus.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.glassfish.jersey.client.ClientConfig;
import ru.otus.client.service.InsideService;
import ru.otus.server.rest.ClientLoggingFilter;
import ru.otus.shared.Emp;
import ru.otus.shared.Search;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InsideServiceImpl extends RemoteServiceServlet implements InsideService
{

    private static final Logger LOGGER = LogManager.getLogger(InsideServiceImpl.class.getName());

    @Override
    public List<Emp> getEmpsList()
    {
        return null;  // TODO RESTful Refactoring
    }


    @Override
    public void addNewEmp(Emp emp)
    {
        LOGGER.info("Added new Emp: {}", emp);
         // TODO RESTful Refactoring
    }

    @Override
    public void setEmpFirstName(long id, String value)
    {
        LOGGER.info("Update Emp with id: {} firstName: {}.", id, value);
         // TODO RESTful Refactoring
    }

    @Override
    public void setEmpSecondName(long id, String value)
    {
        LOGGER.info("Update Emp with id: {} secondName: {}.", id, value);
         // TODO RESTful Refactoring
    }

    @Override
    public void setEmpSurName(long id, String value)
    {
        LOGGER.info("Update Emp with id: {} surName: {}.", id, value);
    }

    @Override
    public void deleteEmp(long id)
    {
        LOGGER.info("EmpEntity deliting by id: {} ...", id);
         // TODO RESTful Refactoring
    }

    private List<Emp> searchEmpInDb(Search search)
    {
        Map<String, Object> attrs = new HashMap<>();

        if (null != search.getFio())
            attrs.put("name", "%" + search.getFio() + "%");

        if (null != search.getJob())
            attrs.put("job", "%" + search.getJob() + "%");

        if (null != search.getCity())
            attrs.put("city", "%" + search.getCity() + "%");

        if (null != search.getAge()) {
            LOGGER.info("age: {}", search.getAge());
            LOGGER.info("parse age: {}", Long.parseLong(search.getAge()));
            attrs.put("age", Long.parseLong(search.getAge()));
        }
        return null; // TODO RESTful Refactoring
    }

    @Override
    public List<Emp> searchEmp(Search search)
    {
        return null; // TODO RESTful Refactoring
    }

    private static URI getBaseURI()
    {
        return UriBuilder.fromUri("http://localhost:8080/homework11-rest").build();
    }

    public ArrayList<Double> pay(String version, String verb, int t, int kr, int st)
    {
        ClientConfig config = new ClientConfig();
        Client client = ClientBuilder.newClient(config);
        client.register(ClientLoggingFilter.class);

        WebTarget target = client.target(getBaseURI())
                                 .path("api").path(version).path(verb)
                                 .path("{t}").path("{kr}").path("{st}")
                                 .resolveTemplate("t", t)
                                 .resolveTemplate("kr", kr)
                                 .resolveTemplate("st", st);
        final Invocation.Builder invocationBuilder = target.request().accept(MediaType.APPLICATION_JSON);

        //noinspection unchecked
        ArrayList<Double> result =  invocationBuilder.get(ArrayList.class);

        return result;
    }

    @Override
    public ArrayList<Double> diffPay(int t, int kr, int st)
    {
        return pay("v1", "diff", t, kr, st);
    }

    @Override
    public ArrayList<Double> annuPay(int t, int kr, int st)
    {
        return pay("v2", "annu", t, kr, st);
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
