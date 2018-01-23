package ru.otus.l071;

/**
 * The  Caretaker object  calls saveMemento()  on  the Originator  object, which
 * creates  a Memento object, saves  its current  internal state (setMemento()),
 * and returns the Memento to the Caretaker.
 */
public class Caretaker {
    private ATMemento memento;

    public ATMemento getMemento() {
        return memento;
    }

    public void setMemento(ATMemento memento) {
        this.memento = memento;
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
