package ru.otus.soap.wservice.corptax;

import javax.jws.WebMethod;
import javax.jws.WebResult;

public interface CorporateTaxProvider
{
    @WebMethod
    @WebResult(name = "currentTax")
    double getCurrentTax(double income, double costs, double taxRate);
}
