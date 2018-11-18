package ru.otus.servlets;

import ru.otus.models.StatisticEntity;
import ru.otus.services.DbService;
import ru.otus.services.StatisticService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import static ru.otus.gwt.shared.Constants.*;
import static ru.otus.utils.Servlet.getBaseURL;

@WebServlet("/" + REQUEST_VISITS_STAT_JSP)
public class StatisticJSPServlet extends HttpServlet
{
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
    {
        StatisticService statService = (StatisticService) getServletContext().getAttribute(STAT_SERVICE);
        request.setAttribute(ATTR_BASE_URL, getBaseURL(request));
        request.setAttribute(ATTR_STAT_ENABLED, statService.isCollectionEnabled());
        try {
            DbService dbService = (DbService) getServletContext().getAttribute(DB_SERVICE);
            List<StatisticEntity> elems = statService.getAllVisitsStatElements(dbService);
            request.setAttribute(ATTR_STAT_ELEMEMTS, elems);

            request.getRequestDispatcher("/WEB-INF/classes/ftl/visits_stat.ftl").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace(); // TODO LOGGER
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
