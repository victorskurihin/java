package ru.otus.models;

/*
 * Created by VSkurikhin at autumn 2018.
 */

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbTypeAdapter;
import javax.persistence.*;
import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;

import ru.otus.adapters.json.DeptEntityJsonAdapter;
import ru.otus.adapters.json.UserEntityJsonAdapter;
import ru.otus.adapters.xml.DeptEntityTypeXMLAdapter;
import ru.otus.adapters.xml.UserEntityTypeXMLAdapter;

@Data
@EqualsAndHashCode
@Entity
@Table(name = "emp_registry")
@XmlRootElement(name = "employee")
@XmlAccessorType(XmlAccessType.FIELD)
public class EmpEntity implements DataSet, Serializable
{
    @Id
    @SequenceGenerator(name="emp_identifier", sequenceName="emp_id_seq", allocationSize=1)
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "emp_identifier")
    @Column(name = "id", nullable = false)
    @XmlAttribute(required = true)
    @JsonbProperty
    private long id;

    @Basic
    @Column(name = "firsh_name", nullable = false)
    @XmlElement(required = true)
    @JsonbProperty("first-name")
    private String firstName;

    @Basic
    @Column(name = "second_name", nullable = false)
    @XmlElement(required = true)
    @JsonbProperty("second-name")
    private String secondName;

    @Basic
    @Column(name = "sur_name", nullable = false)
    @XmlElement(required = true)
    @JsonbProperty("sur-name")
    private String surName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department", referencedColumnName = "id")
    @XmlElement(required = true)
    @XmlJavaTypeAdapter(DeptEntityTypeXMLAdapter.class)
    @JsonbTypeAdapter(DeptEntityJsonAdapter.class)
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
    @XmlJavaTypeAdapter(UserEntityTypeXMLAdapter.class)
    @JsonbTypeAdapter(UserEntityJsonAdapter.class)
    private UserEntity user;

    @Basic
    @Column(name = "age")
    @XmlAttribute
    private Long age;

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
            throw new IndexOutOfBoundsException(); // TODO Custom exception
        setFirstName(names[0]);
        setSecondName(names[1]);
        setSurName(names[2]);
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
