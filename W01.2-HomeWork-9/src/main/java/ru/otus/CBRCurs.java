/*
 * CBRCurs.java
 * This file was last modified at 2018.12.01 21:47 by Victor N. Skurikhin.
 * $Id$
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */

package ru.otus;

/**
 * mvn clean compile dependency:copy-dependencies
 * currentTax.sh or currentTax.bat
 */

import ru.otus.services.CBRCursConsoleService;

import javax.xml.datatype.DatatypeConfigurationException;

public class CBRCurs
{
    public static void main(String[] args) throws DatatypeConfigurationException
    {
        CBRCursConsoleService service = new CBRCursConsoleService(System.in, System.out);

        service.getCursOnDateXML();
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
