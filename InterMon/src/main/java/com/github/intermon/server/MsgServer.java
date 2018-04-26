package com.github.intermon.server;

/*
 * Created by VSkurikhin at Wed, Apr 25, 2018 10:06:55 AM
 */

import com.github.intermon.channel.MsgJson;
import com.github.intermon.messages.Address;
import com.github.intermon.messages.LoginMsg;
import com.github.intermon.messages.Msg;
import com.github.intermon.messages.RegisterOfMsg;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * The class implements a non blocking socket message server.
 */
public class MsgServer implements MsgServerMBean {
    private static final Logger LOG = LogManager.getLogger(MsgServer.class);

    private static final int THREADS_NUMBER = 1;
    private static final int PORT = 5050;
    private static final int ECHO_DELAY = 100;
    private static final int CAPACITY = 256;
    private static final String MESSAGES_SEPARATOR = "\n\n";

    private final ExecutorService executor;
    private final Map<String, ChannelMessages> mapChannelMessages;
    private final Map<Address, SocketChannel> mapAddressRecipients;
    private final Map<Address, Integer> mapDbServers;

    private Selector selector;

    // The helper class.
    private class ChannelMessages {
        private final SocketChannel channel;
        private final List<String> messages = new ArrayList<>();
        private final Set<Address> addresses = new ConcurrentSkipListSet<>();

        private ChannelMessages(SocketChannel channel) {
            this.channel = channel;
        }

        public Set<Address> getAddresses() {
            return addresses;
        }

        public boolean addAddress(Address address) {
            return addresses.add(address);
        }
    }

    /**
     * Default constructor.
     */
    public MsgServer() {
        executor = Executors.newFixedThreadPool(THREADS_NUMBER);
        mapChannelMessages = new ConcurrentHashMap<>();
        mapAddressRecipients = new ConcurrentHashMap<>();
        mapDbServers = new ConcurrentHashMap<>();
    }

    private void sendMessagesToChannel(ChannelMessages channelMessages) {
        if (channelMessages.channel.isConnected()) {
            channelMessages.messages.forEach(message -> {
                try {
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

    // the first loop
    @SuppressWarnings("InfiniteLoopStatement")
    private Object echo() throws InterruptedException {
        while (true) {
            for (Map.Entry<String, ChannelMessages> entry : mapChannelMessages.entrySet()) {
                LOG.info("Echoing message to: {}", entry.getKey()); // TODO debug
                sendMessagesToChannel(entry.getValue());
            }
            Thread.sleep(ECHO_DELAY);
        }
    }

    private Msg getMsgFromJson(String result) {
        if (result.isEmpty()) {
            return null;
        }
        try {
            return MsgJson.get(result);
        } catch (ParseException e) {
            LOG.error("Unable to parse: {}, exeption: {}", result, e);
        } catch (ClassNotFoundException e) {
            LOG.error("Class not fount for: {}, exeption: {}", result, e);
        }
        return null;
    }

    private boolean registerAddress(SocketChannel channel, Address from) {
        if (LoginMsg.BROADCAST != from) {
            mapAddressRecipients.put(from, channel);
            LOG.info("Ok address: {} from channel: {} registered.", from, channel);
            return true;
        }
        return false;
    }

    private boolean registerAddressDBServers(Address from) {
        if (LoginMsg.BROADCAST != from) {
            mapDbServers.put(from, 0);
            LOG.info("Register the DBService: {}", from);
            return true;
        }
        return false;
    }

    private boolean handleMsg(SocketChannel channel, Msg msg) {
        if (null == msg) {
            LOG.error("Can't handle null!");
            return false;
        }
        switch (msg.getId()) {
            case RegisterOfMsg.LOGIN_MSG:
                return registerAddress(channel, msg.getFrom());
            case RegisterOfMsg.REGISTERDBSERVER_MSG:
                return registerAddressDBServers(msg.getFrom());
        }
        return false;
        // sendMessagesToChannel.get(socketAddress.toString()).messages.add(result);
    }

    private void removeRemoteAddresses(SocketChannel channel) throws IOException {
        String remoteAddress = channel.getRemoteAddress().toString();
        ChannelMessages cm = mapChannelMessages.remove(remoteAddress);
        mapAddressRecipients.keySet().removeAll(cm.getAddresses());
        mapDbServers.keySet().removeAll(cm.getAddresses());
        //noinspection UnusedAssignment
        cm = null;
    }

    private void readChannel(SelectionKey key, SocketChannel channel) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(CAPACITY);
        int read = channel.read(buffer);

        if (read != -1) {
            String result = new String(buffer.array()).trim();
            SocketAddress socketAddress = channel.getRemoteAddress();
            LOG.info("Message received: {} from: {}", result, socketAddress); // TODO debug
            if ( ! handleMsg(channel, getMsgFromJson(result)) ) {
                LOG.error("Can't handle the message: {}", result);
            }
        } else {
            key.cancel();
            removeRemoteAddresses(channel);
            LOG.info("Connection closed, key canceled");
        }
    }

    private void nonBockingAccept(SocketChannel channel) throws IOException {
        // non blocking accept
        String remoteAddress = channel.getRemoteAddress().toString();
        LOG.info("Connection Accepted: {}", remoteAddress);

        channel.configureBlocking(false);
        channel.register(selector, SelectionKey.OP_READ);

        mapChannelMessages.put(remoteAddress, new ChannelMessages(channel));
        // ChannelMessages channelMessages = mapChannelMessages.get(remoteAddress);
        // channelMessages.messages.add(MsgJson.createJsonLoginMsg());
        // sendMessagesToChannel(channelMessages);
    }

    // The second loop.
    @SuppressWarnings("InfiniteLoopStatement")
    private void selectorLoop(ServerSocketChannel serverSocketChannel) throws IOException {
        while (true) {
            // where will be happen block
            selector.select();
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

    /**
     * The starting method launches two loops.
     *
     * The first loop  iterates by channel messages  and for each entry,
     * if channel connected and have stored messages, send this messages
     * to a client connected to the own channel.
     *
     * The second loop waits event from selector, if come  the message event
     * the message from event will be stored or if come about new connection
     * and ready to accept a new socket connection, this connection  will be
     * registered.
     *
     * @throws Exception
     */
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
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
