package ru.otus.l151.db;

import ru.otus.l151.app.DBService;
import ru.otus.l151.app.MsgToDB;
import ru.otus.l151.messageSystem.Address;

/**
 * Created by tully.
 */
public class MsgNewUser extends MsgToDB {
    private final String login;
    private final String password;

    public MsgNewUser(Address from, Address to, String login, String password) {
        super(from, to);
        this.login = login;
        this.password = password;
    }

    @Override
    public void exec(DBService dbService) {
        long id = dbService.newUser(login, password);
        // TODO dbService.getMS().sendMessage(new MsgGetUserIdAnswer(getTo(), getFrom(), login, id));
        dbService.getMS().sendMessage(new MsgNewUserAnswer(getTo(), getFrom(), id, login, password));
    }

}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
