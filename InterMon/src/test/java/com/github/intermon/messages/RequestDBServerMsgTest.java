package com.github.intermon.messages;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class RequestDBServerMsgTest {

    private final Address address = new Address();

    private RequestDBServerMsg requestDBServerMsg;

    @Before
    public void setUp() {
        requestDBServerMsg = new RequestDBServerMsg(address);
    }

    @After
    public void tearDown() {
        requestDBServerMsg = null;
    }

    @Test
    public void getId() {
        Assert.assertEquals(RequestDBServerMsg.ID, requestDBServerMsg.getId());
    }

    @Test
    public void createAnswer() {
        Address dbServiceTest = new Address();
        RequestDBServerMsg answer = requestDBServerMsg.createAnswer(dbServiceTest);
        Assert.assertEquals(answer.getTo(), requestDBServerMsg.getFrom());
        Assert.assertEquals(answer.getFrom(), dbServiceTest);
    }

    @Test
    public void toStringCoverageTest() {
        RequestDBServerMsg requestDBServerMsg1 = new RequestDBServerMsg(address);
        RequestDBServerMsg requestDBServerMsg2 = new RequestDBServerMsg(address);
        Assert.assertEquals(requestDBServerMsg1.toString(), requestDBServerMsg2.toString());
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
