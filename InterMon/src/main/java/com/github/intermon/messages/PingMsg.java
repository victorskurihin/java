package com.github.intermon.messages;

/*
 * Created by VSkurikhin at Sun Apr 15 17:11:20 MSK 2018.
 */

import java.util.Objects;

public class PingMsg extends Msg {

    public static final String ID = PingMsg.class.getSimpleName();

    private final long time;

    public PingMsg(Address from, Address to) {
        super(PingMsg.class, from, to);
        time = System.currentTimeMillis();
    }

    @Override
    public String getId() {
        return ID;
    }

    public long getTime() {
        return time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PingMsg)) return false;
        PingMsg pingMsg = (PingMsg) o;
        return getTime() == pingMsg.getTime()
            && getFrom().equals(pingMsg.getFrom());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTime(), getFrom(), getTo());
    }

    @Override
    public String toString() {
        return ID +
               "{ time=" + time +
               ", from=" + super.getFrom() +
               ", to="   + super.getTo() +
               " }";
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
