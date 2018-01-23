package ru.otus.l071;

/**
 * 
 */
public abstract class Observer<T extends ATMObservable> {
    /**
     * 
     */
    public abstract void update(T s);
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
