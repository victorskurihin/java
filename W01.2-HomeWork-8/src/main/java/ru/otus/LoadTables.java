package ru.otus;

/*
 * Created by VSkurikhin at autumn 2018.
 */
/**
 * mvn clean compile dependency:copy-dependencies
 * loadTables.sh or loadTables.bat
 */

import ru.otus.db.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class LoadTables
{
    private final Connection connection = ConnectionHelper.getConnection(
        DBConf.dbName, DBConf.userName, DBConf.password
    );
    private String tableName;
    private String column = "";

    public LoadTables(String tableName) {
        this.tableName = tableName;
    }

    public void readInputStream(Scanner inputStream) throws SQLException
    {
        Executor exec = new Executor(connection);

        while (inputStream.hasNextLine()) {
            String line = inputStream.nextLine();
            String[] fields = line.split(",");

            //noinspection MismatchedQueryAndUpdateOfStringBuilder
            StringBuilder sb = new StringBuilder();
            sb.append("INSERT INTO ").append(tableName);

            if ( fields.length > 0 && "id".equals(fields[0])) {
                StringBuilder sbc = new StringBuilder();
                column = sbc.append(" ( ")
                    .append(String.join(", ", fields))
                    .append(" ) ").toString();
                continue;
            }

            if (fields.length >= 2) {
                sb.append(column).append(" VALUES ( ")
                    .append(String.join(", ", fields))
                    .append(" );");
                System.out.println("Exec: " + sb);
                exec.execUpdate(sb.toString());
            }
            else {
                System.err.println("Invalid record: " + line);
            }
        }
    }

    public void loadTables()
    {
        try {
            connection.setAutoCommit(false);
            readInputStream(new Scanner(System.in));
            connection.commit();
            System.out.println("Ok. The table: " + tableName + " loaded.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args)
    {
        if (args.length != 1) {
            System.out.println("For work this loader need one argument with table name.");
        } else if (args[0] == null) {
            System.out.println("The args is null!");
        } else {
            (new LoadTables(args[0])).loadTables();
        }
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
