package ru.otus.l161.messages;

/**
 * Created by tully.
 */
public class PingMsg extends Msg {
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
        return "PingMsg{" +
               " time=" + time +
               ", from=" + super.getFrom() +
               ", to=" + super.getTo() +
               " }";
    }
}
