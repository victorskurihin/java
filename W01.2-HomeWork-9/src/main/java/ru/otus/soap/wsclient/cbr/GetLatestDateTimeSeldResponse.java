/*
 * GetLatestDateTimeSeldResponse.java
 * This file was last modified at 2018.12.02 23:06 by Victor N. Skurikhin.
 * $Id$
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */

package ru.otus.soap.wsclient.cbr;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 * <p>Java class for anonymous complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="GetLatestDateTimeSeldResult" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {"getLatestDateTimeSeldResult"})
@XmlRootElement(name = "GetLatestDateTimeSeldResponse")
public class GetLatestDateTimeSeldResponse
{
    @XmlElement(name = "GetLatestDateTimeSeldResult", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar getLatestDateTimeSeldResult;

    /**
     * Gets the value of the getLatestDateTimeSeldResult property.
     *
     * @return possible object is
     * {@link XMLGregorianCalendar }
     */
    public XMLGregorianCalendar getGetLatestDateTimeSeldResult()
    {
        return getLatestDateTimeSeldResult;
    }

    /**
     * Sets the value of the getLatestDateTimeSeldResult property.
     *
     * @param value allowed object is
     *              {@link XMLGregorianCalendar }
     */
    public void setGetLatestDateTimeSeldResult(XMLGregorianCalendar value)
    {
        this.getLatestDateTimeSeldResult = value;
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
