package ru.otus.l081;

/**
 * Adapt a HashMap collection of objects.
 */
public class HashMapAdapter extends MapAdapter {
    private final String ADAPTEE_TYPE = "java.util.HashMap";

    @Override
    public String getAdapteeOfType() {
        return ADAPTEE_TYPE;
    }
}
