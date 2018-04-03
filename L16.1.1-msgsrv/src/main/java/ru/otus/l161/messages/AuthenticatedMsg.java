package ru.otus.l161.messages;

import ru.otus.l161.dataset.UserDataSet;

import java.util.Objects;

public class AuthenticatedMsg extends Msg {

    public static final String ID = AuthenticatedMsg.class.getSimpleName();

    private final String user;
    private final boolean isPositive;
    private final String sessionId;
    private final int auth;

    private String message;

    AuthenticatedMsg(
        Address from, String sid, Address to, String user, boolean isPositive, int auth
    ) {
        super(AuthenticatedMsg.class, from, to);
        this.user = user;
        this.sessionId = sid;
        this.auth = auth;
        this.isPositive = isPositive;

    }

    public AuthenticatedMsg(Address from, Address to, String user, int auth) {
        this(from, null, to, user, true, auth);
    }

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public String toString() {
        return ID +
               "{ from="     + super.getFrom() +
               ", to="       + super.getTo() +
               ", positive=" + isPositive  +
               ", user="     + user +
               ", auth="     + auth +
               ", message="  + message +
               " }";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AuthenticatedMsg)) return false;
        AuthenticatedMsg that = (AuthenticatedMsg) o;
        return isPositive == that.isPositive &&
            auth == that.auth &&
            Objects.equals(user, that.user) &&
            Objects.equals(sessionId, that.sessionId) &&
            Objects.equals(message, that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, isPositive, sessionId, auth, message);
    }

    public boolean isPositive() {
        return isPositive;
    }

    public String getUser() {
        return user;
    }

    public String getSessionId() {
        return sessionId;
    }

    public int getAuth() {
        return auth;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
