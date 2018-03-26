package ru.otus.l151.db;

import ru.otus.l151.app.FrontendService;
import ru.otus.l151.app.MsgToFrontend;
import ru.otus.l151.messageSystem.Address;

/**
 * Created by tully.
 */
public class MsgNewUserAnswer extends MsgToFrontend {
    private final long id;
    private final String name;
    private final String password;

    public MsgNewUserAnswer(Address from, Address to, long id, String name, String password) {
        super(from, to);
        this.id = id;
        this.name = name;
        this.password = password;
    }

    @Override
    public void exec(FrontendService frontendService) {
        frontendService.idUser(id, name);
        frontendService.idPassword(id, password);
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
