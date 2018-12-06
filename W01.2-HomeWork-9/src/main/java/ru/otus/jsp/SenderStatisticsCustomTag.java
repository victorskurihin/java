/*
 * SenderStatisticsCustomTag.java
 * This file was last modified at 2018.12.03 20:05 by Victor N. Skurikhin.
 * $Id$
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */

package ru.otus.jsp;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;

public class SenderStatisticsCustomTag extends SimpleTagSupport
{
    private String pageName;

    @Override
    public void doTag() throws JspException, IOException
    {
        StringBuilder sb = new StringBuilder();
        sb.append("<script>")
                .append("logToStatistics(\"").append(pageName).append("\");")
                .append("</script>");
        JspWriter out = getJspContext().getOut();
        out.println(sb.toString());
    }

    public void setPageName(String pageName)
    {
        this.pageName = pageName;
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
