package ru.otus.l101.exeption;

public class AccessException extends RuntimeException {
    public AccessException(IllegalAccessException e) {
        super(e);
    }
}
