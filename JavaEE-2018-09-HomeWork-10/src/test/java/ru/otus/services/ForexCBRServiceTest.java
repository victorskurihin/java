package ru.otus.services;

import org.apache.http.entity.StringEntity;
import org.apache.http.protocol.HttpRequestHandler;
import org.apache.http.testserver.HttpServer;
import org.json.XML;
import org.junit.*;
import org.mockito.Mockito;

import javax.servlet.ServletContext;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;

import static org.mockito.Mockito.mock;
import static ru.otus.gwt.shared.Constants.XML_ERROR;
import static ru.otus.services.TestExpectedData.DELAY_TEST;
import static ru.otus.utils.IO.readFile;

public class ForexCBRServiceTest
{
    private static int port;;
    private static ServletContext ctx;
    private static HttpServer server;
    private static HttpRequestHandler myHttpRequestHandler = (request, response, context) -> {
        response.setEntity(new StringEntity(
            "<?xml version='1.0' encoding='UTF-8'?>\n" +
            "<ValCurs Date='17.11.2018' name='Foreign Currency Market'>\n" +
            " <Valute ID='R01035'>\n" +
            "  <NumCode>826</NumCode>\n" +
            "  <CharCode>GBP</CharCode>\n" +
            "  <Nominal>1</Nominal>\n" +
            "  <Name>Фунт стерлингов Соединенного королевства</Name>\n" +
            "  <Value>84,6296</Value>\n" +
            " </Valute>\n" +
            " <Valute ID='R01060'>\n" +
            "  <NumCode>051</NumCode>\n" +
            "  <CharCode>AMD</CharCode>\n" +
            "  <Nominal>100</Nominal>\n" +
            "  <Name>Армянских драмов</Name>\n" +
            "  <Value>13,5928</Value>\n" +
            " </Valute>\n" +
            " <Valute ID='R01235'>\n" +
            "  <NumCode>840</NumCode>\n" +
            "  <CharCode>USD</CharCode>\n" +
            "  <Nominal>1</Nominal>\n" +
            "  <Name>Доллар США</Name>\n" +
            "  <Value>65,9931</Value>\n" +
            " </Valute>\n" +
            "</ValCurs>"
        ));
        response.setHeader("Content-Type", "text/xml; charset=" + ForexCBRService.ENCODING);
    };

    private ForexCBRService service;

    @BeforeClass
    public static void setupServletContext() throws IOException
    {
        File tmp = new File("target/" + ForexCBRServiceTest.class.getName() + ".xml");
        URI uri = tmp.toURI();
        ctx = mock(ServletContext.class);

        Mockito.doReturn(uri.toString())
               .when(ctx)
               .getInitParameter(ForexCBRService.DATA_FILE_LOCATION);

        server = new HttpServer();
        server.registerHandler("/forex*", myHttpRequestHandler);
        server.start();
        port = server.getPort();

        Mockito.doReturn("http://localhost:" + port + "/forex")
                .when(ctx)
                .getInitParameter(ForexCBRService.CBR_DAILY_URL_LOCATION);
    }

    @AfterClass
    public static void serverHttpShutdown()
    {
        server.shutdown();
        server = null;
    }

    @Before
    public void setUp() throws Exception
    {
        service = new ForexCBRService(ctx);
    }

    @After
    public void tearDown() throws Exception
    {
        service = null;
    }

    private String getPathDataFileLocation() throws URISyntaxException
    {
        String uri = ctx.getInitParameter(ForexCBRService.DATA_FILE_LOCATION);
        return Paths.get(new URI(uri)).toString();
    }

    @Test
    public void getDataJTest() throws URISyntaxException, IOException, InterruptedException
    {
        Assert.assertFalse(service.isReady());
        Assert.assertEquals(XML_ERROR, service.getDataXML());

        service.fetchData();

        Thread.sleep(DELAY_TEST);

        Assert.assertTrue(service.isReady());

        String path = getPathDataFileLocation();
        String expectedXML = readFile(path, StandardCharsets.UTF_8);
        String expectedJson = XML.toJSONObject(expectedXML).toString();
        String resultXML = service.getDataXML();
        String resultJson = service.getDataJSON();

        Assert.assertNotNull(resultXML);
        Assert.assertNotNull(resultJson);
        Assert.assertTrue(resultXML.length() > 0);
        Assert.assertTrue(resultJson.length() > 0);
        Assert.assertEquals(expectedXML, resultXML);
        Assert.assertEquals(expectedJson, resultJson);
    }
}