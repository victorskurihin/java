package ru.otus.l071;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.SortedMap;

public class ATMOriginatorTest {
    private ATMOriginator atm;
    private Caretaker caretaker;
    @Before
    public void setUp() throws Exception {
        atm = new ATM();
        caretaker = new Caretaker();
    }

    @After
    public void tearDown() throws Exception {
        caretaker = null;
        atm = null;
    }

    @Test
    public void restoreMemento() throws Exception {
        SortedMap<Bond, Integer> startState = ATM.generateRandomState();
        ATMOriginator expectedAtm = new ATM();

        atm.setMemento(startState);
        expectedAtm.setMemento(startState);
        caretaker.setMemento(atm.saveMemento());

        atm.setMemento(ATM.generateRandomState());

        atm.restoreMemento(caretaker.getMemento());

        Assert.assertEquals(expectedAtm, atm);
    }
}