package ru.otus.l101.exeption;

public class CreateObjectException extends RuntimeException {
    public CreateObjectException(Throwable e) {
        super(e);
    }
}
