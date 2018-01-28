package ru.otus.l081;

public class HashSetAdapter extends SetAdapter {
    private final String ADAPTEE_TYPE = "java.util.HashSet";

    @Override
    public String getAdapteeOfType() {
        return ADAPTEE_TYPE;
    }
}
