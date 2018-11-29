/*
 * Servlet.java
 * This file was last modified at 29.11.18 11:19 by Victor N. Skurikhin.
 * $Id$
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */

package ru.otus.utils;

import javax.servlet.http.HttpServletRequest;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.PrintWriter;

public class Servlet
{
    public static void okXML(PrintWriter out)
    {
        out.println("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>");
        out.println("<ok ok='ok'/>");
    }

    public static String retrieveCommand(HttpServletRequest request)
    {
        try {
            String pathInfo = request.getPathInfo();
            if (pathInfo.startsWith("/")) {
                return pathInfo.substring(1);
            }
        } catch (NullPointerException ignored) { }
        return null;
    }

    public static XPathExpression getXPathExpression(String expression)
    throws XPathExpressionException
    {
        XPathFactory xpathfactory = XPathFactory.newInstance();
        XPath xpath = xpathfactory.newXPath();

        return xpath.compile(expression);
    }

    public static void transformDOMToStream(DOMSource source, StreamResult resultOut)
    throws TransformerException
    {
        TransformerFactory.newInstance().newTransformer().transform(source, resultOut);
    }

    public static String getBaseURL(HttpServletRequest request)
    {
        String url = request.getRequestURL().toString();
        return url.substring(0, url.length() - request.getRequestURI().length())
                + request.getContextPath() + "/";
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
