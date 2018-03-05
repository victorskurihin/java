package ru.otus.l141;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;

public class FinalAssemblyTest {
    FinalAssembly<Integer> finalAssembly;
    private ArrayList<Integer>[] subList0;
    private ArrayList<Integer>[] subList1;
    private ArrayList<Integer>[] subList2;
    private ArrayList<Integer>[] subList100;

    @SuppressWarnings("unchecked")
    @Before
    public void setUp() throws Exception {
        subList0 = new ArrayList[0];
        subList1 = new ArrayList[1];
        subList2 = new ArrayList[1];
        subList1[0] = new ArrayList<>(1);
        subList1[0].add(1);
        subList2[0] = new ArrayList<>(2);
        subList2[0].add(1);
        subList2[0].add(2);
        subList100 = new ArrayList[10];
        for (int idx = 0; idx < 10; ++idx) {
            subList100[idx] = new ArrayList<>(10);
            for (int jdx = 0; jdx < 10; ++jdx) {
                subList100[idx].add(idx*10 + (jdx + 1));
            }
        }
        Arrays.stream(subList100).forEach(System.out::println);
    }

    @After
    public void tearDown() throws Exception {
        finalAssembly = null;
        subList1 = null;
        subList0 = null;
    }

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void testEmtyThrowExeption() {
        finalAssembly = new FinalAssembly<>(subList0);
    }

    @Test
    public void test1() {
        finalAssembly = new FinalAssembly<>(subList1);
        Assert.assertTrue(finalAssembly.hasNext());
    }

    @Test
    public void test2() {
        finalAssembly = new FinalAssembly<>(subList1);
        finalAssembly.findMinimumInList(subList1[0], 0);
        Assert.assertTrue(finalAssembly.hasNext());
    }

    @Test
    public void test3() {
        finalAssembly = new FinalAssembly<>(subList1);
        finalAssembly.pullMinimal();
        Assert.assertFalse(finalAssembly.hasNext());
    }

    @Test
    public void test4() {
        finalAssembly = new FinalAssembly<>(subList2);
        int i1 = finalAssembly.pullMinimal();
        Assert.assertEquals(1, i1);
        Assert.assertTrue(finalAssembly.hasNext());
        int i2 = finalAssembly.pullMinimal();
        Assert.assertEquals(2, i2);
    }

    @Test
    public void test10() {
        int expected = 1;
        finalAssembly = new FinalAssembly<>(subList100);
        while (finalAssembly.hasNext()) {
            int i = finalAssembly.pullMinimal();
            Assert.assertEquals(expected++, i);
        }
    }
}