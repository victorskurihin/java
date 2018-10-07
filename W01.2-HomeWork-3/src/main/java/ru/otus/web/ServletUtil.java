package ru.otus.web;

import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;

public class ServletUtil
{
    public static StringBuilder outHTMLHeader(PrintWriter out, String title)
    {
        StringBuilder sb = new StringBuilder("<html>\n<head>\n<html>\n<head>\n")
            .append("    <title>").append(title).append("</title>\n")
            .append("    <meta http-equiv='Content-Type' content='text/html; charset=UTF-8' />\n")
            .append("</head>\n").append("<body>\n")
            .append("    <h2>").append(title).append("</h2>");
        return sb;
    }

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

}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
