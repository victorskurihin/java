/*
 * DeptEntitiesList.java
 * This file was last modified at 29.11.18 11:03 by Victor N. Skurikhin.
 * $Id$
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */

package ru.otus.models;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.json.bind.annotation.JsonbProperty;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode
@XmlRootElement(name = "departments")
@XmlAccessorType(XmlAccessType.FIELD)
public class DeptEntitiesList implements Serializable, EntitiesList<DeptEntity>
{
    @XmlElement(name = "department")
    @JsonbProperty("departments")
    private List<DeptEntity> departments = new ArrayList<>();

    public DeptEntitiesList() {}

    public DeptEntitiesList(List<DeptEntity> departments)
    {
        this.departments = departments;
    }

    public List<DeptEntity> getDepartments()
    {
        return departments;
    }

    public void setDepartments(List<DeptEntity> departments)
    {
        this.departments = departments;
    }

    @Override
    public void add(DeptEntity department) {
        departments.add(department);
    }

    @Override
    public List<DeptEntity> asList()
    {
        return getDepartments();
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
