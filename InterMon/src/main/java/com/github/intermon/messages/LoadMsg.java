package com.github.intermon.messages;

/*
 * Created by VSkurikhin at Sun Apr 15 17:11:20 MSK 2018.
 */

public class LoadMsg extends Msg {

    public static final String ID = LoadMsg.class.getSimpleName();

    private final double load;

    public LoadMsg(Address from, Address to, double load) {
        super(LoadMsg.class, from, to);
        this.load = load;
    }

    @Override
    public String getId() {
        return ID;
    }

    public double getLoad() {
        return load;
    }

    @Override
    public String toString() {
        return ID +
               "{ from=" + super.getFrom() +
               ", to="   + super.getTo() +
               ", load=" + load +
               " }";
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
