/*
 * ObjectFactory.java
 * This file was last modified at 2018.12.03 20:05 by Victor N. Skurikhin.
 * $Id$
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */

package ru.otus.soap.wsclient.salary;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;

/**
 * This object contains factory methods for each
 * Java content interface and Java element interface
 * generated in the ru.otus.soap.wsclient.salary package.
 * <p>An ObjectFactory allows you to programatically
 * construct new instances of the Java representation
 * for XML content. The Java representation of XML
 * content can consist of schema derived interfaces
 * and classes representing the binding of schema
 * type definitions, element declarations and model
 * groups.  Factory methods for each of these are
 * provided in this class.
 */
@XmlRegistry
public class ObjectFactory
{

    private final static QName _GetAvgSalaryResponse_QNAME = new QName(
        "http://salary.wservice.soap.otus.ru/",
        "getAvgSalaryResponse"
    );
    private final static QName _GetMaxSalary_QNAME = new QName(
        "http://salary.wservice.soap.otus.ru/",
        "getMaxSalary"
    );
    private final static QName _GetAvgSalary_QNAME = new QName(
        "http://salary.wservice.soap.otus.ru/",
        "getAvgSalary"
    );
    private final static QName _GetMaxSalaryResponse_QNAME = new QName(
        "http://salary.wservice.soap.otus.ru/",
        "getMaxSalaryResponse"
    );

    /**
     * Create a new ObjectFactory that can be used to create new instances
     * of schema derived classes for package: ru.otus.soap.wsclient.salary
     */
    public ObjectFactory()
    { /* None */ }

    /**
     * Create an instance of {@link GetMaxSalary }
     */
    public GetMaxSalary createGetMaxSalary()
    {
        return new GetMaxSalary();
    }

    /**
     * Create an instance of {@link GetAvgSalary }
     */
    public GetAvgSalary createGetAvgSalary()
    {
        return new GetAvgSalary();
    }

    /**
     * Create an instance of {@link GetMaxSalaryResponse }
     */
    public GetMaxSalaryResponse createGetMaxSalaryResponse()
    {
        return new GetMaxSalaryResponse();
    }

    /**
     * Create an instance of {@link GetAvgSalaryResponse }
     */
    public GetAvgSalaryResponse createGetAvgSalaryResponse()
    {
        return new GetAvgSalaryResponse();
    }

    /**
     * Create an instance of
     * {@link JAXBElement }{@code <}{@link GetAvgSalaryResponse }{@code >}}
     */
    @XmlElementDecl(namespace = "http://salary.wservice.soap.otus.ru/", name = "getAvgSalaryResponse")
    public JAXBElement<GetAvgSalaryResponse> createGetAvgSalaryResponse(GetAvgSalaryResponse value)
    {
        return new JAXBElement<GetAvgSalaryResponse>(
            _GetAvgSalaryResponse_QNAME,
            GetAvgSalaryResponse.class,
            null,
            value
        );
    }

    /**
     * Create an instance of
     * {@link JAXBElement }{@code <}{@link GetMaxSalary }{@code >}}
     */
    @XmlElementDecl(namespace = "http://salary.wservice.soap.otus.ru/", name = "getMaxSalary")
    public JAXBElement<GetMaxSalary> createGetMaxSalary(GetMaxSalary value)
    {
        return new JAXBElement<GetMaxSalary>(_GetMaxSalary_QNAME, GetMaxSalary.class, null, value);
    }

    /**
     * Create an instance of
     * {@link JAXBElement }{@code <}{@link GetAvgSalary }{@code >}}
     */
    @XmlElementDecl(namespace = "http://salary.wservice.soap.otus.ru/", name = "getAvgSalary")
    public JAXBElement<GetAvgSalary> createGetAvgSalary(GetAvgSalary value)
    {
        return new JAXBElement<GetAvgSalary>(_GetAvgSalary_QNAME, GetAvgSalary.class, null, value);
    }

    /**
     * Create an instance of
     * {@link JAXBElement }{@code <}{@link GetMaxSalaryResponse }{@code >}}
     */
    @XmlElementDecl(namespace = "http://salary.wservice.soap.otus.ru/", name = "getMaxSalaryResponse")
    public JAXBElement<GetMaxSalaryResponse> createGetMaxSalaryResponse(GetMaxSalaryResponse value)
    {
        return new JAXBElement<GetMaxSalaryResponse>(
            _GetMaxSalaryResponse_QNAME,
            GetMaxSalaryResponse.class,
            null,
            value
        );
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
