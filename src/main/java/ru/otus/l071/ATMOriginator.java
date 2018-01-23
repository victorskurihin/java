package ru.otus.l071;

import java.util.SortedMap;
import java.util.TreeMap;

/**
 * The originator is some object that has an internal state.
 * The caretaker is going to do something to the originator,
 * but wants to be able to undo the change.
 * The caretaker first asks the originator for a memento object.
 */
public class ATMOriginator {
    protected SortedMap<Bond, Integer> dispensers = new TreeMap<>(
            (Bond b1, Bond b2) -> b2.value() - b1.value() // reverse sorting
    );

    /**
     * This method save state of the dispenser.
     * @param state
     */
    public void setMemento(SortedMap<Bond, Integer> state) {
        this.dispensers = state;
    }

    /**
     * This method return state of the dispenser.
     * @return the state as SortedList
     */
    public SortedMap<Bond, Integer> getMemento() {
        return dispensers;
    }

    /**
     * This method create and returning a Memento object that stores originator's
     * current internal state.
     * @return the Memento object
     */
    public ATMemento saveMemento() {
        return new ATMemento(dispensers);
    }

    /**
     * This method restore state from the passed in Memento object.
     * @param memento the Memento object
     */
    public void restoreMemento(ATMemento memento) {
        this.dispensers = memento.getState();
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (o instanceof ATMOriginator) {
            ATMOriginator atmOriginator = (ATMOriginator) o;
            return dispensers.equals(atmOriginator.dispensers);
        }
        return false;
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
