package ru.otus.services;

import org.apache.http.entity.StringEntity;
import org.apache.http.protocol.HttpRequestHandler;
import org.junit.*;
import org.mockito.Mockito;

import javax.servlet.ServletContext;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;

import org.apache.http.testserver.HttpServer;

import static org.mockito.Mockito.mock;
import static ru.otus.services.TestExpectedData.DELAY_TEST;
import static ru.otus.utils.IO.readFile;

public class RBCNewsServiceTest
{
    private static int port;
    private static ServletContext ctx;
    private static HttpServer server;
    private static HttpRequestHandler myHttpRequestHandler = (request, response, context) -> {
        response.setEntity(new StringEntity(
            "<div class='news-feed__item__title'>test1</div>\n" +
            "<div class='news-feed__item__title'>test2</div>\n" +
            "<div class='news-feed__item__title'>test3</div>\n" +
            "<div class='news-feed__item__title'>test4</div>\n" +
            "<div class='news-feed__item__title'>test5</div>\n"
        ));
    };

    private RBCNewsService service;

    @BeforeClass
    public static void setupServletContext() throws IOException
    {
        File tmp = new File("target/" + RBCNewsService.class.getName() + ".xml");
        URI uri = tmp.toURI();
        ctx = mock(ServletContext.class);
        Mockito.doReturn(uri.toString())
               .when(ctx)
               .getInitParameter(RBCNewsService.DATA_FILE_LOCATION);

        server = new HttpServer();
        server.registerHandler("/news*", myHttpRequestHandler);
        server.start();
        port = server.getPort();

        Mockito.doReturn("http://localhost:" + port + "/news")
                .when(ctx)
                .getInitParameter(RBCNewsService.NEWS_URL_LOCATION);
    }

    @Before
    public void setUp() throws Exception
    {
        service = new RBCNewsService(ctx);
    }

    @AfterClass
    public static void serverHttpShutdown()
    {
        server.shutdown();
        server = null;
    }

    @After
    public void tearDown() throws Exception
    {
        service = null;
    }

    private String getPathDataFileLocation() throws URISyntaxException
    {
        String uri = ctx.getInitParameter(RBCNewsService.DATA_FILE_LOCATION);
        return Paths.get(new URI(uri)).toString();
    }

    @Test
    public void getDataTest() throws URISyntaxException, IOException, InterruptedException
    {
        Assert.assertFalse(service.isReady());

        Assert.assertNull(service.getDataXML());

        service.fetchData();

        Thread.sleep(DELAY_TEST);

        Assert.assertTrue(service.isReady());

        String path = getPathDataFileLocation();
        String result = service.getDataJSON();
        String expected = readFile(path, StandardCharsets.UTF_8);

        Assert.assertNotNull(result);
        Assert.assertTrue(result.length() > 0);
        Assert.assertEquals(expected, result);
    }
}