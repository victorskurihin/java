package com.github.intermon.app;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class ConvertUtil {
    public static final Charset UTF8 = StandardCharsets.UTF_8;

    public static ByteBuffer stringToByteBuffer (String msg, Charset charset) {
        return ByteBuffer.wrap(msg.getBytes(charset));
    }
    public static ByteBuffer stringToByteBuffer (String msg){
        return Charset.forName("UTF-8").encode(msg);
    }

    public static String byteBufferToString(ByteBuffer buffer, Charset charset){
        byte[] bytes;
        if(buffer.hasArray()) {
            bytes = buffer.array();
        } else {
            bytes = new byte[buffer.remaining()];
            buffer.get(bytes);
        }
        return new String(bytes, charset);
    }

    public static String byteBufferToString(ByteBuffer buffer){
        return byteBufferToString(buffer, UTF8);
    }

    private ConvertUtil() { }
}
