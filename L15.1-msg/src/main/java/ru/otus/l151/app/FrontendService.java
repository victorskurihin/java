package ru.otus.l151.app;

import ru.otus.l151.dataset.UserDataSet;
import ru.otus.l151.messageSystem.Addressee;
import ru.otus.l151.messageSystem.ControlBlock;
import ru.otus.l151.messageSystem.Message;

public interface FrontendService extends Addressee {
    void init();

    void handleRequest(Message message);

    void deliverUserDataSet(ControlBlock control, UserDataSet user);
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
