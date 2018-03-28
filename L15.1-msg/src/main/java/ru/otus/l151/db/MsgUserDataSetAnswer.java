package ru.otus.l151.db;

import ru.otus.l151.front.FrontendService;
import ru.otus.l151.app.MsgToFrontend;
import ru.otus.l151.dataset.UserDataSet;
import ru.otus.l151.messageSystem.Address;
import ru.otus.l151.messageSystem.ControlBlock;

public class MsgUserDataSetAnswer extends MsgToFrontend {
    private final ControlBlock controlBlock;
    private final UserDataSet userDataSet;

    public MsgUserDataSetAnswer(Address from, Address to, ControlBlock control, UserDataSet user) {
        super(from, to);
        controlBlock = control;
        userDataSet = user;
    }

    @Override
    public void exec(FrontendService frontendService) {
        frontendService.deliverUserDataSet(controlBlock, userDataSet);
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
