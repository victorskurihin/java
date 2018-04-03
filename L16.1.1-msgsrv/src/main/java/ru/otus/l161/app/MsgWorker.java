package ru.otus.l161.app;

import ru.otus.l161.channel.Blocks;
import ru.otus.l161.messages.Address;
import ru.otus.l161.messages.Msg;

import java.io.IOException;

/**
 * Created by tully.
 */
public interface MsgWorker {
    Address getAddress();

    void send(Msg msg);

    Msg pool();

    @Blocks
    Msg take() throws InterruptedException;

    void close() throws IOException, InterruptedException;
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
