package ru.otus.l161.messages;

import ru.otus.l161.dataset.UserDataSet;

import java.util.Objects;

public class SingupMsg extends Msg {

    public static final String ID = SingupMsg.class.getName();

    public class Outcome {
        public final String id = Outcome.class.getSimpleName();

        private final boolean isPositive;
        private final String message;

        public Outcome(boolean isPositive, String message) {
            this.isPositive = isPositive;
            this.message = message;
        }

        @Override
        public String toString() {
            return id +
                "{ isPositive=" + isPositive +
                ", message='" + message + '\'' +
                '}';
        }

        public boolean isPositive() {
            return isPositive;
        }

        public String getMessage() {
            return message;
        }
    };

    private final UserDataSet user;
    private final String sessionId;

    private Outcome outcome;

    public SingupMsg(Address from, String sid, Address to, UserDataSet userDataSet) {
        super(SingupMsg.class, from, to);
        this.user = userDataSet;
        sessionId = sid;
    }

    @Override
    public String toString() {
        return ID +
               "{ from=" + super.getFrom() +
               ", sid="  + sessionId +
               ", to="   + super.getTo() +
               ", user=" + user +
               ", outcome=" + outcome +
               " }";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if ( ! (o instanceof SingupMsg)) return false;
        SingupMsg singupMsg = (SingupMsg) o;
        return Objects.equals(user, singupMsg.user) &&
            Objects.equals(sessionId, singupMsg.sessionId) &&
            Objects.equals(outcome, singupMsg.outcome);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, sessionId, outcome);
    }

    public void setAnswer(boolean isPositive, String message) {
        outcome = new Outcome(isPositive, message);
    }

    public boolean isPositive() {
        return outcome.isPositive();
    }

    public String getMessage() {
        return outcome.getMessage();
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
