package ru.otus.l151.app;

import ru.otus.l151.messageSystem.Addressee;

/**
 * Created by tully.
 */
public interface DBService extends Addressee {
    void init();

    long getUserId(String name);

    String getPasswordById(long id);

    void close() throws Exception;

    long newUser(String login, String password);
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
