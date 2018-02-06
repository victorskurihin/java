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
