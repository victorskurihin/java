package ru.otus.db;

/*
 * Created by VSkurikhin at winter 2018.
 */

import ru.otus.models.*;

import javax.servlet.ServletContext;
import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public interface DBConf
{
    String userName = "dbuser";
    String dbName = "db";
    String password = "password";

    String TABLE_USERS = "users";
    String TABLE_DEP_DIRECTORY = "dep_directory";
    String TABLE_EMP_REGISTRY = "emp_registry";
    String TABLE_USER_GROUPS = "user_groups";
    String TABLE_STATISTIC = "statistic";

    String[] TABLES = new String[] {
            TABLE_USERS,
            TABLE_DEP_DIRECTORY,
            TABLE_EMP_REGISTRY,
            TABLE_USER_GROUPS,
            TABLE_STATISTIC,
    };

    String[] ORDER_OF_DELETE_TABLES = new String[] {
            TABLE_EMP_REGISTRY,
            TABLE_DEP_DIRECTORY,
            TABLE_STATISTIC,
            TABLE_USER_GROUPS,
            TABLE_USERS,
    };

    Map<String, String> FILE_LOCATIONS = new HashMap<String , String>() {{
        put(TABLE_DEP_DIRECTORY, "DeptEntitiesXMLDataFileLocation");
        put(TABLE_EMP_REGISTRY,  "EmpEntitiesXMLDataFileLocation");
        put(TABLE_STATISTIC,     "StatisticEntitiesXMLDataFileLocation");
        put(TABLE_USER_GROUPS,   "UsersgroupEntitiesXMLDataFileLocation");
        put(TABLE_USERS,         "UserEntitiesXMLDataFileLocation");
    }};

    Class<?>[] CLASSES = new Class<?>[] {
            DeptEntity.class,
            DeptEntitiesList.class,
            EmpEntity.class,
            EmpEntitiesList.class,
            GroupEntity.class,
            GroupEntitiesList.class,
            StatisticEntity.class,
            StatisticEntitiesList.class,
            UserEntity.class,
            UserEntitiesList.class,
    };

    static <T extends EntitiesList> ImporterSmallXML<T> createImporterXML(ServletContext sc, String s)
    throws IOException, JAXBException
    {
        String entitiesXMLPath = sc.getInitParameter(s);
        return new ImporterSmallXML<>(sc, entitiesXMLPath, CLASSES);
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
