package ru.otus.l081;

public class LinkedListAdapter extends ListAdapter {
    private final String ADAPTEE_TYPE = "java.util.LinkedList";

    @Override
    public String getAdapteeOfType() {
        return ADAPTEE_TYPE;
    }
}
