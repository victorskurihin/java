package ru.otus.l161.messages;

import java.util.Objects;

public class UserToChatMsg extends Msg {

    public static final String ID = UserToChatMsg.class.getSimpleName();

    private final String text;
    private final String sessionId;

    public UserToChatMsg(Address from, String sid, Address to, String text) {
        super(UserToChatMsg.class, from, to);
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
               ", text=" + text +
               " }";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserToChatMsg)) return false;
        UserToChatMsg that = (UserToChatMsg) o;
        return Objects.equals(text, that.text) &&
            Objects.equals(sessionId, that.sessionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(text, sessionId);
    }

    public String getText() {
        return text;
    }

    public ChatToUsersMsg forward(Address to) {
        return new ChatToUsersMsg(getFrom(), sessionId, to, getText());
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
