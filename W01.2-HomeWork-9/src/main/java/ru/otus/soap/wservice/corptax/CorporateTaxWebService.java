package ru.otus.soap.wservice.corptax;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService(serviceName = "CorporateTaxWebService", name = "CorporateTaxProvider")
public class CorporateTaxWebService implements CorporateTaxProvider
{
    @WebMethod
    public double getCurrentTax(double income, double costs, double taxRate)
    {
        return (income -costs) * taxRate / 100;
    }
}
