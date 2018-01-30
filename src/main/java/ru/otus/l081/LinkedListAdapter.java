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
