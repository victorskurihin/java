package com.github.intermon;

import com.github.intermon.channel.DBServerWorker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DBServerMain {

    private static final Logger LOG = LogManager.getLogger(DBServerMain.class);
    private static final String HOST = "localhost";
    private static final int MESSAGES_PORT = 5050;

    public static void main(String[] args) {
        String host = (args.length > 0) ? args[0] : HOST;
        int msgSrvPort = (args.length > 1) ? Integer.parseInt(args[1]) : MESSAGES_PORT;

        try (DBServerWorker client = new DBServerWorker(host, msgSrvPort)) {
            LOG.info("DBServerWorker address:{}", client.getAddress());
            client.init();
            client.loop();
        } catch (Exception e) {
            LOG.warn(e);
        }
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
