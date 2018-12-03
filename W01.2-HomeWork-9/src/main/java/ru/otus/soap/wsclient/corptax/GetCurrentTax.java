/*
 * GetCurrentTax.java
 * This file was last modified at 2018.12.01 16:02 by Victor N. Skurikhin.
 * $Id$
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */

package ru.otus.soap.wsclient.corptax;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>Java class for getCurrentTax complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="getCurrentTax">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="arg0" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="arg1" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="arg2" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getCurrentTax", propOrder = {"arg0", "arg1", "arg2"})
public class GetCurrentTax
{
    protected Double arg0;
    protected Double arg1;
    protected Double arg2;

    /**
     * Gets the value of the arg0 property.
     *
     * @return possible object is
     * {@link Double }
     */
    public Double getArg0()
    {
        return arg0;
    }

    /**
     * Sets the value of the arg0 property.
     *
     * @param value allowed object is
     *              {@link Double }
     */
    public void setArg0(Double value)
    {
        this.arg0 = value;
    }

    /**
     * Gets the value of the arg1 property.
     *
     * @return possible object is
     * {@link Double }
     */
    public Double getArg1()
    {
        return arg1;
    }

    /**
     * Sets the value of the arg1 property.
     *
     * @param value allowed object is
     *              {@link Double }
     */
    public void setArg1(Double value)
    {
        this.arg1 = value;
    }

    /**
     * Gets the value of the arg2 property.
     *
     * @return possible object is
     * {@link Double }
     */
    public Double getArg2()
    {
        return arg2;
    }

    /**
     * Sets the value of the arg2 property.
     *
     * @param value allowed object is
     *              {@link Double }
     */
    public void setArg2(Double value)
    {
        this.arg2 = value;
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
