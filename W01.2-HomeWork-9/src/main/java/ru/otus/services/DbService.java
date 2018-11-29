/*
 * DbService.java
 * This file was last modified at 29.11.18 11:10 by Victor N. Skurikhin.
 * $Id$
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */

package ru.otus.services;

import ru.otus.db.dao.DAOController;
import ru.otus.models.*;

import javax.servlet.ServletContext;
import java.io.Closeable;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface DbService extends Closeable
{
    List<StatisticEntity> getAllStatisticElements() throws SQLException;

    List<EmpEntity> getEmpEntities();

    EmpEntity getEmpEntityById(long id);

    UserEntity getUserEntityByName(String name);

    long insertIntoStatistic(StatisticEntity entity);

    void updateFirstNameInEmpEntityById(long id, String firstName);

    void updateSecondNameInEmpEntityById(long id, String secondName);

    void deleteEmpEntityById(long id);

    <T extends DataSet> void deleteEntityById(long id, Class<T> c);

    //// REFACTORING ////

    void clearDb(ServletContext sc) throws Exception;

    void importDb(ServletContext sc) throws Exception;

    void exportDb(ServletContext sc) throws Exception;

    <E extends DataSet> DAOController<E , Long> getController(Class<E> c);

    <E extends DataSet> void saveEntity(E entity);

    <E extends DataSet> List<E> getEntities(Class<E> c);

    <E extends DataSet> E getEntityById(long id, Class<E> c);

    List<EmpEntity> searchEmpEntity(Map<String, Object> attrs);
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
