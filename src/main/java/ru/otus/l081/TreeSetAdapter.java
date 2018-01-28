package ru.otus.l081;

public class TreeSetAdapter extends SetAdapter {
    private final String ADAPTEE_TYPE = "java.util.TreeSet";

    @Override
    public String getAdapteeOfType() {
        return ADAPTEE_TYPE;
    }
}
