package ru.otus.services;

import org.junit.*;
import org.mockito.Mockito;

import javax.websocket.Session;

import java.io.IOException;
import java.util.HashMap;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static ru.otus.services.TestExpectedData.JSON_TEST;

public class SimpleQueuedUpdaterTest
{
    private static Session session;
    private static String sendedText;

    private SimpleQueuedUpdater updater;

    static class Basic extends TestRemoteEndpointBasic
    {
        @Override
        public void sendText(String text) throws IOException
        {
            sendedText = text;
        }
    }

    @BeforeClass
    public static void setupServletContext()
    {
        session = mock(Session.class);
        Mockito.doReturn(new Basic())
               .when(session)
               .getBasicRemote();
    }

    @Before
    public void setUp() throws Exception
    {
        sendedText = null;
        updater = new SimpleQueuedUpdater();
    }

    @After
    public void tearDown() throws Exception
    {
        updater = null;
    }

    private void setUpUpdater()
    {
        updater.setSession(session);
        updater.setSources(new HashMap<String, DataOrigin>(){{
            put("TEST_DATA_ORIGIN", new TestDataOrigin());
        }});
    }

    @Test
    public void handleOnDataUpdate()
    {
        updater.handleOnDataUpdate();
        Assert.assertNull(sendedText);

        setUpUpdater();
        updater.handleOnDataUpdate();
        Assert.assertNull(sendedText);

        when(session.isOpen()).thenReturn(true);

        setUpUpdater();
        Assert.assertNull(sendedText);
        Assert.assertEquals(session, updater.getSession());

        updater.handleOnDataUpdate();
        Assert.assertEquals(JSON_TEST, sendedText);
    }
}