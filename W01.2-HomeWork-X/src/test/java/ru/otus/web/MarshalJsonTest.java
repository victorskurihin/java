package ru.otus.web;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.*;
import ru.otus.models.*;
import ru.otus.utils.xml.DOMUtil;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.xpath.*;
import java.io.*;
import java.nio.charset.StandardCharsets;

import static junit.framework.TestCase.assertEquals;
import static ru.otus.web.TestData.*;

public class MarshalJsonTest
{
    final InputStream TEST_STREAM = new ByteArrayInputStream(
        TestData.EXAMPLE_STRING.getBytes(StandardCharsets.UTF_8)
    );
    final InputStream EMPTY_EMPLOYEES_STREAM = new ByteArrayInputStream(
        TestData.EMPTY_EMPLOYEES.getBytes(StandardCharsets.UTF_8)
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
    public void testEmptyEmployees() throws JAXBException
    {
        EmpEntities expected = new EmpEntities();
        JAXBContext jc = JAXBContext.newInstance(
            EmpEntities.class, DeptEntity.class, EmpEntity.class, UserEntity.class
        );
        Unmarshaller u = jc.createUnmarshaller();
        EmpEntities list = (EmpEntities) u.unmarshal(EMPTY_EMPLOYEES_STREAM);

        assertEquals(list, expected);
    }

    @Test
    public void testEmployees() throws JAXBException
    {
        EmpEntities expected = TestData.getExpectedEmpEntitiesList();

        JAXBContext jc = JAXBContext.newInstance(
            EmpEntities.class, DeptEntity.class, EmpEntity.class, UserEntity.class
        );
        Unmarshaller u = jc.createUnmarshaller();
        EmpEntities list = (EmpEntities) u.unmarshal(new StringReader(EXAMPLE_STRING));
        assertEquals(expected, list);
    }

    @Test
    public void testMarshalToJson() throws JAXBException
    {
        JAXBContext jc = JAXBContext.newInstance(
                EmpEntities.class, DeptEntity.class, EmpEntity.class, UserEntity.class
        );
        Unmarshaller u = jc.createUnmarshaller();
        EmpEntities list = (EmpEntities) u.unmarshal(new StringReader(EXAMPLE_STRING));

        /* default config  JsonbConfig config = new JsonbConfig().withFormatting(true); */
        Jsonb jsonb = JsonbBuilder.create();
        String result = jsonb.toJson(list);
        assertEquals(expectedJson, result);
    }

    @Test
    public void testUnmarshalFromJson()
    {
        EmpEntities expected = TestData.getExpectedEmpEntitiesList();

        Jsonb jsonb = JsonbBuilder.create();
        EmpEntities list = jsonb.fromJson(expectedJson, EmpEntities.class);
        assertEquals(expected, list);
        String result = list.getEmployees().stream()
            .filter(EntityUtil::isOdd)
            .map(EntityUtil::convertToJson)
            .reduce("", String::concat);
    }
}