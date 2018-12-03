/*
 * GroupEntities.java
 * This file was last modified at 2018.12.01 15:40 by Victor N. Skurikhin.
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
@XmlRootElement(name = "groups")
@XmlAccessorType(XmlAccessType.FIELD)
public class GroupEntities implements Serializable, Entities<GroupEntity>
{
    @XmlElement(name = "group")
    @JsonbProperty("groups")
    private List<GroupEntity> groups = new ArrayList<>();

    public GroupEntities()
    { /* None */ }

    public GroupEntities(List<GroupEntity> groups)
    {
        this.groups = groups;
    }

    public List<GroupEntity> getGroups()
    {
        return groups;
    }

    public void setGroups(List<GroupEntity> groups)
    {
        this.groups = groups;
    }

    @Override
    public void add(GroupEntity group)
    {
        groups.add(group);
    }

    @Override
    public List<GroupEntity> asList()
    {
        return getGroups();
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
