package ru.otus.l141.sort;

public class RuntimeInterruptedException extends RuntimeException {
    public RuntimeInterruptedException(InterruptedException e) {
        super(e);
    }
}
