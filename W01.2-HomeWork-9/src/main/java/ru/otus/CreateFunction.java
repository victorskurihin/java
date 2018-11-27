/*
 * Copyright (c) Victor N. Skurikhin 27.11.18 22:21.
 * CreateFunction.java
 * $Id$
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */

package ru.otus;

/**
 * mvn clean compile dependency:copy-dependencies
 * createFunction.sh or createFunction.bat
 */

import ru.otus.db.ConnectionHelper;
import ru.otus.db.DBConf;
import ru.otus.db.Executor;

import java.sql.Connection;

public class CreateFunction
{
    private static final Connection connection = ConnectionHelper.getConnection(
        DBConf.dbName, DBConf.userName, DBConf.password
    );

    public static void createFunction()
    {
        try {
            connection.setAutoCommit(false);
            Executor exec = new Executor(connection);
            String mygetmaxFunction =
                "CREATE OR REPLACE FUNCTION insert_db_statistic(\n" +
                "  marker VARCHAR(255), pagename VARCHAR(255), ipaddr VARCHAR(255), uagent VARCHAR(255),\n" +
                "  ctime TIMESTAMP, stime TIMESTAMP, sid VARCHAR(255), uid BIGINT, pid BIGINT)\n" +
                "RETURNS BIGINT LANGUAGE plpgsql AS\n" +
                "$function$\n" +
                "DECLARE\n" +
                "  id BIGINT := nextval('statistic_id_seq');\n" +
                "BEGIN\n" +
                "  IF uid < 0 THEN\n" +
                "    uid := NULL;\n" +
                "  END IF;\n" +
                "  INSERT INTO\n" +
                "    statistic(id,name_marker,jsp_page_name,ip_address,user_agent,client_time,server_time,session_id,user_id,prev_id)\n" +
                "    VALUES (id, marker, pagename, ipaddr, uagent, ctime, stime, sid, uid, pid);\n" +
                "  RETURN id;\n" +
                "END;\n" +
                "$function$\n" +
                ";\n";
            exec.execUpdate(mygetmaxFunction);
            connection.commit();
            System.out.println("Ok. The function insert_db_statistic created.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


    public static void main(String[] args)
    {
        createFunction();
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
