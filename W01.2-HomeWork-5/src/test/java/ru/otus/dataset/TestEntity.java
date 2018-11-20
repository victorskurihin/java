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
@Table(name = "users")
@XmlRootElement(name = "user")
@XmlAccessorType(XmlAccessType.FIELD)
public class TestEntity implements DataSet, Serializable
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    @XmlAttribute(required = true)
    private long id;

    @Basic
    @Column(name = "test")
    @XmlAttribute(required = true)
    private String test;

    @Override
    public String getName()
    {
        return getTest();
    }

    @Override
    public void setName(String name)
    {
        setTest(name);
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
