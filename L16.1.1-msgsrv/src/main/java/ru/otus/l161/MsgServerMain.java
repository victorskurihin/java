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

    private static final String DBSERVER_START_COMMAND = "java -jar ../L16.1.2-dbserver/target/dbserver.jar";
    private static final String FRONTEND_START_COMMAND = "java -jar ../L16.1.3-frontend/target/frontend.jar";
    private static final int CLIENT_START_DELAY_SEC = 5;

    public static void main(String[] args) throws Exception {
        new MsgServerMain().start();
    }

    private void start() throws Exception {
        ScheduledExecutorService executorService1 = Executors.newSingleThreadScheduledExecutor();
        ScheduledExecutorService executorService2 = Executors.newSingleThreadScheduledExecutor();
        ScheduledExecutorService executorService3 = Executors.newSingleThreadScheduledExecutor();
        ScheduledExecutorService executorService4 = Executors.newSingleThreadScheduledExecutor();

        new Thread(() -> {
            try {
                Thread.currentThread().sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            startClient(executorService1, DBSERVER_START_COMMAND + " localhost " + MsgServer.PORT);
            // startClient(executorService2, DBSERVER_START_COMMAND + " localhost " + MsgServer.PORT);
            startClient(executorService3, FRONTEND_START_COMMAND + " localhost " + MsgServer.PORT + " 8090");
            // startClient(executorService4, FRONTEND_START_COMMAND + " localhost " + MsgServer.PORT + " 8091");
        }).start();

        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
        ObjectName name = new ObjectName("ru.otus:type=Server");
        MsgServer server = new MsgServer();
        mbs.registerMBean(server, name);

        server.start();

        executorService4.shutdown();
        executorService3.shutdown();
        executorService2.shutdown();
        executorService1.shutdown();
    }

    private void startClient(ScheduledExecutorService executorService, String command) {
        executorService.schedule(() -> {
            try {
                new ProcessRunnerImpl().start(command);
            } catch (IOException e) {
                LOG.info(e);
            }
        }, CLIENT_START_DELAY_SEC, TimeUnit.SECONDS);
    }

}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
