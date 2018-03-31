package ru.otus.l161.app;

import ru.otus.l161.channel.Blocks;
import ru.otus.l161.messages.Address;

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
