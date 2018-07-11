package com.github.intermon.app;

import org.junit.Assert;
import org.junit.Test;

import java.nio.ByteBuffer;

import static org.junit.Assert.*;

public class ConvertUtilTest {
    String testString = "Test";
    ByteBuffer testByteBuffer = ByteBuffer.wrap(new byte[]{84, 101, 115, 116});

    String юникодString = "юникод";
    ByteBuffer юникодByteBuffer = ByteBuffer.wrap(new byte[]{84, 101, 115, 116});

    @Test
    public void stringToByteBuffer() {
        ByteBuffer test = ConvertUtil.stringToByteBuffer(testString);
        Assert.assertArrayEquals(testByteBuffer.array(), test.array());

        ByteBuffer юникод = ConvertUtil.stringToByteBuffer(юникодString);
    }
}