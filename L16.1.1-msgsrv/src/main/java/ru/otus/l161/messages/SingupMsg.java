package ru.otus.l161.messages;

public class SingupMsg extends Msg {

    private String user;
    private String pass;
    private String reason = null;

    public SingupMsg(Address from, Address to) {
        super(SingupMsg.class, from, to);
    }

//    public SingupMsg(String username, String password, Session session) {
//        super(SingupMsg.class, from, to);
//        user = username;
//        pass = password;
//    }

    @Override
    public String toString() {
        return "SingupMsg{" +
               ", from=" + super.getFrom() +
               ", to=" + super.getTo() +
               ", user=" + user +
               ", pass=" + pass +
               ", reason=" + reason +
               " }";
    }
}
