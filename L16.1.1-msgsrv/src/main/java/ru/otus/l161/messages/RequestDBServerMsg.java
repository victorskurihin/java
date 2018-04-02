package ru.otus.l161.messages;

public class RequestDBServerMsg extends Msg {

    public static final String ID = RequestDBServerMsg.class.getSimpleName();

    public RequestDBServerMsg(Address from) {
        super(RequestDBServerMsg.class, from, new Address(ID));
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
}
