package ru.otus.l071;

import java.util.SortedMap;
import java.util.TreeMap;

/**
 * The memento pattern is implemented with three objects: the originator,
 * a caretaker and a memento. The originator is some object that has an internal
 * state.  The caretaker is going  to do something  to the originator, but wants
 * to be able  to undo the change.  The caretaker first asks  the originator for
 * a memento object.
 */
public class ATMemento {
    private final SortedMap<Bond, Integer> state;

    public ATMemento(SortedMap<Bond, Integer> state) {
        this.state = new TreeMap<>(state);
    }

    public SortedMap<Bond, Integer> getState() {
        return state;
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
