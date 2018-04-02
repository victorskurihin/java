package ru.otus.l161.messages;

/**
 * Created by tully.
 */
public class PingMsg extends Msg {

    public static final String ID = PingMsg.class.getName();

    private final long time;

    public PingMsg(Address from, Address to) {
        super(PingMsg.class, from, to);
        time = System.currentTimeMillis();
    }

    public long getTime() {
        return time;
    }

    @Override
    public String toString() {
        return ID +
               " time=" + time +
               ", from=" + super.getFrom() +
               ", to=" + super.getTo() +
               " }";
    }
}
