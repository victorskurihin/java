package ru.otus.services;

/*
 * Created by VSkurikhin at autumn 2018.
 */

import ru.otus.models.*;

import javax.servlet.ServletContext;
import java.io.Closeable;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface DbService extends Closeable
{
    void clearDb(ServletContext sc) throws Exception;

    void importDb(ServletContext sc) throws Exception;

    void exportDb(ServletContext sc) throws Exception;

    List<StatisticEntity> getAllStatisticElements() throws SQLException;

    List<EmpEntity> getEmpEntities();

    <T extends DataSet> List<T> getEntities(Class<T> c);

    EmpEntity getEmpEntityById(long id);

    UserEntity getUserEntityByName(String name);

    <T extends DataSet> T getEntityById(long id, Class<T> c);

    <T extends DataSet> void saveEntity(T entity);

    long insertIntoStatistic(StatisticEntity entity);

    void updateFirstNameInEmpEntityById(long id, String firstName);

    void updateSecondNameInEmpEntityById(long id, String secondName);

    void updateSurNameInEmpEntityById(long id, String surName);

    void deleteEmpEntityById(long id);

    <T extends DataSet> void deleteEntityById(long id, Class<T> c);

    List<EmpEntity> searchEmpEntity(Map<String, Object> attrs);
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
