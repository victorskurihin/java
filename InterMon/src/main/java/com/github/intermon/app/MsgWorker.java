package com.github.intermon.app;

/*
 * Created by VSkurikhin at Sun Apr 15 13:20:47 MSK 2018.
 */

import com.github.intermon.channel.Blocks;
import com.github.intermon.messages.Msg;

import java.io.IOException;

/**
 *.
 */
public interface MsgWorker {
    void send(Msg msg);

    Msg pool();

    @Blocks
    Msg take() throws InterruptedException;

    void close() throws IOException;
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
