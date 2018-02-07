package ru.otus.l091;

import java.sql.Connection;

public abstract class DBServiceConnection implements DBService {
    private final String name = "vnsk";
    private final Connection connection;

    protected DBServiceConnection() {
        connection = ConnectionHelper.getConnection(name, name);
    }

    protected Connection getConnection() {
        return connection;
    }

    @Override
    public void close() throws Exception {
        connection.close();
        System.out.println("Connection closed. Bye!");
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
