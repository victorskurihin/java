package ru.otus.l161.messages;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ChatToUsersMsgTest {

    private static final String NAME = "NAME";
    private static final String TEXT = "TEXT";
    private static final String PASSWORD = "PASSWORD";
    private static final String ADDRESS = "ADDRESS";
    private final Address from = new Address();
    private final Address to = new Address();
    private final String sid = "0";

    private ChatToUsersMsg chatToUsersMsg;

    @Before
    public void setUp() throws Exception {
        chatToUsersMsg = new ChatToUsersMsg(from, sid, to, NAME, TEXT);
    }

    @After
    public void tearDown() throws Exception {
        chatToUsersMsg = null;
    }

    @Test
    public void getId() {
        Assert.assertEquals(ChatToUsersMsg.ID, chatToUsersMsg.getId());
    }

    @Test
    public void equals() {
        ChatToUsersMsg chatToUsersMsgTest = new ChatToUsersMsg(from, sid, to, NAME, TEXT);
        Assert.assertEquals(chatToUsersMsgTest, chatToUsersMsg);
    }

    @Test
    public void getText() {
        Assert.assertEquals(TEXT, chatToUsersMsg.getText());
    }

    @Test
    public void getUser() {
        Assert.assertEquals(NAME, chatToUsersMsg.getUser());
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
