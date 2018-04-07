package ru.otus.l161.messages;

/*
 * Created by VSkurikhin at spring 2018.
 */

import java.util.Objects;

public class UserToChatMsg extends Msg {

    public static final String ID = UserToChatMsg.class.getSimpleName();

    private final String user;
    private final String text;
    private final String sessionId;

    public UserToChatMsg(Address from, String sid, Address to, String user, String text) {
        super(UserToChatMsg.class, from, to);
        this.user = user;
        this.text = text;
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
               ", text=" + text +
               " }";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserToChatMsg that = (UserToChatMsg) o;

        return Objects.equals(user, that.user) &&
               Objects.equals(text, that.text) &&
               Objects.equals(sessionId, that.sessionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, text, sessionId);
    }

    public String getText() {
        return text;
    }

    public String getUser() {
        return user;
    }

    public ChatToUsersMsg forward(Address to) {
        return new ChatToUsersMsg(getFrom(), sessionId, to, user, getText());
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
