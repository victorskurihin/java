package ru.otus.dataset;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.io.Serializable;

@Data
@EqualsAndHashCode
@Entity
@Table(name = "user_groups")
public class GroupEntity  implements DataSet, Serializable
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @Basic
    @Column(name = "groupname")
    private String groupname;

    @Basic
    @Column(name = "login")
    private String login;

    @Override
    public String getName()
    {
        return getGroupname();
    }

    @Override
    public void setName(String name)
    {
        setGroupname(name);
    }
}
