package ru.otus.l161;

import ru.otus.l161.runner.ProcessRunnerImpl;
import ru.otus.l161.server.MsgServer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by tully.
 */
public class MsgServerMain {
    private static final Logger LOG = LogManager.getLogger(MsgServerMain.class);

    private static final String CLIENT_START_COMMAND = "java -jar ../L16.1.2-client/target/client.jar";
    private static final int CLIENT_START_DELAY_SEC = 5;

    public static void main(String[] args) throws Exception {
        new MsgServerMain().start();
    }

    private void start() throws Exception {
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        //startClient(executorService);

        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
        ObjectName name = new ObjectName("ru.otus:type=Server");
        MsgServer server = new MsgServer();
        mbs.registerMBean(server, name);

        server.start();

        executorService.shutdown();
    }

    private void startClient(ScheduledExecutorService executorService) {
        executorService.schedule(() -> {
            try {
                new ProcessRunnerImpl().start(CLIENT_START_COMMAND);
            } catch (IOException e) {
                LOG.info(e);
            }
        }, CLIENT_START_DELAY_SEC, TimeUnit.SECONDS);
    }

}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
