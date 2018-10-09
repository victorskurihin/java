package ru.otus.web;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.*;
import ru.otus.dataset.*;
import ru.otus.xml.DOMUtil;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.xpath.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

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
        EmpEntitiesList expected = new EmpEntitiesList();
        JAXBContext jc = JAXBContext.newInstance(
            EmpEntitiesList.class, DeptEntity.class, EmpEntity.class, UserEntity.class
        );
        Unmarshaller u = jc.createUnmarshaller();
        EmpEntitiesList list = (EmpEntitiesList) u.unmarshal(EMPTY_EMPLOYEES_STREAM);

        assertEquals(list, expected);
    }

    @Test
    public void testEmployees() throws JAXBException
    {
        EmpEntitiesList expected = new EmpEntitiesList();

        EmpEntity emp1 = new EmpEntity();
        emp1.setId(1L);
        emp1.setFirstName(FirstName1);
        emp1.setSecondName(SecondName1);
        emp1.setSurName(SurName1);
        DeptEntity dept1 = new DeptEntity();
        dept1.setTitle(Department1);
        emp1.setDepartment(dept1);
        emp1.setJob(Job1);
        emp1.setSalary(10_000_000L);
        emp1.setCity(City1);
        emp1.setUser(null);
        expected.setRows(new ArrayList<>());
        expected.add(emp1);

        EmpEntity emp2 = new EmpEntity();
        emp2.setId(2L);
        emp2.setFirstName(FirstName2);
        emp2.setSecondName(SecondName2);
        emp2.setSurName(SurName2);
        DeptEntity dept2 = new DeptEntity();
        dept2.setTitle(Department2);
        emp2.setDepartment(dept2);
        emp2.setJob(Job2);
        emp2.setSalary(10_000L);
        emp2.setCity(City2);
        emp2.setUser(null);
        expected.add(emp2);

        JAXBContext jc = JAXBContext.newInstance(
            EmpEntitiesList.class, DeptEntity.class, EmpEntity.class, UserEntity.class
        );
        Unmarshaller u = jc.createUnmarshaller();
        EmpEntitiesList list = (EmpEntitiesList) u.unmarshal(new StringReader(EXAMPLE_STRING));
        assertEquals(expected, list);
    }

    @Test
    public void testMarshalToJson() throws JAXBException
    {
        JAXBContext jc = JAXBContext.newInstance(
                EmpEntitiesList.class, DeptEntity.class, EmpEntity.class, UserEntity.class
        );
        Unmarshaller u = jc.createUnmarshaller();
        EmpEntitiesList list = (EmpEntitiesList) u.unmarshal(new StringReader(EXAMPLE_STRING));

        /* default config  JsonbConfig config = new JsonbConfig().withFormatting(true); */
        Jsonb jsonb = JsonbBuilder.create();
        String result = jsonb.toJson(list);
        assertEquals(expectedJson, result);
    }

    @Test
    public void testUnmarshalToJson() throws JAXBException
    {
        Jsonb jsonb = JsonbBuilder.create();
        EmpEntitiesList list = jsonb.fromJson(expectedJson, EmpEntitiesList.class);
    }
}