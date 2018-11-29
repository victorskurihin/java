/*
 * BrowsersVersionFilter.java
 * This file was last modified at 29.11.18 11:12 by Victor N. Skurikhin.
 * $Id$
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */

package ru.otus.servlets;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.otus.utils.BrowserInfo;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

import static ru.otus.gwt.shared.Constants.*;

@WebFilter(filterName = BROWSERS_VERSION_FILTER, urlPatterns = "/*")
public class BrowsersVersionFilter extends HttpFilter
{
    private static final Logger LOGGER = LogManager.getLogger(BrowsersVersionFilter.class.getName());

    private boolean isBrowserSupportCheckAlreadyPassed(HttpServletRequest request)
    {
        Cookie[] cookies = request.getCookies();

        return cookies != null && Arrays.stream(cookies).anyMatch(
                cookie -> cookie.getName().equals(COOKIE_NAME_BROWSER_SUPPORT_CHECK_PASSED)
        );
    }

    private boolean isDecorRequest(HttpServletRequest request)
    {
        String url = request.getRequestURL().toString();

        return url.contains("/css/") || url.contains("/img/") || url.contains("/webfonts/");
    }

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
    throws IOException, ServletException
    {
        if (isDecorRequest(req)) {
            chain.doFilter(req, res);
        }
        else if (isBrowserSupportCheckAlreadyPassed(req) || BrowserInfo.isSupportedBrowser(req)) {
            LOGGER.debug("BrowserInfo supported.");
            res.addCookie(new Cookie(COOKIE_NAME_BROWSER_SUPPORT_CHECK_PASSED, "1"));
            LOGGER.debug("Cookie added.");
            chain.doFilter(req, res);
        }
        else {
            LOGGER.debug("BrowserInfo not supported. Forward ...");
            req.getRequestDispatcher("/" + REQUEST_BROWSERS_JSP).forward(req, res);
            LOGGER.debug("Never logged.");
        }
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
