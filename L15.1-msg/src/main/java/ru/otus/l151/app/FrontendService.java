package ru.otus.l151.app;

import ru.otus.l151.messageSystem.Addressee;
import ru.otus.l151.messageSystem.Message;

/**
 * Created by tully.
 */
public interface FrontendService extends Addressee {
    void init();

    void handleRequest(Message message);

    void idUser(long id, String name);

    void idPassword(long id, String name);
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
