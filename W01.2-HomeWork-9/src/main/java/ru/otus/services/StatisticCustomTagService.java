package ru.otus.services;

/*
 * Created by VSkurikhin at autumn 2018.
 */

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.otus.models.StatisticEntitiesList;
import ru.otus.models.StatisticEntity;
import ru.otus.utils.NullString;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.JsonbConfig;
import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static ru.otus.gwt.shared.Constants.*;

public class StatisticCustomTagService implements DataOrigin, StatisticService
{
    private static final Logger LOGGER = LogManager.getLogger(StatisticCustomTagService.class);

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
    public List<StatisticEntity> getAllVisitsStatElements()
    {
        try {
            return dbService.getAllStatisticElements();
        } catch (SQLException e) {
            LOGGER.error("getAllVisitsStatElements: catch({}): {}", e.getClass(), e);
        }
        return null;
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
        JAXBContext context = null;
        try {
            context = JAXBContext.newInstance(StatisticEntitiesList.class);
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            OutputStream os = new ByteArrayOutputStream();
            StatisticEntitiesList list = new StatisticEntitiesList(readyResultList);
            m.marshal(list, os);

            return os.toString();
        } catch (JAXBException e) {
            LOGGER.error("getDataXML: catch({}): {}", e.getClass(), e);
        }

        return null;
    }

    @Override
    public String getDataJSON()
    {
        StatisticEntitiesList list = new StatisticEntitiesList(readyResultList);
        JsonbConfig config = new JsonbConfig().withFormatting(true);
        Jsonb jsonb = JsonbBuilder.create(config);

        return jsonb.toJson(list);
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
