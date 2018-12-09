/*
 * CorporateTaxWebService.java
 * This file was last modified at 2018.12.03 20:05 by Victor N. Skurikhin.
 * $Id$
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */

package ru.otus.soap.wservice.corptax;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService(serviceName = "CorporateTaxWebService", name = "CorporateTaxProvider")
public class CorporateTaxWebService implements CorporateTaxProvider
{
    @WebMethod
    public Double getCurrentTax(Double income, Double costs, Double taxRate)
    {
        return  (income -costs) * taxRate / 100;
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
