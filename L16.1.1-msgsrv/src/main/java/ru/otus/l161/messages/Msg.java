package ru.otus.l161.messages;

import ru.otus.l161.messages.Address;
import ru.otus.l161.messages.Addressee;

/**
 * Created by tully.
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

    public Address getFrom() {
        return from;
    }

    public Address getTo() {
        return to;
    }

    public void exec(Runnable runnable) {
        runnable.run();
    }
}
