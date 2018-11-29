/*
 * EmpEntitiesList.java
 * This file was last modified at 29.11.18 11:04 by Victor N. Skurikhin.
 * $Id$
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */

package ru.otus.models;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbTransient;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode
@XmlRootElement(name = "employees")
@XmlAccessorType(XmlAccessType.FIELD)
public class EmpEntitiesList implements Serializable, EntitiesList<EmpEntity>
{
    @XmlElement(name = "employee")
    @JsonbProperty("employees")
    private List<EmpEntity> employees = new ArrayList<>();

    public EmpEntitiesList() {}

    public EmpEntitiesList(List<EmpEntity> employees)
    {
        this.employees = employees;
    }

    public List<EmpEntity> getEmployees()
    {
        return employees;
    }

    public void setEmployees(List<EmpEntity> employees)
    {
        this.employees = employees;
    }

    @Override
    public void add(EmpEntity emp) {
        employees.add(emp);
    }

    @Override
    public List<EmpEntity> asList()
    {
        return getEmployees();
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
