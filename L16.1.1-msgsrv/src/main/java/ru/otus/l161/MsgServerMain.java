package ru.otus.l161;

import ru.otus.l161.runner.ProcessRunnerImpl;
import ru.otus.l161.server.MsgServer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.io.File;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by tully.
 */
public class MsgServerMain {
    private static final char FS = File.separatorChar;
    private static final Logger LOG = LogManager.getLogger(MsgServerMain.class);

    private static final String DBSERVER_DIR = "L16.1.2-dbserver";
    private static final String DBSERVER_JAR = "dbserver.jar";
    private static final String FRONTEND_DIR = "L16.1.3-frontend";
    private static final String FRONTEND_JAR = "frontend.jar";
    private static final String MESSAGE_SERVER_HOST = "localhost";
    private static final String MESSAGE_SERVER_PORT = Integer.toString(MsgServer.PORT);
    private static final int CLIENT_START_DELAY_SEC = 5;

    public static void main(String[] args) throws Exception {
        new MsgServerMain().start();
    }

    private static String javaExeJar() {
        final String JAVA_HOME = System.getProperty("java.home");
        final File BIN = new File(JAVA_HOME, "bin");
        File exe = new File(BIN, "java");

        if (!exe.exists()) {
            // We might be on Windows, which needs an exe extension
            exe = new File(BIN, "java.exe");
        }
        if (exe.exists()) {
            return exe.getAbsolutePath();
        }

        return null;
    }

    private String clientCommand(String dir, String jar) {
        String cmd = String.format(
            "%s -jar ..%c%s%ctarget%c%s %s %s",
            javaExeJar(), FS, dir, FS, FS, jar, MESSAGE_SERVER_HOST, MESSAGE_SERVER_PORT
        );

        return cmd;
    }

    private String frontendCommand(String dir, String jar, String httpPort) {
        return String.format("%s %s", clientCommand(dir, jar), httpPort);
    }

    private void start() throws Exception {
        List<ScheduledExecutorService> serviceExecutors = new ArrayList<>(4);

        serviceExecutors.add(Executors.newSingleThreadScheduledExecutor());
        String cmd = clientCommand(DBSERVER_DIR, DBSERVER_JAR);
        startClient(serviceExecutors.get(0), cmd);
        LOG.warn("cmd = {}", cmd);

        serviceExecutors.add(Executors.newSingleThreadScheduledExecutor());
        cmd = clientCommand(DBSERVER_DIR, DBSERVER_JAR);
        startClient(serviceExecutors.get(1), cmd);
        LOG.warn("cmd = {}", cmd);

        serviceExecutors.add(Executors.newSingleThreadScheduledExecutor());
        cmd = frontendCommand(FRONTEND_DIR, FRONTEND_JAR,"8090");
        startClient(serviceExecutors.get(2), cmd);
        LOG.warn("cmd = {}", cmd);

        serviceExecutors.add(Executors.newSingleThreadScheduledExecutor());
        cmd = frontendCommand(FRONTEND_DIR, FRONTEND_JAR,"8091");
        startClient(serviceExecutors.get(3), cmd);
        LOG.warn("cmd = {}", cmd);

        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
        ObjectName name = new ObjectName("ru.otus:type=Server");
        MsgServer server = new MsgServer();
        mbs.registerMBean(server, name);

        server.start();

        for (ScheduledExecutorService serviceExecutor : serviceExecutors) {
            serviceExecutor.shutdown();
        }
    }

    private void startClient(ScheduledExecutorService executorService, String cmd) {
        executorService.schedule(() -> {
            try {
                new ProcessRunnerImpl().start(cmd);
            } catch (IOException e) {
                LOG.info(e);
            }
        }, CLIENT_START_DELAY_SEC, TimeUnit.SECONDS);
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
