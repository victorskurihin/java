package ru.otus.services;

/*
 * Created by VSkurikhin at autumn 2018.
 */

import ru.otus.models.StatisticEntity;
import ru.otus.utils.NullString;

import javax.servlet.http.HttpServletRequest;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static ru.otus.gwt.shared.Constants.*;

public class StatisticCustomTagService implements DataOrigin, StatisticService
{
    private DbService dbService;
    private boolean collectionEnabled;
    private List<StatisticEntity> readyResultList = null;

    public StatisticCustomTagService(DbService dbService, boolean collection)
    {
        this.dbService = dbService;
        this.collectionEnabled = collection;
    }

    private String getNameMarker()
    {
        return Optional.ofNullable(System.getenv(ENVIRONMENT_MARKER_NAME)).orElse(DEFAULT_MARKER_NAME);
    }

    @Override
    public long saveStatisticFromRequestParams(HttpServletRequest request, DbService dbService)
    {

        StatisticEntity result = new StatisticEntity();
        result.setId(-1L);
        result.setNameMarker(getNameMarker());
        result.setJspPageName(request.getParameter(PARAMETER_PAGE_NAME));
        result.setIpAddress(request.getRemoteAddr());
        result.setUserAgent(request.getHeader(HEADER_USER_AGENT));
        result.setClientTime(LocalDateTime.parse(request.getParameter(PARAMETER_CLIENT_TIME)));
        result.setServerTime(LocalDateTime.now());
        result.setSessionId(request.getSession().getId());
        result.setUser(dbService.getUserEntityByName(request.getRemoteUser()));
        result.setPreviousId(NullString.returnLong(request.getParameter(PARAMETER_PREVIOS_ID)));

        return dbService.insertIntoStatistic(result);
    }

    @Override
    public boolean isCollectionEnabled()
    {
        return collectionEnabled;
    }

    @Override
    public List<StatisticEntity> getAllVisitsStatElements() throws SQLException
    {
        return dbService.getAllStatisticElements();
    }

    @Override
    public void setCollectionEnabled(boolean collectionEnabled)
    {
        this.collectionEnabled = collectionEnabled;
    }

    @Override
    public synchronized boolean isReady()
    {
        return readyResultList != null;
    }

    private synchronized void setReadyResultList(List<StatisticEntity> readyResultList)
    {
        this.readyResultList = readyResultList;
    }

    @Override
    public void fetchData() throws SQLException
    {
        setReadyResultList(getAllVisitsStatElements());
    }

    @Override
    public String getDataXML()
    {
        return null;
    }

    @Override
    public String getDataJSON()
    {
        return null;
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
