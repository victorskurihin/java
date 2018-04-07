package ru.otus.l161.messages;

/*
 * Created by VSkurikhin at spring 2018.
 */

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

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
