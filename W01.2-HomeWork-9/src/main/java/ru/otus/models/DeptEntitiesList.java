package ru.otus.models;

/*
 * Created by VSkurikhin at autumn 2018.
 */

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
