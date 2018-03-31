package ru.otus.l161.messages;

import ru.otus.l161.app.Msg;

/**
 * Created by tully.
 */
public class RequestDBServerMsg extends Msg {

    public static final String REQUEST_DB_SERVER = "REQUEST DB SERVER";

    public RequestDBServerMsg(Address from) {
        super(RequestDBServerMsg.class, from, new Address(REQUEST_DB_SERVER));
    }

    @Override
    public String toString() {
        return "RegisterDBServerMsg{" +
               " from=" + super.getFrom() +
               ", to=" + super.getTo() +
               " }";
    }

    @Override
    public void exec(Addressee addressee) {
        // TODO
    }
}
