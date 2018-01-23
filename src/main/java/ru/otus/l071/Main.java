package ru.otus.l071;

import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Created by VSkurikhin.
 *
 * Solution for L07.1
 *
 * To start:
 * mvn clean compile
 *
 * ./run.sh
 * or
 * run.bat
 */

public class Main {

    public static void main(String[] args) throws Exception {
        List<ATM> listATM = DepartmentObserver.createListOfATM(
            DepartmentObserver.DEFAULT_NUMBER_ATM
        );
        DepartmentObserver departmentObserver = new DepartmentObserver(listATM);
        System.out.println("Sum of Balanses = " + departmentObserver.sumOfBalance());
        for (ATM atm : listATM) {
            atm.withdraw(atm.balance() - 100);
        }
        System.out.println("Sum of Balanses = " + departmentObserver.sumOfBalance());
        for (ATM atm : listATM) {
            atm.withdraw(atm.balance());
        }
        System.out.println("Sum of Balanses = " + departmentObserver.sumOfBalance());
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
