/*
 * UserEntities.java
 * This file was last modified at 2018.12.01 15:39 by Victor N. Skurikhin.
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
@XmlRootElement(name = "users")
@XmlAccessorType(XmlAccessType.FIELD)
public class UserEntities implements Serializable, Entities<UserEntity>
{
    @XmlElement(name = "user")
    @JsonbProperty("users")
    private List<UserEntity> users = new ArrayList<>();

    public UserEntities()
    { /* None */ }

    public UserEntities(List<UserEntity> users)
    {
        this.users = users;
    }

    public List<UserEntity> getUsers()
    {
        return users;
    }

    public void setUsers(List<UserEntity> users)
    {
        this.users = users;
    }

    @Override
    public void add(UserEntity emp)
    {
        users.add(emp);
    }

    @Override
    public List<UserEntity> asList()
    {
        return getUsers();
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
