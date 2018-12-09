/*
 * ForexCBRService.java
 * This file was last modified at 2018.12.03 20:05 by Victor N. Skurikhin.
 * $Id$
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */

package ru.otus.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.XML;

import javax.servlet.ServletContext;

import static ru.otus.gwt.shared.Constants.TIMEOUT;
import static ru.otus.utils.IO.saveResultToTruncatedFile;

public class ForexCBRService extends DataOriginFetching
{
    private static final Logger LOGGER = LogManager.getLogger(ForexCBRService.class.getName());

    public static final String DATA_FILE_LOCATION = "CBRForexServiceFileLocation";
    public static final String CBR_DAILY_URL_LOCATION = "CBRForexServiceURLLocation";

    public static final String ENCODING = "windows-1251";
    public static final String encoding1 = "encoding=\"" + ENCODING + '"';
    public static final String encoding2 = "encoding=\"UTF-8\"";

    private ServletContext ctx;
    private String path = null;
    private String url = null;
    private boolean ready = false;

    public ForexCBRService(ServletContext context)
    {
        this.ctx = context;
        this.path = ctx.getInitParameter(DATA_FILE_LOCATION);
        this.url = ctx.getInitParameter(CBR_DAILY_URL_LOCATION);
    }

    @Override
    public synchronized boolean isReady()
    {
        return ready;
    }

    private synchronized void setReady(Boolean ready)
    {
        this.ready = ready;
    }

    private void saveXMLToFile()
    {
        LOGGER.info("getCurrencyExchangeRates: ready.set(false)");

        try {
            String xml = fetchURL(url, TIMEOUT, ENCODING).replaceFirst(encoding1, encoding2);
            if (xml.length() > 1) {
                setReady(false);
                saveResultToTruncatedFile(xml, path);
                setReady(true);
                LOGGER.info("getCurrencyExchangeRates: ready.set(true).");
            }
        }
        catch (Throwable e) {
            LOGGER.error("getCurrencyExchangeRates: catch({}): {}", e.getClass(), e);
        }
    }

    @Override
    public void fetchData()
    {
        saveXMLToFile();
    }

    @Override
    public String getDataXML()
    {
        return loadFromFile(path, LOGGER);
    }

    @Override
    public String getDataJSON()
    {
        return XML.toJSONObject(getDataXML()).toString();
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
