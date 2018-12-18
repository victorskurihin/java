package ru.otus.db.dao;

import ru.otus.exceptions.ExceptionThrowable;
import ru.otus.models.StatisticEntity;

import javax.ejb.LocalBean;

@LocalBean
public interface StatisticDAO extends DAOController<StatisticEntity, Long>
{
    long insertProcedure(StatisticEntity entity) throws ExceptionThrowable;
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
