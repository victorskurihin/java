package ru.otus.dataset;

/*
 * Created by VSkurikhin at autumn 2018.
 */

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
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    @XmlAttribute(required = true)
    private long id;

    @Basic
    @Column(name = "login")
    @XmlAttribute(required = true)
    private String login;

    @Basic
    @Column(name = "password")
    @XmlAttribute
    private String password;

    @Override
    public String getName()
    {
        return getLogin();
    }

    @Override
    public void setName(String name)
    {
        setLogin(name);
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
