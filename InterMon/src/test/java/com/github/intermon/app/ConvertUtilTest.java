package com.github.intermon.app;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.nio.ByteBuffer;

import static org.junit.Assert.*;

public class ConvertUtilTest {
    String testString = "Test";
    ByteBuffer testByteBuffer = ByteBuffer.wrap(new byte[]{84, 101, 115, 116});

    String юникодString = "юникод";
    ByteBuffer юникодByteBuffer = ByteBuffer.wrap(new byte[]{84, 101, 115, 116});

    public ConvertUtilTest() { }

    @Test
    public void extectedExceptionInDumpConstructor() {
    }

    @Test
    public void stringToByteBuffer() {
        ByteBuffer test = ConvertUtil.stringToByteBuffer(testString);
        Assert.assertArrayEquals(testByteBuffer.array(), test.array());

        ByteBuffer юникод = ConvertUtil.stringToByteBuffer(юникодString);

        for (Byte b : юникод.array()) {
            System.out.println("b = " + b);
        }
    }

    @Test
    public void stringToByteBuffer1() {
    }

    @Test
    public void byteBufferToString() {
    }

    @Test
    public void byteBufferToString1() {
    }
}