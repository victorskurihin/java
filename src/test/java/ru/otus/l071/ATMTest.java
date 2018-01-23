package ru.otus.l071;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

import static org.junit.Assert.*;
import static ru.otus.l071.Bond.*;

public class ATMTest {
    private ATM atm;

    @Before
    public void setUp() throws Exception {
        atm = new ATM();
    }

    @After
    public void tearDown() throws Exception {
        atm = null;
    }

    /**
     * Принимать банкноты разных номиналов.
     * (на каждый номинал должна быть своя ячейка)
     */
    @Test
    public void  acceptBanknotesOfDifferentDenominations () {
        atm.deposit(2, ONE_HUNDRED);
        atm.deposit(3, FIVE_HUNDREDS);
        atm.deposit(4, ONE_THOUSAND);
        atm.deposit(5, FIVE_THOUSANDS);
        int balance = atm.balance();
        assertEquals(2*100 + 3*500 + 4*1000 + 5*5000, balance);
    }

    /**
     * Bыдавать запрошенную сумму минимальным количеством банкнот или ошибку
     * если сумму нельзя выдать.
     * @throws Exception
     */
    @Test(expected = NotDispenseAmountExecption.class)
    public void pickUp() throws Exception {
        atm.deposit(6, ONE_HUNDRED);
        atm.deposit(3, FIVE_HUNDREDS);
        atm.deposit(2, ONE_THOUSAND);
        Map<Bond, Integer> actualMap = atm.pickUp(1*100 + 2*500 + 2*1000);
        Map<Bond, Integer> expectedMap = new TreeMap<>();
        expectedMap.put(ONE_THOUSAND, 2);
        expectedMap.put(FIVE_HUNDREDS, 2);
        expectedMap.put(ONE_HUNDRED, 1);
        Assert.assertEquals(expectedMap, actualMap);
        atm.pickUp(6*100 + 3*500 + 2*1000 + 1);
    }

    /**
     * Выдавать сумму остатка денежных средств.
     * @throws Exception
     */
    @Test
    public void withdraw() throws Exception {
        final int LIMIT = 1*5000 + 4*1000 + 1*500 + 4*100;
        atm.deposit(1, FIVE_THOUSANDS);
        atm.deposit(4, ONE_THOUSAND);
        atm.deposit(1, FIVE_HUNDREDS);
        atm.deposit(4, ONE_HUNDRED);
        Random random = new Random();
        int amount = random.nextInt((LIMIT - 100) / 100) * 100 + 100;
        atm.withdraw(amount);
        Assert.assertEquals(LIMIT - amount, atm.balance());
    }

}