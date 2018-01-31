package ru.otus.l081;

/**
 * Adapt a HashSet collection of objects.
 */
public class HashSetAdapter extends SetAdapter {
    private final String ADAPTEE_TYPE = "java.util.HashSet";

    @Override
    public String getAdapteeOfType() {
        return ADAPTEE_TYPE;
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF