package ru.otus.l161.messages;

public class CloseSocketMsg extends Msg {

    public static final String ID = CloseSocketMsg.class.getSimpleName();

    public CloseSocketMsg(Address from) {
        super(CloseSocketMsg.class, from, new Address(ID));
    }

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public String toString() {
        return ID +
               "{ from=" + super.getFrom() +
               ", to=" + super.getTo() +
               " }";
    }
}
