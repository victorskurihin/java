package ru.otus.l161.dataset;

/*
 * Created by VSkurikhin at winter 2018.
 */

import javax.persistence.*;

/**
 * Base Data Set class.
 */
@MappedSuperclass
public abstract class DataSet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private final long id; // This is the primary key field.

    /**
     * TODO
     * @return
     */
    public long getId() {
        return id;
    }

    protected DataSet(long id) {
        this.id = id;
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
