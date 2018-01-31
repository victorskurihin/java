package ru.otus.l081;

/**
 * Adapt a TreeMap collection of objects.
 */
public class TreeMapAdapter extends MapAdapter {
    private final String ADAPTEE_TYPE = "java.util.TreeMap";

    @Override
    public String getAdapteeOfType() {
        return ADAPTEE_TYPE;
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF