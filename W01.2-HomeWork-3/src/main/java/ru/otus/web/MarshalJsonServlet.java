package ru.otus.web;

/*
 * Created by VSkurikhin at autumn 2018.
 */

import ru.otus.dataset.DeptEntity;
import ru.otus.dataset.EmpEntitiesList;
import ru.otus.dataset.EmpEntity;
import ru.otus.dataset.UserEntity;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.JsonbConfig;
import javax.persistence.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Paths;

@WebServlet(urlPatterns = {"/jsonmarshal", "/jsonmarshal/*"})
public class MarshalJsonServlet extends HttpServlet
{
    private static final String XML_DATA_FILE_LOCATION = "XMLDataFileLocation";
    private static final String JSON_DATA_FILE_LOCATION = "JsonDataFileLocation";
    static final String GET = "get";
    static final String OK = "ok";

    private InputStream getDataFileInputStream() throws FileNotFoundException, URISyntaxException
    {
        String file = getServletContext().getInitParameter(XML_DATA_FILE_LOCATION);
        return new FileInputStream(Paths.get(new URI(file)).toFile());
    }

    private PrintWriter getDataFilePrintWriter() throws FileNotFoundException, URISyntaxException
    {
        String file = getServletContext().getInitParameter(JSON_DATA_FILE_LOCATION);
        return new PrintWriter(Paths.get(new URI(file)).toFile());
    }

    private String getJsonFromEmpEntitiesList(EmpEntitiesList list)
    {
        JsonbConfig config = new JsonbConfig().withFormatting(true);
        Jsonb jsonb = JsonbBuilder.create(config);

        return jsonb.toJson(list);
    }

    private EmpEntitiesList getEmpEntitiesListFromDataFile()
    throws JAXBException, FileNotFoundException, URISyntaxException
    {
        JAXBContext jc = JAXBContext.newInstance(
            EmpEntitiesList.class, DeptEntity.class, EmpEntity.class, UserEntity.class
        );
        Unmarshaller u = jc.createUnmarshaller();

        return (EmpEntitiesList) u.unmarshal(getDataFileInputStream());
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
    throws IOException, ServletException
    {
        response.setContentType("text/json; charset=UTF-8");
        PrintWriter out = response.getWriter();

        try {
            String command = ServletUtil.retrieveCommand(request);
            if (command == null) {
                command = GET;
            }
            if (command.equals(OK)) {
                ServletUtil.okXML(out);
            } else if (command.equals(GET)) {
                EmpEntitiesList list = getEmpEntitiesListFromDataFile();
                String json = getJsonFromEmpEntitiesList(list);

                out.println(json);
                try (PrintWriter fout = getDataFilePrintWriter()) {
                    fout.println(json);
                } catch (IOException e2) {
                    e2.printStackTrace();
                }
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
