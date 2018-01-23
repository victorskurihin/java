package ru.otus.l071;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

public class DepartmentObserverTest {
    DepartmentObserver departmentObserver;
    List<ATM> listATM;

    @Before
    public void setUp() throws Exception {
        listATM = DepartmentObserver.createListOfATM(
            DepartmentObserver.DEFAULT_NUMBER_ATM
        );
        departmentObserver = new DepartmentObserver(listATM);
    }

    @After
    public void tearDown() throws Exception {
        departmentObserver = null;
        listATM = null;
    }

    @SuppressWarnings("Convert2MethodRef")
    @Test
    public void sumOfBalance() throws Exception {
        int expectedSum = listATM.stream()
            .mapToInt(atm -> atm.balance())
            .sum();
        Assert.assertEquals(expectedSum, departmentObserver.sumOfBalance());
    }

    private static ATM cloneATM(ATM atm) {
        ATM result = new ATM();
        result.setMemento(new TreeMap<>(atm.getMemento()));
        return result;

    }

    @SuppressWarnings("Convert2MethodRef")
    @Test
    public void testObserver() {
        DepartmentObserver expectedDepartmentObserver = new DepartmentObserver(
            listATM.stream()
                .map(DepartmentObserverTest::cloneATM)
                .collect(Collectors.toList())
        );

        for (ATM atm : listATM) {
            atm.withdraw(atm.balance());
        }
        Assert.assertEquals(expectedDepartmentObserver, departmentObserver);
    }
}