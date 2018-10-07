package ru.otus.web;

import org.dom4j.dom.DOMDocument;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.*;
import org.xml.sax.SAXException;
import ru.otus.xml.DOMUtil;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;

public class DOMXMLServletTest
{
    final String EXAMPLE_STRING = "<?xml version='1.0' encoding='UTF-8' standalone='yes'?>\n" +
            "<employees>\n" +
            "  <employee id='1' salary='10000000'>\n" +
            "    <firstName>firstName1</firstName>\n" +
            "    <secondName>secondName</secondName>\n" +
            "    <surName>surName</surName>\n" +
            "    <department>department</department>\n" +
            "    <city>City</city>\n" +
            "    <job>Job</job>\n" +
            "  </employee>\n" +
            "  <employee id='2' salary='10000'>\n" +
            "    <firstName>firstName2</firstName>\n" +
            "    <secondName>secondName</secondName>\n" +
            "    <surName>surName</surName>\n" +
            "    <department>department</department>\n" +
            "    <city>City</city>\n" +
            "    <job>Job</job>\n" +
            "  </employee>\n" +
            "</employees>";
    final String EMPTY_EMPLOYEES =
            "<?xml version='1.0' encoding='UTF-8' standalone='yes'?>\n" +
            "<employees>\n" +
            "</employees>";
    final InputStream TEST_STREAM = new ByteArrayInputStream(EXAMPLE_STRING.getBytes(StandardCharsets.UTF_8));
    final InputStream EMPTY_EMPLOYEES_STREAM = new ByteArrayInputStream(
        MarshalXMLServlet.EMPTY_EMPLOYEES.getBytes(StandardCharsets.UTF_8)
    );
    Document document;
    XPathFactory xpathfactory;
    XPath xpath;


    @Before
    public void setUp() throws Exception
    {
        document = DOMUtil.getDocument(TEST_STREAM);
        xpathfactory = XPathFactory.newInstance();
        xpath = xpathfactory.newXPath();
    }

    @After
    public void tearDown() throws Exception
    {
        xpath = null;
        xpathfactory = null;
        document = null;
    }

    @Test
    public void doGet()
    throws XPathExpressionException, TransformerException, IOException, SAXException, ParserConfigurationException
    {
        // Use a Transformer for output
        TransformerFactory tFactory = TransformerFactory.newInstance();
        Transformer transformer = tFactory.newTransformer();

        final String expected = "id=\"1\", salary=\"10000000\", firstName1, secondName, surName, department, City, Job";
        String expression = "/employees/employee[@salary > (sum(/employees/employee/@salary) div count(/employees/employee/@salary))]";
        XPathExpression expr = xpath.compile(expression);
        Object result = expr.evaluate(document, XPathConstants.NODESET);
        NodeList nodes = (NodeList) result;
//        Document resultXML = new DOMDocument();
        Document resultXML = DOMUtil.getDocument(EMPTY_EMPLOYEES_STREAM);
        Element employees = resultXML.createElement("employees");

        for (int i = 0; i < nodes.getLength(); ++i) {
            List<String> resultList = new ArrayList<>();
            NamedNodeMap attrs = nodes.item(i).getAttributes();

            for (int j = 0; j < attrs.getLength(); ++j) {
                Attr attr = (Attr) attrs.item(j);
                resultList.add(attr.toString());
            }
            NodeList elements = nodes.item(i).getChildNodes();

            for (int j = 0; j < elements.getLength(); ++j) {
                if (elements.item(j).getChildNodes().item(0) != null) {
                    resultList.add(
                        elements.item(j).getChildNodes().item(0).getNodeValue()
                    );
                }
            }
            assertEquals(String.join(", ", resultList), expected);
            Node copyNode = nodes.item(i).cloneNode(true);
            resultXML.adoptNode(copyNode);
            employees.appendChild(copyNode);
        }
        DOMSource source = new DOMSource(employees);
        StreamResult resultOut = new StreamResult(System.out);
        transformer.transform(source, resultOut);
    }
}