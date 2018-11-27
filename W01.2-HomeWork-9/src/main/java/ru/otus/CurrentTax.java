/*
 * Copyright (c) Victor N. Skurikhin 27.11.18 23:53.
 * CurrentTax.java
 * $Id$
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */

package ru.otus;

/**
 * mvn clean compile dependency:copy-dependencies
 * currentTax.sh or currentTax.bat
 */

import ru.otus.services.CurrentTaxConsoleService;

public class CurrentTax
{
    public static void main(String[] args)
    {
        CurrentTaxConsoleService service = new CurrentTaxConsoleService(System.in, System.out);

        service.caclCurrentTax();
        service.outTaxRateReportingPeriod();
    }

}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
