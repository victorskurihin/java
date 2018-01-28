package ru.otus.l081;

public class TreeMapAdapter extends MapAdapter {
    private final String ADAPTEE_TYPE = "java.util.TreeMap";

    @Override
    public String getAdapteeOfType() {
        return ADAPTEE_TYPE;
    }
}
