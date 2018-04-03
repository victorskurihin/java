package ru.otus.l161.front;

import ru.otus.l161.channel.SocketMsgWorker;
import ru.otus.l161.messages.Address;

import javax.websocket.*;
import java.util.HashMap;
import java.util.Map;

public abstract class FrontEndpoint extends Endpoint implements FrontendService {

    @SuppressWarnings("WeakerAccess")
    protected SocketMsgWorker client;

    public Map<String, String> getOkResult(String authId) {
        Map<String, String> result = new HashMap<>();
        result.put("result", "ok");
        result.put("authid", authId);
        return result;
    }

    public Map<String, String> getErrorResult(String msg) {
        Map<String, String> result = new HashMap<>();
        result.put("result", "Error! " + msg);
        return result;
    }

    public void setClient(SocketMsgWorker client) {
        this.client = client;
    }

    public Address getAddress() {
        return client.getAddress();
    }

    public abstract void setChatEndpoint(FrontEndpoint chat);
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
