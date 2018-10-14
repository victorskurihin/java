package ru.otus.web;

/*
 * Created by VSkurikhin at autumn 2018.
 */

import ru.otus.dataset.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

@WebServlet("/cbrforex")
public class CBRForexServlet extends HttpServlet
{
    static final String OK = "ok";

    public void doGet(HttpServletRequest request, HttpServletResponse response)
    throws IOException, ServletException
    {
        response.setContentType("text/json; charset=UTF-8");
        PrintWriter out = response.getWriter();

        try {
            String command = ServletUtil.retrieveCommand(request);
            if (command == null) {
                command = OK;
            }

            if (command.equals(OK)) {
                ServletUtil.okXML(out);
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
