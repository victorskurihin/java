package ru.otus.l121.dataset;

/*
 * Created by VSkurikhin at winter 2018.
 */

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * TODO
 */
@Entity
public class EmptyDataSet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
