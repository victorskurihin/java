/*
 * UserEntity.java
 * This file was last modified at 2018.12.03 20:05 by Victor N. Skurikhin.
 * $Id$
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */

package ru.otus.models;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@Data
@EqualsAndHashCode
@Entity
@Table(name = "users")
@NamedQueries({
    @NamedQuery(name = "findUserById", query = "SELECT u FROM UserEntity u WHERE u.id = :id"),
    @NamedQuery(name = "findUserByName", query = "SELECT u FROM UserEntity u WHERE u.login= :name")
})
@XmlRootElement(name = "user")
@XmlAccessorType(XmlAccessType.FIELD)
public class UserEntity implements DataSet, Serializable
{
    @Id
    @SequenceGenerator(name = "user_identifier", sequenceName = "user_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "user_identifier")
    @Column(name = "id", nullable = false, unique = true)
    @XmlAttribute(required = true)
    private long id;

    @Basic
    @Column(name = "login", nullable = false)
    @XmlAttribute(required = true)
    private String login;

    @Basic
    @Column(name = "password")
    @XmlAttribute
    private String password;

    @Override
    public String nameGet()
    {
        return getLogin();
    }

    @Override
    public void letName(String name)
    {
        setLogin(name);
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
