package ru.otus.l081;

/**
 * Adapt a TreeSet collection of objects.
 */
public class TreeSetAdapter extends SetAdapter {
    private final String ADAPTEE_TYPE = "java.util.TreeSet";

    @Override
    public String getAdapteeOfType() {
        return ADAPTEE_TYPE;
    }
}
