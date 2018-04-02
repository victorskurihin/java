package ru.otus.l161;

import ru.otus.l161.channel.DBServerWorker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DBServerMain {

    private static final Logger LOG = LogManager.getLogger(MsgServerMain.class);
    private static final String HOST = "localhost";
    private static final int MESSAGES_PORT = 5050;

    public static void main(String[] args) {
        try (DBServerWorker client = new DBServerWorker(HOST, MESSAGES_PORT)) {
            client.init();
            client.loop();
        } catch (Exception e) {
            LOG.warn(e);
        }
    }
}
