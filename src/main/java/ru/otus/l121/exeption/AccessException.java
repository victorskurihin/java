package ru.otus.l121.exeption;

public class AccessException extends RuntimeException {
    public AccessException(IllegalAccessException e) {
        super(e);
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
