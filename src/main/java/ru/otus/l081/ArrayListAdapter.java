package ru.otus.l081;

public class ArrayListAdapter extends ListAdapter {
    private final String ADAPTEE_TYPE = "java.util.ArrayList";

    @Override
    public String getAdapteeOfType() {
        return ADAPTEE_TYPE;
    }
}
