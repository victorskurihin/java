package ru.otus.l091;

public abstract class DataSet {
    private final long id;

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
