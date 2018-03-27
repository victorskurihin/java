package ru.otus.l151.db;

import ru.otus.l151.app.DBService;
import ru.otus.l151.app.MsgToDB;
import ru.otus.l151.dataset.UserDataSet;
import ru.otus.l151.messageSystem.Address;
import ru.otus.l151.messageSystem.ControlBlock;

/**
 * Created by tully.
 */
public class MsgGetUserDataSet extends MsgToDB {
    private final String login;
    private final ControlBlock controlBlock;

    public MsgGetUserDataSet(Address from, Address to, ControlBlock control, String login) {
        super(from, to);
        this.login = login;
        controlBlock = control;
    }

    @Override
    public void exec(DBService dbService) {
        System.err.printf("MsgGetUserDataSet.exec(%s, %s)%n", controlBlock, login);
        UserDataSet user = dbService.loadByName(login);
        System.err.printf("MsgGetUserDataSet.exec send %s%n", user);
        dbService.getMS().sendMessage(
            new MsgUserDataSetAnswer(getTo(), getFrom(), controlBlock, user)
        );
    }

}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
