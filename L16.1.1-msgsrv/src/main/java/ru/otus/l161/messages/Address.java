package ru.otus.l161.messages;

import java.lang.management.ManagementFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author tully
 */
public final class Address implements Comparable {
    private static final String COLON = ":";
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

    public String getId() {
        return id;
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

