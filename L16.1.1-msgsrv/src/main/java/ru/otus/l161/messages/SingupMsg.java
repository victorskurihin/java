package ru.otus.l161.messages;

/*
 * Created by VSkurikhin at spring 2018.
 */

import ru.otus.l161.dataset.UserDataSet;

import java.util.Objects;

public class SingupMsg extends Msg {

    public static final String ID = SingupMsg.class.getSimpleName();

    private final UserDataSet user;
    private final String sessionId;

    public SingupMsg(Address from, String sid, Address to, UserDataSet userDataSet) {
        super(SingupMsg.class, from, to);
        this.user = userDataSet;
        sessionId = sid;
    }

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public String toString() {
        return ID +
               "{ from=" + super.getFrom() +
               ", sid="  + sessionId +
               ", to="   + super.getTo() +
               ", user=" + user +
               " }";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if ( ! (o instanceof SingupMsg)) return false;
        SingupMsg singupMsg = (SingupMsg) o;
        return Objects.equals(user, singupMsg.user) &&
            Objects.equals(sessionId, singupMsg.sessionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, sessionId);
    }

    public UserDataSet getUser() {
        return user;
    }

    public SingedMsg createAnswer(boolean isPositive, String answer) {
        SingedMsg msg = new SingedMsg(getTo(), sessionId, getFrom(), user.getName(), isPositive);
        msg.setMessage(answer);
        return msg;
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
