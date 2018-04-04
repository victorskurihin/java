package ru.otus.l161.messages;

import java.util.Objects;

public class ChatToUsersMsg extends Msg {

    public static final String ID = ChatToUsersMsg.class.getSimpleName();

    private final String text;
    private final String sessionId;

    public ChatToUsersMsg(Address from, String sid, Address to, String text) {
        super(ChatToUsersMsg.class, from, to);
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
        if (!(o instanceof ChatToUsersMsg)) return false;
        ChatToUsersMsg that = (ChatToUsersMsg) o;
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
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
