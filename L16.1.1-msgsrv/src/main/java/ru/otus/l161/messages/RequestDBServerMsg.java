package ru.otus.l161.messages;

public class RequestDBServerMsg extends Msg {

    public static final String ID = RequestDBServerMsg.class.getSimpleName();

    public RequestDBServerMsg(Address from) {
        super(RequestDBServerMsg.class, from, new Address(ID));
    }

    private RequestDBServerMsg(Address from, Address to) {
        super(RequestDBServerMsg.class, from, to);
    }

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public String toString() {
        return  ID +
               "{ from=" + super.getFrom() +
               ", to="   + super.getTo() +
               " }";
    }

    public RequestDBServerMsg createAnswer(Address dbServerAddress) {
        return new RequestDBServerMsg(dbServerAddress, getFrom());
    }
}
