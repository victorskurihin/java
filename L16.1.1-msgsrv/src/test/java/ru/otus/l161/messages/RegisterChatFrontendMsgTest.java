package ru.otus.l161.messages;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class RegisterChatFrontendMsgTest {

    private RegisterChatFrontendMsg registerChatFrontendMsg;

    @Before
    public void setUp() throws Exception {
        registerChatFrontendMsg = new RegisterChatFrontendMsg(new Address());
    }

    @After
    public void tearDown() throws Exception {
        registerChatFrontendMsg = null;
    }

    @Test
    public void getId() {
        Assert.assertEquals(RegisterChatFrontendMsg.ID, registerChatFrontendMsg.getId());
    }

    @Test
    public void getFrom(){
        Assert.assertEquals(new Address(
                RegisterChatFrontendMsg.class.getSimpleName()
            ),
            registerChatFrontendMsg.getTo()
        );
        Assert.assertEquals(
            RegisterChatFrontendMsg.ID,
            registerChatFrontendMsg.getTo().getId()
        );
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
