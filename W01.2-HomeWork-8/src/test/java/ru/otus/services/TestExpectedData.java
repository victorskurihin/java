package ru.otus.services;

import ru.otus.models.*;

import java.time.LocalDateTime;

public class TestExpectedData
{
    public static final String directory =
            "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + System.lineSeparator() +
            "<directory>" + System.lineSeparator() +
            "<organizationUnit id='1' title='title1'>" + System.lineSeparator() +
            "<organizationUnit id='2' title='title2'>" + System.lineSeparator() +
            "</organizationUnit>" + System.lineSeparator() +
            "<organizationUnit id='3' title='title3'>" + System.lineSeparator() +
            "</organizationUnit>" + System.lineSeparator() +
            "</organizationUnit>" + System.lineSeparator() +
            "</directory>";
    public static final String XML_TEST = "<?xml version='1.0' encoding='UTF-8'?><Test/>";
    public static final String JSON_TEST = "{Test:''}";
    public static final int DELAY_TEST = 150;

    public static DeptEntity getTestDeptEntity1()
    {
        DeptEntity result = new DeptEntity();
        result.setId(1L);
        result.setParentId(0L);
        result.setTitle("title1");

        return result;
    }

    public static DeptEntity getTestDeptEntity2()
    {
        DeptEntity result = new DeptEntity();
        result.setId(2L);
        result.setParentId(1L);
        result.setTitle("title2");

        return result;
    }

    public static DeptEntity getTestDeptEntity3()
    {
        DeptEntity result = new DeptEntity();
        result.setId(3L);
        result.setParentId(1L);
        result.setTitle("title3");

        return result;
    }

    public static DeptEntitiesList getTestDeptEntitiesList()
    {
        DeptEntitiesList result = new DeptEntitiesList();
        result.add(getTestDeptEntity1());
        result.add(getTestDeptEntity2());
        result.add(getTestDeptEntity3());
        return result;
    }

    public static EmpEntity getTestEmpEntity1()
    {
        EmpEntity result = new EmpEntity();
        result.setId(1L);
        result.setFirstName("Александр");
        result.setSecondName("Иванович");
        result.setSurName("Фунт");
        result.setCity("Одесса");
        result.setJob("Зицпредседатель");
        result.setSalary(15L);
        result.setAge(75L);

        result.setUser(getTestUserEntity1());
        result.setDepartment(getTestDeptEntity1());

        return result;
    }

    public static EmpEntity getTestEmpEntity2()
    {
        EmpEntity result = new EmpEntity();
        result.setId(2L);
        result.setFirstName("Иван");
        result.setSecondName("Иванович");
        result.setSurName("Иванов");
        result.setCity("Самара");
        result.setJob("Директор");
        result.setSalary(10L);
        result.setAge(33L);

        result.setUser(null);
        result.setDepartment(getTestDeptEntity2());

        return result;
    }

    public static EmpEntity getTestEmpEntity3()
    {
        EmpEntity result = new EmpEntity();
        result.setId(3L);
        result.setFirstName("Светлана");
        result.setSecondName("Семёновна");
        result.setSurName("Семёнова");
        result.setCity("Воронеж");
        result.setJob("Бухгалтер");
        result.setSalary(9L);
        result.setAge(34L);

        result.setUser(null);
        result.setDepartment(getTestDeptEntity3());

        return result;
    }

    public static EmpEntitiesList getTestEmpEntitiesList()
    {
        EmpEntitiesList result = new EmpEntitiesList();
        result.add(getTestEmpEntity1());
        result.add(getTestEmpEntity2());
        result.add(getTestEmpEntity3());
        return result;
    }

    public static UserEntity getTestUserEntity1()
    {
        UserEntity result = new UserEntity();
        result.setId(1L);
        result.setName("funt");
        result.setPassword("e1X6Nk2/9ydYmDO74y89BK0aNQmQnQjK59X46mMRV9Q=");

        return result;
    }

    private static void getTestStatisticEntity(StatisticEntity result)
    {
        result.setNameMarker("DEFAULT_MARKER");
        result.setJspPageName("browsers");
        result.setIpAddress("127.0.0.1");
        result.setUserAgent("Wget/1.19.4 (linux-gnu)");
        result.setSessionId("0000000000000000000000000000");
    }

    public static StatisticEntity getTestStatisticEntity1()
    {
        StatisticEntity result = new StatisticEntity();
        result.setId(1L);
        getTestStatisticEntity(result);
        result.setClientTime(null);
        result.setServerTime(null);
        result.setUser(null);
        result.setPreviousId(0L);
        return result;
    }

    public static StatisticEntity getTestStatisticEntity2()
    {
        StatisticEntity result = new StatisticEntity();
        result.setId(2L);
        getTestStatisticEntity(result);
        result.setUser(getTestUserEntity1());
        result.setClientTime(LocalDateTime.MIN);
        result.setServerTime(LocalDateTime.MAX);
        result.setPreviousId(1L);
        return result;
    }

    public static StatisticEntity getTestStatisticEntity3()
    {
        StatisticEntity result = new StatisticEntity();
        result.setId(3L);
        getTestStatisticEntity(result);
        result.setUser(getTestUserEntity1());
        result.setClientTime(LocalDateTime.MIN);
        result.setServerTime(LocalDateTime.MAX);
        result.setPreviousId(1L);
        return result;
    }
}
