package ru.otus.l091;

import java.sql.Connection;

public abstract class DBServiceConnection implements DBService {
    private final Connection connection;

    protected DBServiceConnection() {
        connection = ConnectionHelper.getConnection("vnsk", "vnsk");
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
