package ru.otus.l161.messages;

import ru.otus.l161.app.Msg;

/**
 * Created by tully.
 */
public class RegisterDBServerMsg extends Msg {

    public static final String DB_REGISTRATOR = "DB REGISTRATOR";

    public RegisterDBServerMsg(Address from) {
        super(RegisterDBServerMsg.class, from, new Address(DB_REGISTRATOR));
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
