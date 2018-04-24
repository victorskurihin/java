package com.github.intermon.messages;

public interface Addressee {
    Address getAddress();

    void deliver(Msg msg);
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
