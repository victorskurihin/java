package ru.otus.dataset;

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
import java.util.List;

@Data
@EqualsAndHashCode
@XmlRootElement(name="employees")
@XmlAccessorType(XmlAccessType.FIELD)
public class EmpEntitiesList implements Serializable
{
    @XmlElement(name = "employee")
    @JsonbProperty("employees")
    private List<EmpEntity> rows = null;

    public EmpEntitiesList() {}

    public EmpEntitiesList(List<EmpEntity> rows)
    {
        this.rows = rows;
    }

    public List<EmpEntity> getRows()
    {
        return rows;
    }

    public void setRows(List<EmpEntity> rows)
    {
        this.rows = rows;
    }

    public void add(EmpEntity emp) {
        rows.add(emp);
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
