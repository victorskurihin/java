package com.github.intermon.db;

import com.github.intermon.dataset.DataSet;
import com.github.intermon.dataset.UserDataSet;
import com.github.intermon.messages.Addressee;

public interface DBService extends Addressee {
    UserDataSet loadByName(String name);

    <T extends DataSet> void save(T dataSet);

    void close() throws Exception;

}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
