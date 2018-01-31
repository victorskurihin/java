package ru.otus.l081;

/**
 * Adapt a LinkedList collection of objects.
 */
public class LinkedListAdapter extends ListAdapter {
    private final String ADAPTEE_TYPE = "java.util.LinkedList";

    @Override
    public String getAdapteeOfType() {
        return ADAPTEE_TYPE;
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF