package ru.otus.l161;

import ru.otus.l161.app.Msg;
import ru.otus.l161.channel.SocketMsgWorker;
import ru.otus.l161.messages.CloseSocketMsg;

import java.io.IOException;
import java.net.Socket;

/**
 * Created by tully.
 */
class DBServerWorker extends SocketMsgWorker {

    private final Socket socket;

    DBServerWorker(String host, int port) throws IOException {
        this(new Socket(host, port));
    }

    private DBServerWorker(Socket socket) throws IOException {
        super(socket);
        this.socket = socket;
    }

    @Override
    public void close() throws IOException, InterruptedException {
        super.close();
        socket.close();
    }
}
