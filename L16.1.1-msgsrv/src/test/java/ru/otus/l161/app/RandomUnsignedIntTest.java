package ru.otus.l161.app;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class RandomUnsignedIntTest {

    @Test
    public void get() {
        Assert.assertTrue(RandomUnsignedInt.get() > -1);
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
