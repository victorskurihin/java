package ru.otus.web;

/*
 * Created by VSkurikhin at autumn 2018.
 */

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.stream.Collectors;

@WebServlet("/cbrforex")
public class CBRForexProxyServlet extends HttpServlet
{
    String getXMLCBRdaily() {
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpGet httpGet = new HttpGet("http://www.cbr.ru/scripts/XML_daily.asp");

            try (CloseableHttpResponse response = client.execute(httpGet)) {
                return new BufferedReader(
                       new InputStreamReader(
                           response.getEntity().getContent(), "windows-1251"
                )).lines().collect(Collectors.joining());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        final String encoding1 = "encoding=\"windows-1251\"";
        final String encoding2 = "encoding=\"UTF-8\"";
        response.setContentType("text/xml; charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.println(getXMLCBRdaily().replaceFirst(encoding1, encoding2));
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
