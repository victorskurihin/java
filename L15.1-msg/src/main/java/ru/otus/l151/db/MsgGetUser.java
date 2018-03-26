package ru.otus.l151.db;

import ru.otus.l151.app.DBService;
import ru.otus.l151.app.MsgToDB;
import ru.otus.l151.messageSystem.Address;

/**
 * Created by tully.
 */
public class MsgGetUser extends MsgToDB {
    private final String login;
    private final String password;

    public MsgGetUser(Address from, Address to, String login, String password) {
        super(from, to);
        this.login = login;
        this.password = password;
    }

    @Override
    public void exec(DBService dbService) {
        long id = dbService.getUserId(login);
        String pwd = dbService.getPasswordById(id);
        System.err.printf("EXEC password:%s pwd:%s%n", password, pwd);

        if (!pwd.equals(password)) {
            id = -1;
        }
        dbService.getMS().sendMessage(
            new MsgGetUserAnswer(getTo(), getFrom(), id, login, pwd)
        );
    }

}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
