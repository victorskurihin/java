package ru.otus.web;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
// import ru.otus.persistent.ConfigDAO;
// import ru.otus.persistent.ConfigDataSet;
import ru.otus.persistent.*;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Stateless
public class SimpleServlet extends HttpServlet {
    private static final long serialVersionUID = -4751096228274971485L;
    private final static Logger LOG =  LogManager.getLogger(SimpleServlet.class);

    @EJB
    private ConfigDAO configDAO;

    @Override
    protected void doGet(HttpServletRequest reqest, HttpServletResponse response)
        throws ServletException, IOException {

        LOG.info("Servlet got request {}", reqest);
        String configString = "configDAO is null";

        if (configDAO != null) {
            List<ConfigDataSet> config = configDAO.getAll();
            if (config != null) {
                LOG.info("Servlet do DAO request getAll {}", config.toArray());
                configString = config.toString();
            } else {
                LOG.info("Servlet do DAO request getAll");
                configString = "config is null";

            }
        }

        response.getWriter().println("Config: " + configString);
    }

    @Override
    public void init() throws ServletException {
        LOG.info("Servlet {} has started", this.getServletName());
    }

    @Override
    public void destroy() {
        LOG.info("Servlet {} has stopped", this.getServletName());
    }
}
