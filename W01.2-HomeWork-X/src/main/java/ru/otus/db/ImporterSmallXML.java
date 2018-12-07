/*
 * ImporterSmallXML.java
 * This file was last modified at 2018.12.03 20:05 by Victor N. Skurikhin.
 * $Id$
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */

package ru.otus.db;

import ru.otus.models.*;

import javax.persistence.EntityManager;
import javax.servlet.ServletContext;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.*;

import static ru.otus.utils.IO.readInputStream;

public class ImporterSmallXML<T extends Entities>
{
    private String xmlObjects;
    private JAXBContext jc;

    public ImporterSmallXML(ServletContext sc, String filename, Class<?>... classes)
    throws IOException, JAXBException
    {
        if (null == classes || classes.length < 1)
            throw new RuntimeException("classes has'n classes");
        jc = JAXBContext.newInstance(classes
        );

        try (InputStream is = sc.getResourceAsStream(filename)) {
            xmlObjects = readInputStream(is, "UTF-8");
        }
        catch (IOException e) {
            throw e;
        }
    }

    public <E extends DataSet> void saveEntities(EntityManager em) throws JAXBException
    {
        Unmarshaller unmarshaller = jc.createUnmarshaller();
        //noinspection unchecked
        T entities = (T) unmarshaller.unmarshal(new StringReader(xmlObjects));

        for (Object e : entities.asList()) {
            //noinspection unchecked
            E entity = (E) e;
            em.merge(entity);
        }
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
