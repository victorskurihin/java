package ru.otus.l161.messages;

/*
 * Created by VSkurikhin at spring 2018.
 */

import java.lang.management.ManagementFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * This class  is for marking messages with  a label. When creating  an Address
 * object, the label is generated that consists of a name of RuntimeMXBean plus
 * the thread number plus a unique index in that thread.
 */

public final class Address implements Comparable {
    public static final String COLON = ":";
    private static final AtomicInteger ID_GENERATOR = new AtomicInteger();
    private final String id;

    public Address(){
        id = ManagementFactory.getRuntimeMXBean().getName() +
             COLON + String.valueOf(Thread.currentThread().getId()) +
             COLON + String.valueOf(ID_GENERATOR.getAndIncrement());
    }

    public Address(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Address address = (Address) o;

        return id != null ? id.equals(address.id) : address.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Address{ " + "id='" + id + "' }";
    }

    @Override
    public int compareTo(Object o) {
        if (o instanceof Address) {
            return id.compareTo(((Address) o).id);
        } else
            throw new RuntimeException();
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
