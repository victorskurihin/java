package ru.otus.web;

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
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
