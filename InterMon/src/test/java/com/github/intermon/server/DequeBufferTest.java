package com.github.intermon.server;

import com.github.intermon.app.ConvertUtil;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.nio.ByteBuffer;
import java.util.Deque;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedDeque;

public class DequeBufferTest {

    // The helper class.
    private class DequeBufferImpl implements DequeBuffer {
        private final Deque<StringBuilder> inQueue = new ConcurrentLinkedDeque<>();

        public int addBuffer(ByteBuffer buffer, int size) {
            return addBuffer(inQueue, buffer, size);
        }

        public List<String> getStringsFromBuffer() {
            return getStringsFromBuffer(inQueue);
        }
    }

    DequeBufferImpl queueBuffer;

    @Before
    public void setUp() throws Exception {
        queueBuffer = new DequeBufferImpl();
    }

    @After
    public void tearDown() throws Exception {
        queueBuffer = null;
    }

    ByteBuffer byteBuffer = ConvertUtil.stringToByteBuffer("test1\n\ntest2\n\ntest3");

    @Test
    public void isNewLine() {
        Assert.assertFalse(queueBuffer.isNewLine(byteBuffer, 0));
        Assert.assertTrue(queueBuffer.isNewLine(byteBuffer, 5));
        Assert.assertTrue(queueBuffer.isNewLine(byteBuffer, 6));
        Assert.assertFalse(queueBuffer.isNewLine(byteBuffer, 7));
        Assert.assertTrue(queueBuffer.isNewLine(byteBuffer, 12));
        Assert.assertTrue(queueBuffer.isNewLine(byteBuffer, 13));
    }

    @Test
    public void readBuffer() {
        queueBuffer.addBuffer(
            byteBuffer,  ConvertUtil.byteByfferToString(byteBuffer).toString().length() - 1
        );
        System.out.println("queueBuffer.inQueue = " + queueBuffer.inQueue);
        System.out.println("queueBuffer.getStringsFromBuffer() = " + queueBuffer.getStringsFromBuffer());
        System.out.println("queueBuffer.inQueue = " + queueBuffer.inQueue);
        queueBuffer.addBuffer(
                byteBuffer,  ConvertUtil.byteByfferToString(byteBuffer).toString().length() - 1
        );
        System.out.println("queueBuffer.inQueue = " + queueBuffer.inQueue);
        System.out.println("queueBuffer.getStringsFromBuffer() = " + queueBuffer.getStringsFromBuffer());
        System.out.println("queueBuffer.inQueue = " + queueBuffer.inQueue);
    }

    @Test
    public void lineExists() {
    }

    @Test
    public void getStringsFromBuffer() {
    }
}