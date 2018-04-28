package com.github.intermon.server;

import com.github.intermon.app.ConvertUtil;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.ByteBuffer;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedDeque;

public class DequeBufferTest {

    private static final Logger LOG = LogManager.getLogger(DequeBufferTest.class);

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

    private DequeBufferImpl queueBuffer;
    private final ByteBuffer buffers[] = {
        ConvertUtil.stringToByteBuffer(""),
        ConvertUtil.stringToByteBuffer("t"),
        ConvertUtil.stringToByteBuffer("\n"),
        ConvertUtil.stringToByteBuffer("te"),
        ConvertUtil.stringToByteBuffer("\n\n"),
        ConvertUtil.stringToByteBuffer("test1\n"),
        ConvertUtil.stringToByteBuffer("test1\n\n"),
        ConvertUtil.stringToByteBuffer("test1\n\nt"),
        ConvertUtil.stringToByteBuffer("test1\n\nte"),
        ConvertUtil.stringToByteBuffer("test1\n\ntest2\n"),
        ConvertUtil.stringToByteBuffer("test1\n\ntest2\n\n"),
        ConvertUtil.stringToByteBuffer("test1\n\ntest2\n\nt"),
        ConvertUtil.stringToByteBuffer("test1\n\ntest2\n\nte"),
        ConvertUtil.stringToByteBuffer("test1\n\ntest2\n\ntest3")
    };
    private int bufferLengths[] = new int[buffers.length];
    private Deque<StringBuilder> expected = new ArrayDeque<>();

    @Before
    public void setUp() throws Exception {
        queueBuffer = new DequeBufferImpl();
        for (int i = 0; i < buffers.length; ++i) {
            bufferLengths[i] = ConvertUtil.byteByfferToString(buffers[i]).length();
            LOG.debug("bufferLengths[{}] = {}", i, bufferLengths[i]);
        }
        expected = new ArrayDeque<>();
    }

    @After
    public void tearDown() throws Exception {
        expected = null;
        queueBuffer = null;
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void indexOutOfBoundsException0() {
        Assert.assertFalse(queueBuffer.isNewLine(buffers[0], 0));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void indexOutOfBoundsException1() {
        Assert.assertFalse(queueBuffer.isNewLine(buffers[1], 1));
    }

    @Test
    public void isNewLineFalse1() {
        Assert.assertFalse(queueBuffer.isNewLine(buffers[1], 0));
    }

    @Test
    public void isNewLineTrue2() {
        Assert.assertTrue(queueBuffer.isNewLine(buffers[2], 0));
    }

    @Test
    public void isNewLineFalse3() {
        Assert.assertFalse(queueBuffer.isNewLine(buffers[3], 1));
    }

    @Test
    public void isNewLineTrue4() {
        Assert.assertTrue(queueBuffer.isNewLine(buffers[4], 0));
        Assert.assertTrue(queueBuffer.isNewLine(buffers[4], 1));
    }

    @Test
    public void isNewLineTrue5() {
        int i; for (i = 0; i < 5; ++i)
            Assert.assertFalse(queueBuffer.isNewLine(buffers[5], i));
        Assert.assertTrue(queueBuffer.isNewLine(buffers[5], i));
    }

    @Test
    public void readBuffer0() {
        queueBuffer.addBuffer(buffers[0], bufferLengths[0]);
        Assert.assertArrayEquals(expected.toArray(), queueBuffer.inQueue.toArray());

    }

    private void assertEquals() {
        Assert.assertEquals(expected.stream()
                .map(StringBuilder::toString).reduce("", String::concat),
            queueBuffer.inQueue.stream()
                .map(StringBuilder::toString).reduce("", String::concat)
        );
    }

    private void logDebugQueueBuffer() {
        if (LOG.isDebugEnabled())
            LOG.debug("queueBuffer.inQueue = {}", queueBuffer.inQueue.stream()
                .map(StringBuilder::toString) .reduce("", String::concat)
            );
    }

    private void logDebugExpected() {
        if (LOG.isDebugEnabled())
            LOG.debug("expected = {}", expected.stream()
                .map(StringBuilder::toString).reduce("", String::concat)
            );
    }

    private void logDebugOutBuffer(int i) {
        if (LOG.isDebugEnabled())
            LOG.debug(String.format("length(buffers[%d] = %s) = %d",
                i, ConvertUtil.byteByfferToString(buffers[i]), bufferLengths[i]
            ));
    }

    @Test
    public void readBuffer1() throws Exception {
        int i = 1;
        logDebugOutBuffer(i);
        queueBuffer.addBuffer(buffers[i], bufferLengths[i]);
        logDebugQueueBuffer();
        expected.add(new StringBuilder(ConvertUtil.byteByfferToString(buffers[i])));
        logDebugExpected();
        assertEquals();
    }

    @Test
    public void readBuffer() throws Exception {
        for (int i = 1; i < buffers.length; ++i) {
            setUp();
            logDebugOutBuffer(i);
            queueBuffer.addBuffer(buffers[i], bufferLengths[i]);
            logDebugQueueBuffer();
            expected.add(new StringBuilder(ConvertUtil.byteByfferToString(buffers[i])));
            logDebugExpected();
            assertEquals();
            tearDown();
        }
    }

    @Test
    public void readBuffer2() throws Exception {
        queueBuffer.addBuffer(buffers[3], bufferLengths[3]);
        queueBuffer.addBuffer(buffers[1], bufferLengths[1]);
        queueBuffer.addBuffer(buffers[3], bufferLengths[3]);
        Assert.assertEquals("tette",
            queueBuffer.inQueue.stream()
                .map(StringBuilder::toString).reduce("", String::concat)
        );
    }

    @Test
    public void lineExistsFalse() throws Exception {
        int i = 5;
        queueBuffer.addBuffer(buffers[i], bufferLengths[i]);
        Assert.assertFalse(queueBuffer.lineExists(queueBuffer.inQueue));
        for (i = 0; i < 4; ++i) {
            setUp();
            queueBuffer.addBuffer(buffers[i], bufferLengths[i]);
            Assert.assertFalse(queueBuffer.lineExists(queueBuffer.inQueue));
            tearDown();
        }
    }

    @Test
    public void lineExistsTrue() throws Exception {
        int i = 4;
        queueBuffer.addBuffer(buffers[i], bufferLengths[i]);
        Assert.assertTrue(queueBuffer.lineExists(queueBuffer.inQueue));
        for (i = 6; i < buffers.length; ++i) {
            setUp();
            queueBuffer.addBuffer(buffers[i], bufferLengths[i]);
            Assert.assertTrue(queueBuffer.lineExists(queueBuffer.inQueue));
            tearDown();
        }
    }

    @Test
    public void getStringsFromBufferZero() throws Exception {
        int i = 5;
        queueBuffer.addBuffer(buffers[i], bufferLengths[i]);
        List<String> list = queueBuffer.getStringsFromBuffer();
        Assert.assertEquals(0, list.size());
        for (i = 0; i < 4; ++i) {
            setUp();
            queueBuffer.addBuffer(buffers[i], bufferLengths[i]);
            list = queueBuffer.getStringsFromBuffer();
            Assert.assertEquals(0, list.size());
            tearDown();
        }
    }

    @Test
    public void getStringsFromBuffer1() throws Exception {
        int i = 4;
        queueBuffer.addBuffer(buffers[i], bufferLengths[i]);
        List<String> list = queueBuffer.getStringsFromBuffer();
        Assert.assertEquals(1, list.size());
    }

    @Test
    public void getStringsFromBuffer() {
    }
}