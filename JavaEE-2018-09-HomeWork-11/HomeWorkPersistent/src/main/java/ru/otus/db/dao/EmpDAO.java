package ru.otus.db.dao;

import ru.otus.exceptions.ExceptionThrowable;
import ru.otus.models.EmpEntity;

import javax.ejb.Remote;

@Remote
public interface EmpDAO extends DAOController<EmpEntity, Long>
{
    void updateFirstName(Long id, String firstName) throws ExceptionThrowable;

    void updateSecondName(Long id, String secondName) throws ExceptionThrowable;

    void updateSurName(Long id, String surName) throws ExceptionThrowable;

    long getMaxSalary() throws ExceptionThrowable;

    double getAvgSalary() throws ExceptionThrowable;
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
