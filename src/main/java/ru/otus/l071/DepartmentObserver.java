package ru.otus.l071;

import java.util.*;
import java.util.stream.Collectors;

public class DepartmentObserver extends Observer<ATM> {
    final public static int DEFAULT_NUMBER_ATM = 10;
    private Map<ATM, Caretaker> atmStates;

    private Caretaker takeATM(ATM a) {
        Caretaker result = new Caretaker();
        result.setMemento(a.saveMemento());
        a.attach(this);
        return result;
    }

    @SuppressWarnings("Convert2MethodRef")
    public DepartmentObserver(List<ATM> al) {
        atmStates = al.stream().collect(
            Collectors.toMap(a -> a, atm -> takeATM(atm))
        );
    }

    static List<ATM> createListOfATM(int numberAtm) {
        List<ATM> result = new ArrayList<>();

        for (int i = 0; i < numberAtm; i++) {
            ATM atm = new ATM();
            atm.setMemento(ATM.generateRandomState());
            result.add(atm);
        }
        return result;
    }

    public DepartmentObserver(int numberATM) {
        this(createListOfATM(numberATM));
    }

    /**
     * Default constructor
     */
    public DepartmentObserver() {
        this(createListOfATM(DEFAULT_NUMBER_ATM));
    }

    @SuppressWarnings("Convert2MethodRef")
    public int sumOfBalance() {
        return atmStates.keySet().stream()
            .mapToInt(atm -> atm.balance())
            .sum();
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (other instanceof DepartmentObserver) {
            DepartmentObserver o = (DepartmentObserver) other;
            Set<ATM> set1 = new HashSet<>(atmStates.keySet());
            Set<ATM> set2 = new HashSet<>(o.atmStates.keySet());
            for (ATM atm2 : o.atmStates.keySet()) {
                for (ATM atm1 : atmStates.keySet()) {
                    if (atm1.equals(atm2)) {
                        set1.remove(atm1);
                        set2.remove(atm2);
                    }
                }
            }
            return (0 == set1.size()) && (0 == set2.size());
        }
        return false;
    }

    @Override
    public void update(ATM atm) {
        if (0 == atm.getStatus()) {
            if (atmStates.containsKey(atm)) {
                Caretaker caretaker = atmStates.get(atm);
                atm.restoreMemento(caretaker.getMemento());
            }
        }
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
