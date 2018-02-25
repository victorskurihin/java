package ru.otus.l121.dataset;

import javax.persistence.*;

/**
 * Base Data Set class.
 */
@MappedSuperclass
public abstract class DataSet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private final long id; // Primary key field.

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
