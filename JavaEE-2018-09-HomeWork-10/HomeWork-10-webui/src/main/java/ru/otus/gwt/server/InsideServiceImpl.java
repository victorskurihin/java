/*
 * InsideServiceImpl.java
 * This file was last modified at 2018.12.03 20:05 by Victor N. Skurikhin.
 * $Id$
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */

package ru.otus.gwt.server;

import ru.otus.db.dao.jpa.EmpController;
import ru.otus.exeptions.ExceptionThrowable;
import ru.otus.soap.wsclient.corptax.CorporateTaxProvider;
import ru.otus.soap.wsclient.corptax.CorporateTaxWebService;
import ru.otus.soap.wsclient.salary.SalaryProvider;
import ru.otus.soap.wsclient.salary.SalaryWebService;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.otus.models.EmpEntity;
import ru.otus.gwt.client.service.InsideService;
import ru.otus.gwt.shared.Emp;
import ru.otus.gwt.shared.Search;
import ru.otus.services.DbService;
import ru.otus.services.SearchCacheService;

import javax.servlet.ServletContext;
import javax.xml.ws.WebServiceRef;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static ru.otus.gwt.shared.Constants.CACHE_SERVICE;
import static ru.otus.gwt.shared.Constants.DB_SERVICE;

public class InsideServiceImpl extends RemoteServiceServlet implements InsideService
{
    @WebServiceRef
    private CorporateTaxWebService corpTaxservice;

    @WebServiceRef
    private SalaryWebService salaryService;

    private static final Logger LOGGER = LogManager.getLogger(InsideServiceImpl.class.getName());

    private Emp convertEmpEntityToEmp(EmpEntity entity)
    {
        return new Emp(
            entity.getId(), entity.getFirstName(), entity.getSecondName(), entity.getSurName(),
            entity.getJob(), entity.getCity(), entity.getAge().toString()
        );
    }

    @Override
    public List<Emp> getEmpsList()
    {
        LOGGER.info("getEmpsList.");
        DbService dbService = (DbService) getServletContext().getAttribute(DB_SERVICE);
        List<EmpEntity> list = dbService.getEmpEntities();

        return list.stream().map(this::convertEmpEntityToEmp).collect(Collectors.toList());
    }

    private EmpEntity convertToEmpEntity(Emp emp)
    {
        EmpEntity entity = new EmpEntity();

        entity.setId(-1L);
        entity.setFirstName(emp.getFirstName());
        entity.setSecondName(emp.getSecondName());
        entity.setSurName(emp.getSurName());
        entity.setJob(emp.getJob());
        entity.setCity(emp.getCity());
        entity.setAge(Long.parseLong(emp.getAge()));

        return entity;
    }

    @Override
    public void addNewEmp(Emp emp)
    {
        DbService dbService = (DbService) getServletContext().getAttribute(DB_SERVICE);
        dbService.saveEntity(convertToEmpEntity(emp));
        LOGGER.info("Added new Emp: {}", emp);
    }

    @Override
    public void setEmpFirstName(long id, String value)
    {
        DbService dbService = (DbService) getServletContext().getAttribute(DB_SERVICE);
        EmpController empController = (EmpController) dbService.getController(EmpEntity.class);
        try {
            empController.updateFirstName(id, value);
        }
        catch (ExceptionThrowable exceptionThrowable) {
            LOGGER.error("ERROR update first_name: {}", exceptionThrowable);
        }
        LOGGER.info("Update Emp with id: {} firstName: {}.", id, value);
    }

    @Override
    public void setEmpSecondName(long id, String value)
    {
        DbService dbService = (DbService) getServletContext().getAttribute(DB_SERVICE);
        EmpController empController = (EmpController) dbService.getController(EmpEntity.class);
        try {
            empController.updateSecondName(id, value);
        }
        catch (ExceptionThrowable exceptionThrowable) {
            LOGGER.error("ERROR update second_name: {}", exceptionThrowable);
        }
        LOGGER.info("Update Emp with id: {} secondName: {}.", id, value);
    }

    @Override
    public void setEmpSurName(long id, String value)
    {
        DbService dbService = (DbService) getServletContext().getAttribute(DB_SERVICE);
        EmpController empController = (EmpController) dbService.getController(EmpEntity.class);
        try {
            empController.updateSurName(id, value);
        }
        catch (ExceptionThrowable exceptionThrowable) {
            LOGGER.error("ERROR update sur_name: {}", exceptionThrowable);
        }
        LOGGER.info("Update Emp with id: {} surName: {}.", id, value);
    }

    @Override
    public void deleteEmp(long id)
    {
        LOGGER.info("EmpEntity deliting by id: {} ...", id);
        DbService dbService = (DbService) getServletContext().getAttribute(DB_SERVICE);
        try {
            dbService.deleteEntityById(id, EmpEntity.class);
        }
        catch (ExceptionThrowable exceptionThrowable) {
            LOGGER.error("ERROR delete: {}", exceptionThrowable);
        }
        LOGGER.info("EmpEntity by id: {} deleted.");
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

        if (!attrs.isEmpty()) {
            DbService dbService = (DbService) getServletContext().getAttribute(DB_SERVICE);
            List<EmpEntity> empEntities = dbService.searchEmpEntity(attrs);
            return empEntities.stream().map(this::convertEmpEntityToEmp).collect(Collectors.toList());
        }

        return getEmpsList();
    }

    @Override
    public List<Emp> searchEmp(Search search)
    {
        ServletContext sc = getServletContext();
        SearchCacheService cacheService = (SearchCacheService) sc.getAttribute(CACHE_SERVICE);

        List<Emp> result = cacheService.searchInCache(search);
        if (result != null) return result;

        LOGGER.info("Direct request to the database with hash: {}", search.hashCode());

        result = searchEmpInDb(search);
        cacheService.putToCache(search, result);

        return result;
    }

    @Override
    public double getTax(double income, double costs, double taxRate)
    {
        CorporateTaxProvider port = corpTaxservice.getCorporateTaxProviderPort();

        return port.getCurrentTax(income, costs, taxRate);
    }

    @Override
    public long getMaxSalary()
    {
        SalaryProvider port = salaryService.getSalaryProviderPort();

        return port.getMaxSalary();
    }

    @Override
    public double getAvgSalary()
    {
        SalaryProvider port = salaryService.getSalaryProviderPort();

        return port.getAvgSalary();
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
