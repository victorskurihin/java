package com.github.intermon.messages;

/*
 * Created by VSkurikhin at Sun Apr 15 17:12:29 MSK 2018.
 */

/**
 * This class has a answer for request a DB service in the messages server.
 */
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

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
