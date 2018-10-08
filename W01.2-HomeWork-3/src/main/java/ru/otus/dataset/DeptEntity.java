package ru.otus.dataset;

/*
 * Created by VSkurikhin at autumn 2018.
 */

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.xml.bind.annotation.*;
import java.io.Serializable;

@Data
@EqualsAndHashCode
@Entity
@Table(name = "dep_directory")
@XmlRootElement(name = "department")
@XmlAccessorType(XmlAccessType.FIELD)
public class DeptEntity implements DataSet, Serializable
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    @XmlAttribute(required = true)
    private long id;

    @Basic
    @Column(name = "pid")
    @XmlAttribute
    private long parentId; // recursion

    @Basic
    @Column(name = "title")
    @XmlElement(required = true)
    private String title;


    @Override
    public String getName()
    {
        return getTitle();
    }

    @Override
    public void setName(String name)
    {
        setTitle(name);
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
