package ru.otus.dataset;

/*
 * Created by VSkurikhin at autumn 2018.
 */

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "dep_directory")
public class DeptEntity implements DataSet, Serializable
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @Basic
    @Column(name = "pid")
    private long parentId; // recursion

    @Basic
    @Column(name = "title")
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
