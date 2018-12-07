/*
 * ObjectFactory.java
 * This file was last modified at 2018.12.03 20:05 by Victor N. Skurikhin.
 * $Id$
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */

package ru.otus.soap.wsclient.corptax;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;

/**
 * This object contains factory methods for each
 * Java content interface and Java element interface
 * generated in the ru.otus.soap.wsclient.corptax package.
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
    private final static QName _GetCurrentTax_QNAME = new QName(
        "http://corptax.wservice.soap.otus.ru/",
        "getCurrentTax"
    );
    private final static QName _GetCurrentTaxResponse_QNAME = new QName(
        "http://corptax.wservice.soap.otus.ru/",
        "getCurrentTaxResponse"
    );

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: ru.otus.soap.wsclient.corptax
     */
    public ObjectFactory()
    { /* None */ }

    /**
     * Create an instance of {@link GetCurrentTax }
     */
    public GetCurrentTax createGetCurrentTax()
    {
        return new GetCurrentTax();
    }

    /**
     * Create an instance of {@link GetCurrentTaxResponse }
     */
    public GetCurrentTaxResponse createGetCurrentTaxResponse()
    {
        return new GetCurrentTaxResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetCurrentTax }{@code >}}
     */
    @XmlElementDecl(namespace = "http://corptax.wservice.soap.otus.ru/", name = "getCurrentTax")
    public JAXBElement<GetCurrentTax> createGetCurrentTax(GetCurrentTax value)
    {
        return new JAXBElement<GetCurrentTax>(_GetCurrentTax_QNAME, GetCurrentTax.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetCurrentTaxResponse }{@code >}}
     */
    @XmlElementDecl(namespace = "http://corptax.wservice.soap.otus.ru/", name = "getCurrentTaxResponse")
    public JAXBElement<GetCurrentTaxResponse> createGetCurrentTaxResponse(GetCurrentTaxResponse value)
    {
        return new JAXBElement<GetCurrentTaxResponse>(
            _GetCurrentTaxResponse_QNAME,
            GetCurrentTaxResponse.class,
            null,
            value
        );
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
