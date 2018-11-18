package ru.otus.services;

/*
 * Created by VSkurikhin at autumn 2018.
 */

import ru.otus.models.StatisticEntity;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.List;

public interface StatisticService
{
    long saveStatisticFromRequestParams(HttpServletRequest request, DbService dbService);

    boolean isCollectionEnabled();

    List<StatisticEntity> getAllVisitsStatElements(DbService dbService) throws SQLException;

    void setCollectionEnabled(boolean collectionEnabled);
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
