package com.github.intermon.app;

/*
 * Created by VSkurikhin at Sun Apr 15 13:20:51 MSK 2018.
 */

import java.io.IOException;

public interface ProcessRunner {
    void start(String command) throws IOException;

    void stop();

    String getOutput();
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
