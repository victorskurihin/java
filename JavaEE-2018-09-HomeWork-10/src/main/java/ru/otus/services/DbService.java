/*
 * DbService.java
 * This file was last modified at 2018.12.03 20:05 by Victor N. Skurikhin.
 * $Id$
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */

package ru.otus.services;

import ru.otus.db.dao.DAOController;
import ru.otus.db.dao.jpa.AbstractController;
import ru.otus.exeptions.ExceptionThrowable;
import ru.otus.models.*;

import javax.servlet.ServletContext;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface DbService
{
    List<StatisticEntity> getAllStatisticElements() throws SQLException;

    List<EmpEntity> getEmpEntities();

    // EmpEntity getEmpEntityById(long id);

    UserEntity getUserEntityByName(String name);

    // long insertIntoStatistic(StatisticEntity entity);

    // void updateFirstNameInEmpEntityById(long id, String firstName);

    // void updateSecondNameInEmpEntityById(long id, String secondName);

    // void deleteEmpEntityById(long id);

    //// REFACTORING ////

    void clearDb(ServletContext sc) throws ExceptionThrowable;

    void importDb(ServletContext sc) throws ExceptionThrowable;

    void exportDb(ServletContext sc) throws Exception;

    <E extends DataSet> AbstractController<E, Long> getController(Class<E> c);

    <E extends DataSet> void saveEntity(E entity);

    <E extends DataSet> E getEntityById(long id, Class<E> c) throws ExceptionThrowable;

    <E extends DataSet> List<E> getEntities(Class<E> c) throws ExceptionThrowable;

    <E extends DataSet> boolean deleteEntityById(long id, Class<E> c) throws ExceptionThrowable;

    List<EmpEntity> searchEmpEntity(Map<String, Object> attrs);
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
