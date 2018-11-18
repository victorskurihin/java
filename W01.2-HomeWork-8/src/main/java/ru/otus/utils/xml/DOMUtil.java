package ru.otus.utils.xml;

/*
 * Created at autumn 2018.
 */

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class DOMUtil
{

    public static Document getDocument(InputStream stream)
    throws ParserConfigurationException, IOException, SAXException
    {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        return factory.newDocumentBuilder().parse(stream);
    }

    public static Document getDocument(String fileLocation)
    throws ParserConfigurationException, IOException, SAXException
    {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        return factory.newDocumentBuilder().parse(fileLocation);
    }

    public static final void saveDocument(Document doc, String destination)
    throws TransformerConfigurationException, TransformerException
    {
        TransformerFactory factory = TransformerFactory.newInstance();
        Transformer xform = factory.newTransformer();
        xform.setOutputProperty(OutputKeys.INDENT, "yes");
        xform.setOutputProperty(OutputKeys.ENCODING, StandardCharsets.UTF_8.toString());
        xform.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
        xform.transform(new DOMSource(doc), new StreamResult(destination));

    }

    public static boolean isEmpty(String str){
        return str != null && str.trim().length() == 0;
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
