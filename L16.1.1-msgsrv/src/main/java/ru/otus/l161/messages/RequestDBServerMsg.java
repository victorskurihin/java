package ru.otus.l161.messages;

/**
 * Created by tully.
 */
public class RequestDBServerMsg extends Msg {

    public static final String ID = RequestDBServerMsg.class.getName();

    public static final String REQUEST_DB_SERVER = "REQUEST DB SERVER";

    public RequestDBServerMsg(Address from) {
        super(RequestDBServerMsg.class, from, new Address(REQUEST_DB_SERVER));
    }

    @Override
    public String toString() {
        return  ID +
               "{ from=" + super.getFrom() +
               ", to=" + super.getTo() +
               " }";
    }
}
