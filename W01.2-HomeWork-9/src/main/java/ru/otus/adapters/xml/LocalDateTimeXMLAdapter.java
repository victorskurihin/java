/*
 * LocalDateTimeXMLAdapter.java
 * This file was last modified at 29.11.18 10:35 by Victor N. Skurikhin.
 * $Id$
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */

package ru.otus.adapters.xml;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.LocalDateTime;

public class LocalDateTimeXMLAdapter extends XmlAdapter<String, LocalDateTime>
{
    @Override
    public LocalDateTime unmarshal(String v) throws Exception
    {
        return LocalDateTime.parse(v);
    }

    @Override
    public String marshal(LocalDateTime v) throws Exception
    {
        return v.toString();
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
