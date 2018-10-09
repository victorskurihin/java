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
import java.util.stream.Collectors;

@WebServlet(urlPatterns = {"/jsonmarshal", "/jsonmarshal/*"})
public class MarshalJsonServlet extends HttpServlet
{
    private static final String XML_DATA_FILE_LOCATION = "XMLDataFileLocation";
    private static final String JSON_DATA_FILE_LOCATION = "JsonDataFileLocation";
    static final String GET = "get";
    static final String OK = "ok";
    static final String ODD = "odd";

    private InputStream getXMLDataFileInputStream() throws FileNotFoundException, URISyntaxException
    {
        String file = getServletContext().getInitParameter(XML_DATA_FILE_LOCATION);
        return new FileInputStream(Paths.get(new URI(file)).toFile());
    }

    private InputStream getJsonDataFileInputStream() throws FileNotFoundException, URISyntaxException
    {
        String file = getServletContext().getInitParameter(JSON_DATA_FILE_LOCATION);
        return new FileInputStream(Paths.get(new URI(file)).toFile());
    }

    private PrintWriter getJsonDataFilePrintWriter() throws FileNotFoundException, URISyntaxException
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

    private EmpEntitiesList getEmpEntitiesListFromXMLDataFile()
    throws JAXBException, FileNotFoundException, URISyntaxException
    {
        JAXBContext jc = JAXBContext.newInstance(
            EmpEntitiesList.class, DeptEntity.class, EmpEntity.class, UserEntity.class
        );
        Unmarshaller u = jc.createUnmarshaller();

        return (EmpEntitiesList) u.unmarshal(getXMLDataFileInputStream());
    }

    public String converEmpEntityToJson(EmpEntity entity) {
        JsonbConfig config = new JsonbConfig().withFormatting(true);
        Jsonb jsonb = JsonbBuilder.create(config);
        return jsonb.toJson(entity);
    }

    boolean isOdd(EmpEntity e) {
        return ! (e.getId() % 2 == 0);
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
                EmpEntitiesList list = getEmpEntitiesListFromXMLDataFile();
                String json = getJsonFromEmpEntitiesList(list);

                out.println(json);
                try (PrintWriter fout = getJsonDataFilePrintWriter()) {
                    fout.println(json);
                } catch (IOException e2) {
                    e2.printStackTrace();
                }
            } else if (command.equals(ODD)) {
                String jsonEmployees = getJsonEmployeesFromJsonDataFile();
                EmpEntitiesList list = getEmpEntitiesListFromJsonString(jsonEmployees);

                String oddJsonEmployees = list.getEmployees().stream()
                        .filter(this::isOdd)
                        .map(this::converEmpEntityToJson)
                        .reduce("", String::concat);
                out.println("[" + oddJsonEmployees + "\n]\n");
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    private EmpEntitiesList getEmpEntitiesListFromJsonString(String jsonEmployees)
    {
        Jsonb jsonb = JsonbBuilder.create();
        return jsonb.fromJson(jsonEmployees, EmpEntitiesList.class);
    }

    private String getJsonEmployeesFromJsonDataFile() throws FileNotFoundException, URISyntaxException
    {
        return new BufferedReader(new InputStreamReader(getJsonDataFileInputStream()))
                            .lines()
                            .collect(Collectors.joining("\n"));
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
