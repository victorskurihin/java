/*
 * Copyright (c) Victor N. Skurikhin 27.11.18 23:58.
 * Constants.java
 * $Id$
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */

package ru.otus.gwt.shared;

/*
 * Created by VSkurikhin at autumn 2018.
 */

public class Constants
{
    public static final String CACHE_SERVICE = "CACHE_SERVICE";
    public static final String DB_SERVICE = "DB_SERVICE";
    public static final String FOREX_SERVICE = "FOREX_SERVICE";
    public static final String NEWS_SERVICE = "NEWS_SERVICE";
    public static final String BROADCASTER_PUBLIC_SERVICE = "BROADCASTER_PUBLIC_SERVICE";
    public static final String BROADCASTER_INSIDE_SERVICE = "BROADCASTER_INSIDE_SERVICE";
    public static final String STAT_SERVICE = "STAT_SERVICE";

    public static final String REQUEST_BROWSERS_JSP = "browsers.jsp";
    public static final String REQUEST_COLLECT_STATISTIC = "stats/insert";
    public static final String REQUEST_DIRECTORY = "directory";
    public static final String REQUEST_FOREX_RATES = "forex";
    public static final String REQUEST_NEWS = "news";
    public static final String REQUEST_VISITS_STAT_JSP = "inside/stat.do";
    public static final String REQUEST_CORPORATE_TAX = "corporate-tax";
    public static final String REQUEST_VISITS_SWITCH_COLLECTION = "inside/switch_collection_enabled";

    public static final String ENDPOINT_PUBLIC = "wspublic";
    public static final String ENDPOINT_INSIDE = "inside/ws";

    public static final String DEFAULT_MARKER_NAME = "DEFAULT_MARKER";
    public static final String ENVIRONMENT_MARKER_NAME = "MARKER_NAME";
    public static final String HEADER_USER_AGENT = "User-Agent";
    public static final String PARAMETER_CLIENT_TIME = "client_time";
    public static final String PARAMETER_PAGE_NAME = "page_name";
    public static final String PARAMETER_PREVIOS_ID = "prev_id";

    public static final String ATTR_BASE_URL = "base_url";
    public static final String ATTR_STAT_ELEMEMTS = "stat_elememts";
    public static final String ATTR_STAT_ENABLED = "stat_enabled";

    public static final String OPERA = "Opera";
    public static final String FIREFOX = "Firefox";
    public static final String CHROME = "Chrome";
    public static final String IE = "IE";
    public static final String SAFARI = "Safari";

    public static final int MIN_VERSION_OPERA = 38;
    public static final int MIN_VERSION_FIREFOX = 63;
    public static final int MIN_VERSION_CHROME = 50;
    public static final int MIN_VERSION_IE = 10;

    public static final String COOKIE_NAME_BROWSER_SUPPORT_CHECK_PASSED = "BrowserSupportCheckPassed";
    public static final String BROWSERS_VERSION_FILTER ="BrowsersVersionFilter";

    public static final String XML_ERROR = "<?xml version='1.0' encoding='UTF-8'?><Error/>";
    public static final String EMPTY = "";

    public static final int TIMEOUT = 800;

    public static final int DEFAULT_UPDATE_PERIOD = 4000; // 4s
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
