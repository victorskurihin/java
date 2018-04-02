package ru.otus.l161.server;

import java.net.Socket;

@FunctionalInterface
public interface OnSocketClose {

    void onClose(Socket socket);

}
