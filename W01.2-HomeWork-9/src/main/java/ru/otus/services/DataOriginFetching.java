/*
 * DataOriginFetching.java
 * This file was last modified at 2018.12.01 15:48 by Victor N. Skurikhin.
 * $Id$
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */

package ru.otus.services;

import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.stream.Collectors;

import static ru.otus.gwt.shared.Constants.XML_ERROR;
import static ru.otus.utils.IO.converURIToAbsolutePath;
import static ru.otus.utils.IO.readFile;

public abstract class DataOriginFetching implements DataOrigin
{
    public String fetchURL(String url, int timeout, String encoding) throws IOException
    {
        URL u = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) u.openConnection();
        connection.setConnectTimeout(timeout);

        try (InputStreamReader isr = new InputStreamReader(connection.getInputStream(), encoding)) {
            return new BufferedReader(isr).lines().collect(Collectors.joining());
        }
    }

    public String loadFromFile(String uriPath, final Logger logger)
    {
        if (isReady()) {
            String aPath = null;
            StringBuilder sb = new StringBuilder();

            try {
                aPath = converURIToAbsolutePath(uriPath);
            }
            catch (URISyntaxException e) {
                logger.info("loadFromFile: catch({}): {}", e.getClass(), e);
                return XML_ERROR;
            }

            try {
                return readFile(aPath, Charset.defaultCharset());
            }
            catch (IOException e) {
                logger.info("loadFromFile: empty file: {}", uriPath);
                return XML_ERROR;
            }
        }
        else {
            logger.info("loadFromFile: isn't ready!");
        }

        return XML_ERROR;
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
