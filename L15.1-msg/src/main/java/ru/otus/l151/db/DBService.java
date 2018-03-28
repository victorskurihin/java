package ru.otus.l151.db;

import ru.otus.l151.dataset.DataSet;
import ru.otus.l151.dataset.UserDataSet;
import ru.otus.l151.messageSystem.Addressee;

public interface DBService extends Addressee {
    void init();

    UserDataSet loadByName(String name);

    <T extends DataSet> void save(T dataSet);

    void close() throws Exception;

}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
