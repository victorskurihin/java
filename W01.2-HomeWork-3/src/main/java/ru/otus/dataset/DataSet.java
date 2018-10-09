package ru.otus.dataset;

/*
 * Created by VSkurikhin at winter 2018.
 */

import javax.persistence.*;

/**
 * Base Data Set class.
 */
@MappedSuperclass
public interface DataSet
{
    long getId();
    void setId(long id);
    String getName();
    void setName(String name);
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
