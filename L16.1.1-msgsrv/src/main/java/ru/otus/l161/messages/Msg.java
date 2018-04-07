package ru.otus.l161.messages;

/*
 * Created by tully.
 */

/**
 * This is contract for message (Msg) classes with default the helper
 * realization.
 *
 * A class is a superclass for implementing a message (Msg) interfaces.
 */

public abstract class Msg {
    private final Address from;
    private final Address to;
    public static final String CLASS_NAME_VARIABLE = "className";

    private final String className;

    protected Msg(Class<?> klass, Address from, Address to) {
        this.className = klass.getName();
        this.from = from;
        this.to = to;
    }

    public abstract String getId();

    public Address getFrom() {
        return from;
    }

    public Address getTo() {
        return to;
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
