/*
 * CBRCursConsoleService.java
 * This file was last modified at 2018.12.02 22:53 by Victor N. Skurikhin.
 * $Id$
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */

package ru.otus.services;

import ru.otus.adapters.soap.GetCursOnDateXMLAdapter;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.GregorianCalendar;
import java.util.Scanner;

public class CBRCursConsoleService
{
    private InputStream in;
    private PrintStream ps;

    public CBRCursConsoleService(InputStream inputStream, PrintStream printStream)
    {
        in = inputStream;
        ps = printStream;
    }

    public void getCursOnDateXML() throws DatatypeConfigurationException
    {
        Scanner scan = new Scanner(in);

        ps.print("Введите год: ");
        int year = scan.nextInt();

        ps.print("Введите месяц: ");
        int month = scan.nextInt() + 1;

        ps.print("Введите день: ");
        int day = scan.nextInt();

        GregorianCalendar gregorianCalendar = new GregorianCalendar(year, month, day);
        DatatypeFactory datatypeFactory = DatatypeFactory.newInstance();
        XMLGregorianCalendar date = datatypeFactory.newXMLGregorianCalendar(gregorianCalendar);
        GetCursOnDateXMLAdapter adapter = new GetCursOnDateXMLAdapter(date);

        System.out.println(adapter.getList());
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
