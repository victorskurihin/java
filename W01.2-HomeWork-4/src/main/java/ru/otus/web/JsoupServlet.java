package ru.otus.web;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.stream.IntStream;

@WebServlet("/jsouprbc")
public class JsoupServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Document doc;
        response.setContentType("text/json; charset=UTF-8");
        try (PrintWriter pw = response.getWriter()) {
            doc = Jsoup.connect("https://www.rbc.ru/").get();
            pw.print("{\"NewsArray\": [");
            IntStream.range(1, 5).forEach(i -> {
                pw.print(
                    "{\"Id\": \"news" + i + "\", \"Text\": \"" +
                    doc.getElementsByClass("news-feed__item__title").get(i).text() + "\"}, "
                );
            });
            pw.println(" {\"end\": \"\"} ]}");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
