/*
 * DeptEntity.java
 * This file was last modified at 29.11.18 21:24 by Victor N. Skurikhin.
 * $Id$
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */

/*
 * DeptEntity.java
 * This file was last modified at 29.11.18 11:03 by Victor N. Skurikhin.
 * $Id$
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */

package ru.otus.models;

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
    @SequenceGenerator(name="dept_identifier", sequenceName="dept_id_seq", allocationSize=1)
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "dept_identifier")
    @Column(name = "id", nullable = false, unique = true)
    @XmlAttribute(required = true)
    private long id;

    @Basic
    @Column(name = "pid", nullable = false)
    @XmlAttribute
    private long parentId; // recursion

    @Basic
    @Column(name = "title", nullable = false)
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
