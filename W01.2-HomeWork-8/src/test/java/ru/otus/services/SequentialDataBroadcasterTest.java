package ru.otus.services;

import javax.websocket.Session;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.IOException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static ru.otus.services.TestExpectedData.JSON_TEST;

public class SequentialDataBroadcasterTest
{
    private SequentialDataBroadcaster router;
    private static String sendedText;

    static class Basic extends TestRemoteEndpointBasic
    {
        @Override
        public void sendText(String text) throws IOException
        {
            sendedText = text;
        }
    }

    @Before
    public void setUp() throws Exception
    {
        router = new SequentialDataBroadcaster();
    }

    @After
    public void tearDown() throws Exception
    {
        router.close();
        router = null;
    }

    @Test
    public void updatePeriod()
    {
        int expected = 13;
        router.setUpdatePeriod(expected);
        Assert.assertEquals(expected, router.getUpdatePeriod());
    }

    @Test
    public void registerDataOrigin() throws InterruptedException
    {
        Assert.assertFalse(router.isRunning());

        TestDataOrigin origin = new TestDataOrigin();
        router.registerDataOrigin("TEST_DATA_ORIGIN", origin);
        router.start();
        Thread.sleep(50);
        Assert.assertTrue(router.isRunning());
        Assert.assertTrue(origin.isReady());

        router.unregisterDataOrigin("TEST_DATA_ORIGIN");
        router.shutdown();
        Assert.assertFalse(router.isRunning());
    }

    private void registerDataUpdater(Session session)
    throws InterruptedException
    {
        DataUpdater updater = new SimpleQueuedUpdater();
        TestDataOrigin origin = new TestDataOrigin();
        router.registerDataOrigin("TEST_DATA_ORIGIN", origin);
        router.registerDataUpdater(session, updater);
    }

    @Test
    public void registerDataUpdater() throws InterruptedException
    {
        Session session = mock(Session.class);
        Mockito.doReturn(new Basic())
               .when(session)
               .getBasicRemote();
        when(session.isOpen()).thenReturn(true);

        registerDataUpdater(session);
        router.start();
        Thread.sleep(100);
        Assert.assertEquals(JSON_TEST, sendedText);

        router.unregisterDataUpdater(session);
        router.start();
        sendedText = null;
        Thread.sleep(100);
        Assert.assertNull(sendedText);
        router.shutdown();
    }
}