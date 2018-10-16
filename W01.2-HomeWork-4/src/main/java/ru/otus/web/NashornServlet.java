package ru.otus.web;

import jdk.nashorn.api.scripting.NashornScriptEngineFactory;

import javax.script.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/js")
public class NashornServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    {
        NashornScriptEngineFactory factory = new NashornScriptEngineFactory();
        ScriptEngine engine = factory.getScriptEngine(new String[] { "-scripting" });
        String programForExecution = request.getParameter("command");
        try (PrintWriter pw = response.getWriter()) {
            Invocable invocable = (Invocable) engine;
            engine.eval(programForExecution);
            pw.println(invocable.invokeFunction("main"));
        } catch (ScriptException | NoSuchMethodException | IOException e) {
            e.printStackTrace();
        }
    }
}
