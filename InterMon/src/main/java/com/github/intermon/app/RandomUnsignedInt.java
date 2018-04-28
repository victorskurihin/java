package com.github.intermon.app;

import java.util.Random;

public class RandomUnsignedInt {
    public static int get() {
        Random random = new Random();
        return (int) (random.nextLong() & 0x7fffffffL);
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
