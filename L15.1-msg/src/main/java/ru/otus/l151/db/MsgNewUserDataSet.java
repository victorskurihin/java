package ru.otus.l151.db;

import ru.otus.l151.app.DBService;
import ru.otus.l151.app.MsgToDB;
import ru.otus.l151.dataset.UserDataSet;
import ru.otus.l151.messageSystem.Address;
import ru.otus.l151.messageSystem.ControlBlock;

/**
 * Created by tully.
 */
public class MsgNewUserDataSet extends MsgToDB {
    private final UserDataSet userDataSet;
    private final ControlBlock controlBlock;

    public MsgNewUserDataSet(Address from, Address to, ControlBlock control, UserDataSet user) {
        super(from, to);
        userDataSet = user;
        controlBlock = control;
    }

    @Override
    public void exec(DBService dbService) {
        System.err.printf("MsgNewUserDataSet.exec(%s, %s)%n", controlBlock, userDataSet);
        System.err.printf("MsgNewUserDataSet.exec save %s%n", userDataSet);
        dbService.save(userDataSet);
        UserDataSet user = dbService.loadByName(userDataSet.getName());
        System.err.printf("MsgNewUserDataSet.exec send %s%n", user);
        dbService.getMS().sendMessage(
            new MsgUserDataSetAnswer(getTo(), getFrom(), controlBlock, user)
        );
    }

}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
