/*
 * SalaryWebService.java
 * This file was last modified at 29.11.18 11:18 by Victor N. Skurikhin.
 * $Id$
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */

package ru.otus.soap.wservice.salary;

import ru.otus.exeptions.ExceptionThrowable;
import ru.otus.services.DbSQLService;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

@WebService(serviceName = "SalaryWebService", name = "SalaryProvider")
public class SalaryWebService implements SalaryProvider
{
    public static final String PERSISTENCE_UNIT_NAME = "jpa";
    // private static final EntityManagerFactory emf =
    // Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME); // for Tomcat
    @PersistenceUnit(unitName = PERSISTENCE_UNIT_NAME)
    private EntityManagerFactory emf; // for Glassfish

    @Override
    @WebMethod
    public long getMaxSalary() throws ExceptionThrowable
    {
        DbSQLService dbService = new DbSQLService(emf.createEntityManager());

        return dbService.getEmpController().getMaxSalary();
    }

    @Override
    @WebMethod
    public Double getAvgSalary() throws ExceptionThrowable
    {
        DbSQLService dbService = new DbSQLService(emf.createEntityManager());

        return dbService.getEmpController().getAvgSalary();
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
