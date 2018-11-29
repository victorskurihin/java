/*
 * CurrentTaxConsoleService.java
 * This file was last modified at 29.11.18 11:10 by Victor N. Skurikhin.
 * $Id$
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */

package ru.otus.services;

import ru.otus.soap.wsclient.corptax.CorporateTaxProvider;
import ru.otus.soap.wsclient.corptax.CorporateTaxWebService;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class CurrentTaxConsoleService
{
    private InputStream in;
    private PrintStream ps;
    private double taxRateReportingPeriod;

    public CurrentTaxConsoleService(InputStream inputStream, PrintStream printStream)
    {
        in = inputStream;
        ps = printStream;
    }

    public void caclCurrentTax()
    {
        Scanner scan = new Scanner(in);

        ps.print("Введите доходы: ");
        double income = scan.nextDouble();

        ps.print("Введите расходы: ");
        double costs = scan.nextDouble();

        ps.print("Налоговая ставка: ");
        double taxRate = scan.nextDouble();

        CorporateTaxWebService service = new CorporateTaxWebService();
        CorporateTaxProvider port = service.getCorporateTaxProviderPort();

        taxRateReportingPeriod = port.getCurrentTax(income, costs, taxRate);
    }

    public void outTaxRateReportingPeriod()
    {
        ps.println("Размер налога на прибыль за отчетный период: " + taxRateReportingPeriod);
    }

}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
