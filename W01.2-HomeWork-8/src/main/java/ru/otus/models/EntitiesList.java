package ru.otus.models;

/*
 * Created by VSkurikhin at autumn 2018.
 */

import java.util.List;

public interface EntitiesList<T extends DataSet>
{
    void add(T emp);
    List<T> asList();
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
