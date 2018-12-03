package ru.otus.web;

import ru.otus.models.DeptEntity;
import ru.otus.models.EmpEntities;
import ru.otus.models.EmpEntity;

import java.util.ArrayList;

public class TestData
{
    static final String FirstName1 = "FirstName1";
    static final String SecondName1 = "SecondName1";
    static final String SurName1 = "SurName1";
    static final String Department1 = "Department1";
    static final String City1 = "City1";
    static final String Job1 = "Job1";
    static final String FirstName2 = "FirstName2";
    static final String SecondName2 = "SecondName2";
    static final String SurName2 = "SurName2";
    static final String Department2 = "Department2";
    static final String City2 = "City2";
    static final String Job2 = "Job2";
    static final String EXAMPLE_STRING =
        "<?xml version='1.0' encoding='UTF-8' standalone='yes'?>\n" +
        "<employees>\n" +
        "  <employee id='1' salary='10000000'>\n" +
        "    <firstName>" + FirstName1 + "</firstName>\n" +
        "    <secondName>" + SecondName1 + "</secondName>\n" +
        "    <surName>" + SurName1 + "</surName>\n" +
        "    <department id='0'>" + Department1 + "</department>\n" +
        "    <city>" + City1 +  "</city>\n" +
        "    <job>" + Job1 + "</job>\n" +
        "  </employee>\n" +
        "  <employee id='2' salary='10000'>\n" +
        "    <firstName>" + FirstName2 + "</firstName>\n" +
        "    <secondName>" + SecondName2 + "</secondName>\n" +
        "    <surName>" + SurName2 + "</surName>\n" +
        "    <department id='0'>" + Department2 + "</department>\n" +
        "    <city>" + City2 +  "</city>\n" +
        "    <job>" + Job2 + "</job>\n" +
        "  </employee>\n" +
        "</employees>";
    static final String EMPTY_EMPLOYEES =
        "<?xml version='1.0' encoding='UTF-8' standalone='yes'?>\n" +
        "<employees>\n" +
        "</employees>";
    static final String expectedXML =
        "id=\"1\", salary=\"10000000\", " + FirstName1 + ", " + SecondName1 + ", " +
            SurName1 + ", " + Department1 + ", " + City1 + ", " + Job1;
    static final String expectedJson =
        "{\"employees\":[{\"city\":\"City1\",\"department\":\"Department1\",\"first-name\":\"FirstName1\"" +
        ",\"id\":1,\"job\":\"Job1\",\"name\":\"FirstName1 SecondName1 SurName1\",\"salary\":10000000" +
        ",\"second-name\":\"SecondName1\",\"sur-name\":\"SurName1\"},{\"city\":\"City2\"" +
        ",\"department\":\"Department2\",\"first-name\":\"FirstName2\",\"id\":2,\"job\":\"Job2\"" +
        ",\"name\":\"FirstName2 SecondName2 SurName2\",\"salary\":10000,\"second-name\":\"SecondName2\"" +
        ",\"sur-name\":\"SurName2\"}]}";

    public static EmpEntities getExpectedEmpEntitiesList()
    {
        EmpEntities expected = new EmpEntities();

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
        expected.setEmployees(new ArrayList<>());
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
        return expected;
    }
}
