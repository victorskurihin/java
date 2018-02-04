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
