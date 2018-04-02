package ru.otus.l161.messages;

/**
 * Created by tully.
 */
public class RegisterDBServerMsg extends Msg {

    public static final String ID = RegisterDBServerMsg.class.getName();

    public static final String DB_REGISTRATOR = "DB REGISTRATOR";

    public RegisterDBServerMsg(Address from) {
        super(RegisterDBServerMsg.class, from, new Address(DB_REGISTRATOR));
    }

    @Override
    public String toString() {
        return ID +
               "{ from=" + super.getFrom() +
               ", to=" + super.getTo() +
               " }";
    }
}
