/*
 * GetAvgSalaryResponse.java
 * This file was last modified at 2018.12.01 16:02 by Victor N. Skurikhin.
 * $Id$
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */

package ru.otus.soap.wsclient.salary;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>Java class for getAvgSalaryResponse complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="getAvgSalaryResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="return" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getAvgSalaryResponse", propOrder = {"_return"})
public class GetAvgSalaryResponse
{
    @XmlElement(name = "return")
    protected Double _return;

    /**
     * Gets the value of the return property.
     *
     * @return possible object is
     * {@link Double }
     */
    public Double getReturn()
    {
        return _return;
    }

    /**
     * Sets the value of the return property.
     *
     * @param value allowed object is
     *              {@link Double }
     */
    public void setReturn(Double value)
    {
        this._return = value;
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
