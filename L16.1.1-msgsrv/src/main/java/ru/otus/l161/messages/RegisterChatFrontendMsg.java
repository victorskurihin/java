package ru.otus.l161.messages;

public class RegisterChatFrontendMsg extends Msg {

    public static final String ID = RegisterChatFrontendMsg.class.getSimpleName();

    public RegisterChatFrontendMsg(Address from) {
        super(RegisterChatFrontendMsg.class, from, new Address(ID));
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

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
