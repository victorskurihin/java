/*
 * Currency.java
 * This file was last modified at 2018.12.02 13:26 by Victor N. Skurikhin.
 * $Id$
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */

package ru.otus.models.cbr;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.json.bind.annotation.JsonbProperty;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@Data
@EqualsAndHashCode
@XmlRootElement(name = "currency")
@XmlAccessorType(XmlAccessType.FIELD)
public class Currency
{
    // Название валюты
    @XmlAttribute
    @JsonbProperty("v-name")
    private String vName;

    // Курс
    @XmlAttribute
    @JsonbProperty("v-curs")
    private String vCurs;

    // Номинал
    @XmlAttribute
    @JsonbProperty("v-nom")
    private String vNom;

    // Цифровой код валюты
    @XmlAttribute
    @JsonbProperty("v-code")
    private String vCode;

    // Символьный код валюты
    @XmlAttribute
    @JsonbProperty("v-ch-code")
    private String vChCode;
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
