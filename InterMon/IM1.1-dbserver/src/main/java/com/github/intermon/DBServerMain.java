package com.github.intermon;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by VSkurikhin at winter 2018.
 */

public class DBServerMain {
    private static final int DELAY = 100;
    private static final Logger LOG = LogManager.getLogger(Main.class);

    public static void main(String[] args) throws InterruptedException {
        for (int i = 1; i < 100; ++i) {
            LOG.info("Ok.");
            Thread.sleep(DELAY);
        }
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
