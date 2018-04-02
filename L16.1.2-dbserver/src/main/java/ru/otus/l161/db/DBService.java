package ru.otus.l161.db;

import ru.otus.l161.dataset.DataSet;
import ru.otus.l161.dataset.UserDataSet;
import ru.otus.l161.messages.Addressee;

public interface DBService extends Addressee {
    UserDataSet loadByName(String name);

    <T extends DataSet> void save(T dataSet);

    void close() throws Exception;

}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
