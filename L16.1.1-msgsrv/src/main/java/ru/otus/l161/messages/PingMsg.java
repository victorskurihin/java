package ru.otus.l161.messages;

/**
 * Created by tully.
 */
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
    public String toString() {
        return ID +
               "{ time=" + time +
               ", from=" + super.getFrom() +
               ", to="   + super.getTo() +
               " }";
    }
}
