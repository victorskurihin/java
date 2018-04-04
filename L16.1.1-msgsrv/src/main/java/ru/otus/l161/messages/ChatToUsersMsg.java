package ru.otus.l161.messages;

import java.util.Objects;

public class ChatToUsersMsg extends Msg {

    public static final String ID = ChatToUsersMsg.class.getSimpleName();

    private final String user;
    private final String text;
    private final String sessionId;

    public ChatToUsersMsg(Address from, String sid, Address to, String user, String text) {
        super(ChatToUsersMsg.class, from, to);
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
        ChatToUsersMsg that = (ChatToUsersMsg) o;

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
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
