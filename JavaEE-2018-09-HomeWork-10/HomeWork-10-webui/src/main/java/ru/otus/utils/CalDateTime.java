/*
 * CalDateTime.java
 * This file was last modified at 2018.12.03 20:05 by Victor N. Skurikhin.
 * $Id$
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */

package ru.otus.utils;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public final class CalDateTime
{
    public static final String expectedPattern = "yyyy-MM-dd";
    private static SimpleDateFormat formatter = new SimpleDateFormat(expectedPattern);

    public static XMLGregorianCalendar parseStringToXMLGregorian(String datetime)
    throws ParseException, DatatypeConfigurationException
    {
        Date date = formatter.parse(datetime);
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.setTime(date);
        DatatypeFactory datatypeFactory = DatatypeFactory.newInstance();
        XMLGregorianCalendar calendar = datatypeFactory.newXMLGregorianCalendar(gregorianCalendar);

        return calendar;
    }

    public static Date localDateTimeToDate(LocalDateTime localDateTime)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(
            localDateTime.getYear(), localDateTime.getMonthValue() - 1, localDateTime.getDayOfMonth(),
            localDateTime.getHour(), localDateTime.getMinute(), localDateTime.getSecond()
        );
        return calendar.getTime();
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
