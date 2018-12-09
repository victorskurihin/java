package ru.otus.services;

import javax.websocket.EncodeException;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.nio.ByteBuffer;

public abstract class TestRemoteEndpointBasic implements javax.websocket.RemoteEndpoint.Basic
{
    @Override
    public void sendBinary(ByteBuffer data) throws IOException { }

    @Override
    public void sendText(String partialMessage, boolean isLast) throws IOException { }

    @Override
    public void sendBinary(ByteBuffer partialByte, boolean isLast) throws IOException { }

    @Override
    public OutputStream getSendStream() throws IOException
    {
        return null;
    }

    @Override
    public Writer getSendWriter() throws IOException
    {
        return null;
    }

    @Override
    public void sendObject(Object data) throws IOException, EncodeException { }

    @Override
    public void setBatchingAllowed(boolean allowed) throws IOException { }

    @Override
    public boolean getBatchingAllowed()
    {
        return false;
    }

    @Override
    public void flushBatch() throws IOException { }

    @Override
    public void sendPing(ByteBuffer applicationData) throws IOException, IllegalArgumentException { }

    @Override
    public void sendPong(ByteBuffer applicationData) throws IOException, IllegalArgumentException { }
}
