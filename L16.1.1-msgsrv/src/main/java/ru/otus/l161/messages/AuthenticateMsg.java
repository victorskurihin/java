package ru.otus.l161.messages;

import ru.otus.l161.app.RandomUnsignedInt;
import ru.otus.l161.dataset.UserDataSet;

import java.util.Objects;
import java.util.Random;

public class AuthenticateMsg extends Msg {

    public static final String ID = AuthenticateMsg.class.getSimpleName();

    private final UserDataSet user;
    private final String sessionId;

    public AuthenticateMsg(Address from, String sid, Address to, UserDataSet userDataSet) {
        super(AuthenticateMsg.class, from, to);
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
        if ( ! (o instanceof AuthenticateMsg)) return false;
        AuthenticateMsg singupMsg = (AuthenticateMsg) o;
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

    public AuthenticatedMsg createAnswer(boolean isPositive, String answer) {
        int authId = RandomUnsignedInt.get();

        AuthenticatedMsg msg = new AuthenticatedMsg(
            getTo(), sessionId, getFrom(), user.getName(), isPositive, authId
        );
        msg.setMessage(answer);
        return msg;
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
