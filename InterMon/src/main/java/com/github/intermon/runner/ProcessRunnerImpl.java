package com.github.intermon.runner;

/*
 * Created by VSkurikhin at Wed, Apr 25, 2018 13:09:37 AM
 */

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.github.intermon.app.ProcessRunner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 *.
 */
public class ProcessRunnerImpl implements ProcessRunner {

    private class StreamListener extends Thread {
        private final Logger LOG = LogManager.getLogger(StreamListener.class);
        private final InputStream is;
        private final String type;

        private StreamListener(InputStream is, String type) {
            this.is = is;
            this.type = type;
        }

        @Override
        public void run() {
            try (InputStreamReader isr = new InputStreamReader(is)) {
                BufferedReader br = new BufferedReader(isr);
                String line;
                while ((line = br.readLine()) != null) {
                    out.append(type).append('>').append(line).append('\n');
                    LOG.info(line);
                }
            } catch (IOException e) {
                LOG.error(e);
            }
        }
    }

    private final StringBuffer out = new StringBuffer();
    private Process process;

    public void start(String command) throws IOException {
        process = runProcess(command);
    }


    public void stop() {
        process.destroy();
    }

    public String getOutput() {
        return out.toString();
    }

    private Process runProcess(String command) throws IOException {
        ProcessBuilder pb = new ProcessBuilder(command.split(" "));
        pb.redirectErrorStream(true);
        Process child = pb.start();

        Thread closeChildThread = new Thread() {
            public void run() {
                if (child.isAlive()) {
                    child.destroy();
                }
            }
        };

        Runtime.getRuntime().addShutdownHook(closeChildThread);

        StreamListener output = new StreamListener(child.getInputStream(), "OUTPUT");
        output.start();

        return child;
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
