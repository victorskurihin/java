package ru.otus.l161.messages;

/**
 * Created by tully.
 */
public class CloseSocketMsg extends Msg {

    public static final String CLOSE_SOCKET = "CLOSE SOCKET";

    public CloseSocketMsg(Address from) {
        super(CloseSocketMsg.class, from, new Address(CLOSE_SOCKET));
    }

    public CloseSocketMsg() {
        this(new Address("CLOSE"));
    }

    @Override
    public String toString() {
        return "CloseSocketMsg{" +
               " from=" + super.getFrom() +
               ", to=" + super.getTo() +
               " }";
    }
}
