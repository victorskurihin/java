package ru.otus.web;

/*
 * Created by VSkurikhin at autumn 2018.
 */

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONObject;
import org.json.XML;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.stream.Collectors;

@WebServlet("/cbrforex")
public class CBRForexServlet extends HttpServlet
{
    String getJsonCBRdaily() {
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpGet httpGet = new HttpGet("http://www.cbr.ru/scripts/XML_daily.asp");

            try (CloseableHttpResponse response = client.execute(httpGet)) {
                final String content = new BufferedReader(
                        new InputStreamReader(response.getEntity().getContent(), "windows-1251")
                ).lines().collect(Collectors.joining());
                JSONObject jsonObject = XML.toJSONObject(content);

                return jsonObject.toString();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
    throws IOException, ServletException
    {
        response.setContentType("text/json; charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.println(getJsonCBRdaily());
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
