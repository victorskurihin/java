package ru.otus.l071;

/**
 * 
 */
public interface Subject {


    /**
     * @param o
     */
    public void attach(Observer o);

    /**
     * @param o
     */
    public void detach(Observer o);

    /**
     * 
     */
    public void notice();
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
