package ru.otus.dataset;

/*
 * Created by VSkurikhin at autumn 2018.
 */

import lombok.Data;

import javax.persistence.*;
import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;

import ru.otus.adapter.DataSetAdapter;

@Data
@Entity
@Table(name = "emp_registry")
@XmlRootElement(name = "employee")
@XmlAccessorType(XmlAccessType.FIELD)
public class EmpEntity implements DataSet, Serializable
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    @XmlAttribute(required = true)
    private long id;

    @Basic
    @Column(name = "firsh_name", nullable = false)
    @XmlElement(required = true)
    private String firstName;

    @Basic
    @Column(name = "second_name", nullable = false)
    @XmlElement(required = true)
    private String secondName;

    @Basic
    @Column(name = "sur_name", nullable = false)
    private String surName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department", referencedColumnName = "id")
    @XmlJavaTypeAdapter(DataSetAdapter.class)
    private DeptEntity department;

    @Basic
    @Column(name = "city")
    @XmlElement
    private String city;

    @Basic
    @Column(name = "job")
    @XmlElement
    private String job;

    @Basic
    @Column(name = "salary")
    @XmlAttribute
    private Long salary;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @XmlJavaTypeAdapter(DataSetAdapter.class)
    private UserEntity user;

    @Override
    public String getName()
    {
        return getFirstName() + " " + getSecondName() + " " + getSurName();
    }

    @Override
    public void setName(String name)
    {
        String names[] = name.split(" ");
        if (names.length != 3)
            throw new IndexOutOfBoundsException();
        setFirstName(names[0]);
        setSecondName(names[1]);
        setSurName(names[2]);
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
