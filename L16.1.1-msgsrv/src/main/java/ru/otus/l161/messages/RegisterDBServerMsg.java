package ru.otus.l161.messages;

public class RegisterDBServerMsg extends Msg {

    public static final String ID = RegisterDBServerMsg.class.getSimpleName();

    public RegisterDBServerMsg(Address from) {
        super(RegisterDBServerMsg.class, from, new Address(ID));
    }

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public String toString() {
        return ID +
               "{ from=" + super.getFrom() +
               ", to="   + super.getTo() +
               " }";
    }
}
