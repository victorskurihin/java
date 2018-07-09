package com.github.intermon.messages;

/*
 * Created by VSkurikhin at Sun Apr 15 17:11:20 MSK 2018.
 */

import com.google.gson.Gson;

public class LoginMsg extends Msg {

    public static final String ID = LoginMsg.class.getSimpleName();
    public static final Address BROADCAST = new Address(ID);

    public LoginMsg() {
        super(LoginMsg.class, BROADCAST, BROADCAST);
    }

    private LoginMsg(Address from) {
        super(LoginMsg.class, from, BROADCAST);
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

    public LoginMsg createAnswer(Address from) {
        return new LoginMsg(from);
    }

    public static String createJsonLoginMsg() {
        return new Gson().toJson(new LoginMsg());
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
