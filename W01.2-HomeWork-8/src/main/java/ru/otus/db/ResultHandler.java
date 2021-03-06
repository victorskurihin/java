package ru.otus.db;

/*
 * Created by VSkurikhin at winter 2018.
 */

import java.sql.ResultSet;
import java.sql.SQLException;

@FunctionalInterface
public interface ResultHandler
{
    void handle(ResultSet result) throws SQLException;
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
