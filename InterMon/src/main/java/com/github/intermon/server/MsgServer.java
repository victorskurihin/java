package com.github.intermon.server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MsgServer implements MsgServerMBean {
    private static final Logger LOG = LogManager.getLogger(MsgServer.class);

    private static final int THREADS_NUMBER = 1;
    private static final int PORT = 5050;
    private static final int ECHO_DELAY = 100;
    private static final int CAPACITY = 256;
    private static final String MESSAGES_SEPARATOR = "\n\n";

    private final ExecutorService executor;
    private final Map<String, ChannelMessages> channelMessages;

    private Selector selector;

    public MsgServer() {
        executor = Executors.newFixedThreadPool(THREADS_NUMBER);
        channelMessages = new ConcurrentHashMap<>();
    }

    private void readChannel(SelectionKey key, SocketChannel channel) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(CAPACITY);
        int read = channel.read(buffer);

        if (read != -1) {
            String result = new String(buffer.array()).trim();
            LOG.info("Message received: {} from: {}", result, channel.getRemoteAddress());
            channelMessages.get(channel.getRemoteAddress().toString()).messages.add(result);
        } else {
            key.cancel();
            String remoteAddress = channel.getRemoteAddress().toString();
            channelMessages.remove(remoteAddress);
            LOG.info("Connection closed, key canceled");
        }
    }

    private void nonBockingAccept(SocketChannel channel) throws IOException {
        // non blocking accept
        String remoteAddress = channel.getRemoteAddress().toString();
        LOG.info("Connection Accepted: {}", remoteAddress);

        channel.configureBlocking(false);
        channel.register(selector, SelectionKey.OP_READ);

        channelMessages.put(remoteAddress, new ChannelMessages(channel));
    }

    @SuppressWarnings("InfiniteLoopStatement")
    private void selectorLoop(ServerSocketChannel serverSocketChannel) throws IOException {
        while (true) {
            selector.select();//blocks
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                try {
                    if (key.isAcceptable()) {
                        nonBockingAccept(serverSocketChannel.accept());
                    } else if (key.isReadable()) {
                        readChannel(key, (SocketChannel) key.channel());
                    }
                } catch (IOException e) {
                    LOG.error(e);
                } finally {
                    iterator.remove();
                }
            }
        }
    }

    public void start() throws Exception {
        executor.submit(this::echo);

        try (ServerSocketChannel serverSocketChannel = ServerSocketChannel.open()) {
            serverSocketChannel.bind(new InetSocketAddress("localhost", PORT));

            serverSocketChannel.configureBlocking(false); //non blocking mode
            int ops = SelectionKey.OP_ACCEPT;
            selector = Selector.open();
            serverSocketChannel.register(selector, ops, null);

            LOG.info("Started on port: " + PORT);

            selectorLoop(serverSocketChannel);
        }
    }

    private void channelMessages(String key, ChannelMessages channelMessages) {
        if (channelMessages.channel.isConnected()) {
            channelMessages.messages.forEach(message -> {
                try {
                    LOG.info("Echoing message to: {}", key);
                    ByteBuffer buffer = ByteBuffer.allocate(CAPACITY);
                    buffer.put(message.getBytes());
                    buffer.put(MESSAGES_SEPARATOR.getBytes());
                    buffer.flip();
                    while (buffer.hasRemaining()) {
                        channelMessages.channel.write(buffer);
                    }
                } catch (IOException e) {
                    LOG.error(e);
                }
            });
            channelMessages.messages.clear();
        }
    }

    @SuppressWarnings("InfiniteLoopStatement")
    private Object echo() throws InterruptedException {
        while (true) {
            for (Map.Entry<String, ChannelMessages> entry : channelMessages.entrySet()) {
                channelMessages(entry.getKey(), entry.getValue());
            }
            Thread.sleep(ECHO_DELAY);
        }
    }

    @Override
    public boolean getRunning() {
        return true;
    }

    @Override
    public void setRunning(boolean running) {
        if (!running) {
            executor.shutdown();
            LOG.info("Bye.");
        }
    }

    private class ChannelMessages {
        private final SocketChannel channel;
        private final List<String> messages = new ArrayList<>();

        private ChannelMessages(SocketChannel channel) {
            this.channel = channel;
        }
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
