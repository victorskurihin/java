package com.github.intermon.exeption;

/*
 * Created by VSkurikhin at Sun Apr 15 21:37:45 MSK 2018.
 */

public class NewInstanceException extends RuntimeException {
    public NewInstanceException(ReflectiveOperationException e) {
        super(e);
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF

