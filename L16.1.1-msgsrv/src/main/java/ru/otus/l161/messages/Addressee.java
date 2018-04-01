package ru.otus.l161.messages;

public interface Addressee {
    Address getAddress();

    void deliver(Msg msg);
}
