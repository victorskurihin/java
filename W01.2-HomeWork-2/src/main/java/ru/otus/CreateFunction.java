package ru.otus;

/*
 * Created by VSkurikhin at autumn 2018.
 */
/**
 * mvn clean compile dependency:copy-dependencies
 * createFunction.sh or createFunction.bat
 */

import ru.otus.db.ConnectionHelper;
import ru.otus.db.DBConf;
import ru.otus.db.Executor;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class CreateFunction {
    private static final Connection connection = ConnectionHelper.getConnection(
        DBConf.dbName, DBConf.userName, DBConf.password
    );

    public static void createFunction() {
        try {
            connection.setAutoCommit(false);
            Executor exec = new Executor(connection);
            String mygetmaxFunction = "CREATE OR REPLACE FUNCTION mygetmax() " + "" +
                    "RETURNS SETOF TEXT AS 'SELECT sur_name FROM emp_registry ORDER BY salary DESC LIMIT 1;' " +
                    "LANGUAGE sql;";
            exec.execUpdate(mygetmaxFunction);
            connection.commit();
            System.out.println("Ok. The function mygetmax created.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        createFunction();
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
