package ru.otus.l111.exeption;

/*
 * Created by VSkurikhin at winter 2018.
 */

public class AccessException extends RuntimeException {
    public AccessException(IllegalAccessException e) {
        super(e);
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
