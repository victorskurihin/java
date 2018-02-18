package ru.otus.l101.exeption;

public class NewInstanceException extends RuntimeException {
    public NewInstanceException(ReflectiveOperationException e) {
        super(e);
    }
}
