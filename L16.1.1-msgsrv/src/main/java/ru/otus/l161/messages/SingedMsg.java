package ru.otus.l161.messages;

import java.util.Objects;

public class SingedMsg extends Msg {

    public static final String ID = SingedMsg.class.getSimpleName();

    private final boolean isPositive;
    private final String user;
    private final String sessionId;

    private String message;

    public SingedMsg(
        Address from, String sid, Address to, String username, boolean isPositive
    ) {
        super(SingedMsg.class, from, to);
        sessionId = sid;
        this.isPositive = isPositive;
        this.user = username;
    }

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public String toString() {
        return ID +
               "{ from="     + super.getFrom() +
               ", sid="      + sessionId +
               ", to="       + super.getTo() +
               ", positive=" + isPositive  +
               ", user="     + user +
               ", message="  + message +
               " }";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SingedMsg singedMsg = (SingedMsg) o;
        return isPositive == singedMsg.isPositive &&
                Objects.equals(user, singedMsg.user) &&
                Objects.equals(sessionId, singedMsg.sessionId) &&
                Objects.equals(message, singedMsg.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(isPositive, user, sessionId, message);
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isPositive() {
        return isPositive;
    }

    public String getUser() {
        return user;
    }

    public String getMessage() {
        return message;
    }

    public String getSessionId() {
        return sessionId;
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
