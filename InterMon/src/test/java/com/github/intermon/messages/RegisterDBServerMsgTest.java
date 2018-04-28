package com.github.intermon.messages;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class RegisterDBServerMsgTest {

    private RequestDBServerMsg requestDBServerMsg;

    @Before
    public void setUp() throws Exception {
        requestDBServerMsg = new RequestDBServerMsg(new Address());
    }

    @After
    public void tearDown() throws Exception {
        requestDBServerMsg = null;
    }

    @Test
    public void getId() {
        Assert.assertEquals(RequestDBServerMsg.ID, requestDBServerMsg.getId());
    }

    @Test
    public void getFrom(){
        Assert.assertEquals(new Address(
                RequestDBServerMsg.class.getSimpleName()
            ),
            requestDBServerMsg.getTo()
        );
        Assert.assertEquals(RequestDBServerMsg.ID, requestDBServerMsg.getTo().getId());
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
