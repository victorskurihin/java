package ru.otus.web;

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
import javax.xml.xpath.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static ru.otus.web.TestData.*;

public class MarshalXMLTest
{
    final InputStream TEST_STREAM = new ByteArrayInputStream(
        EXAMPLE_STRING.getBytes(StandardCharsets.UTF_8)
    );
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
    public void testXPathExp()
    throws XPathExpressionException, TransformerException, IOException, SAXException, ParserConfigurationException
    {
        // Use a Transformer for output
        TransformerFactory tFactory = TransformerFactory.newInstance();
        Transformer transformer = tFactory.newTransformer();

        String expression = "/employees/employee[@salary > (sum(/employees/employee/@salary) div count(/employees/employee/@salary))]";
        XPathExpression expr = xpath.compile(expression);
        Object result = expr.evaluate(document, XPathConstants.NODESET);
        NodeList nodes = (NodeList) result;
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
            assertEquals(String.join(", ", resultList), expectedXML);
            Node copyNode = nodes.item(i).cloneNode(true);
            resultXML.adoptNode(copyNode);
            employees.appendChild(copyNode);
        }
    }
}