package ru.otus.db;

/*
 * Created by VSkurikhin at winter 2018.
 */

import org.mockito.Mockito;
import ru.otus.models.EntitiesList;

import javax.persistence.EntityManager;
import javax.servlet.ServletContext;
import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.mock;
import static ru.otus.utils.IO.getAbsolutePath;

public interface TestDBConf
{
    String PERSISTENCE_UNIT_NAME = "test-jpa";

    String TEST_RESOURCES = "src" + File.separator
                          + "test" + File.separator
                          + "resources" + File.separator;

    Map<String, String> FILE_LOCATIONS = new HashMap<String , String>() {{
        put(DBConf.TABLE_DEP_DIRECTORY, TEST_RESOURCES + "DeptEntities.xml");
        put(DBConf.TABLE_EMP_REGISTRY,  TEST_RESOURCES + "EmpEntities.xml");
        put(DBConf.TABLE_STATISTIC,     TEST_RESOURCES + "StatisticEntities.xml");
        put(DBConf.TABLE_USER_GROUPS,   TEST_RESOURCES + "UsersgroupEntities.xml");
        put(DBConf.TABLE_USERS,         TEST_RESOURCES + "UserEntities.xml");
    }};

    String FIRST_NAME = "FirstName";
    String SECOND_NAME = "SecondName";
    String SUR_NAME = "SurName";

    static InputStream getInputStream(String relativePath) throws IOException
    {
        String path = getAbsolutePath(relativePath);
        return new FileInputStream(path);
    }

    static void mockResourceAsStream(ServletContext ctx, String fileLocationTest, String fileLocationMock)
    throws IOException
    {
        Mockito.doReturn(fileLocationTest)
                .when(ctx)
                .getInitParameter(fileLocationMock);
        Mockito.doReturn(getInputStream(fileLocationTest))
                .when(ctx)
                .getResourceAsStream(fileLocationTest);
    }

    static <T extends EntitiesList> ImporterSmallXML<T> createImporterXML(ServletContext ctx, String s)
    throws IOException, JAXBException
    {
        String entitiesXMLPath = ctx.getInitParameter(s);
        return new ImporterSmallXML<>(ctx, entitiesXMLPath, DBConf.CLASSES);
    }

    static ServletContext getMockedServletContext() throws IOException
    {
        ServletContext ctx = mock(ServletContext.class);
        for (String table : DBConf.TABLES) {
            String mock = DBConf.FILE_LOCATIONS.get(table);
            TestDBConf.mockResourceAsStream(ctx, FILE_LOCATIONS.get(table), mock);
        }

        return ctx;
    }

    static List<ImporterSmallXML<?>> createImporters() throws IOException, JAXBException
    {
        ServletContext ctx = getMockedServletContext();

        List<ImporterSmallXML<?>> importeres = new ArrayList<>();
        for (String table : DBConf.TABLES) {
            if (FILE_LOCATIONS.get(table) != null)
                importeres.add(createImporterXML(ctx, DBConf.FILE_LOCATIONS.get(table)));
        }

        return importeres;
    }

    static void importTestDB(List<ImporterSmallXML<?>> importeres, EntityManager em) throws JAXBException
    {
        for (ImporterSmallXML<?> importer : importeres) {
            importer.saveEntities(em);
        }
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
